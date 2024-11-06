/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.services;

import com.stately.common.formating.ObjectValue;
import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.elections.constants.ElectionType;
import com.statelyhub.elections.constants.ResultSource;
import com.statelyhub.elections.entities.ConstituencyElection;
import com.statelyhub.elections.entities.ElectionContestant;
import com.statelyhub.elections.entities.ElectionPollingStation;
import com.statelyhub.elections.entities.PollingStationResult;
import com.statelyhub.elections.entities.Result;
import com.statelyhub.elections.entities.SubmittedResult;
import com.statelyhub.elections.model.ElectionTypeResult;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author edwin
 */
@Stateless
public class ElectionResultService {

    @Inject
    private CrudService crudService;

    @Inject
    private ElectionService electionService;

    public List<ElectionTypeResult> pollingStationBucket(ElectionPollingStation eps) {

        ElectionTypeResult presidential = new ElectionTypeResult();
        presidential.setElectionType(ElectionType.PRESIDENTIAL);
        presidential.setVotingsList(electionService.eps(eps, ElectionType.PRESIDENTIAL));

        ElectionTypeResult parliamentary = new ElectionTypeResult();
        parliamentary.setElectionType(ElectionType.PARLIAMENTARY);
        parliamentary.setVotingsList(electionService.eps(eps, ElectionType.PARLIAMENTARY));

        List<ElectionTypeResult> list = new LinkedList<>();

        list.add(presidential);
        list.add(parliamentary);

        return list;

    }

    public List<ElectionTypeResult> volunteerEpsBucket(ElectionPollingStation eps) {

        ElectionTypeResult presidential = new ElectionTypeResult();
        presidential.setElectionType(ElectionType.PRESIDENTIAL);
        presidential.setSubmittedResultsList(electionService.submitted(eps, ElectionType.PRESIDENTIAL));

        ElectionTypeResult parliamentary = new ElectionTypeResult();
        parliamentary.setElectionType(ElectionType.PARLIAMENTARY);
        parliamentary.setSubmittedResultsList(electionService.submitted(eps, ElectionType.PARLIAMENTARY));

        List<ElectionTypeResult> list = new LinkedList<>();

        list.add(presidential);
        list.add(parliamentary);

        return list;

    }

    public List<ElectionTypeResult> constituency(ConstituencyElection constituencyElection) {

        ElectionTypeResult presidential = new ElectionTypeResult();
        presidential.setElectionType(ElectionType.PRESIDENTIAL);
        presidential.setContestantsList(electionService.consititueny(constituencyElection, ElectionType.PRESIDENTIAL));

        ElectionTypeResult parliamentary = new ElectionTypeResult();
        parliamentary.setElectionType(ElectionType.PARLIAMENTARY);
        parliamentary.setContestantsList(electionService.consititueny(constituencyElection, ElectionType.PARLIAMENTARY));

        List<ElectionTypeResult> list = new LinkedList<>();

        list.add(presidential);
        list.add(parliamentary);

        return list;

    }

    public void updatePollingStationSourceChange(ConstituencyElection ce) {

        List<PollingStationResult> pollingStationResultsList = QryBuilder.get(crudService.getEm(), PollingStationResult.class)
                .addObjectParam(PollingStationResult._constituencyElection, ce)
                .buildQry().getResultList();

        for (PollingStationResult result : pollingStationResultsList) {

            if (ce.getResultSource() == ResultSource.SUBMITTED) {
                result.setAcceptedResult(result.getSubmittedResult());

            } else if (ce.getResultSource() == ResultSource.OFFICIAL) {
                result.setAcceptedResult(result.getOfficialResult());
            } else if (ce.getResultSource() == ResultSource.INPUTTED) {
                result.setAcceptedResult(result.getInputResult());
            }

        }

        //Run Positions for polling station
        Map<ElectionPollingStation, List<PollingStationResult>> map = pollingStationResultsList
                .stream()
                .collect(Collectors.groupingBy(PollingStationResult::getElectionPollingStation));

        for (Map.Entry<ElectionPollingStation, List<PollingStationResult>> entry : map.entrySet()) {

            runPosition(entry.getValue());
        }

        for (PollingStationResult result : pollingStationResultsList) {
            crudService.save(result);
        }

        // run consistuncy cyooary
        crudService.save(ce);
    }

    public void runConstituency(ConstituencyElection ce) {
        List<Object[]> summedResult = QryBuilder.get(crudService.getEm(), PollingStationResult.class)
                .addReturnField("e." + PollingStationResult._electionContestant)
                .addReturnField("SUM(e." + PollingStationResult._acceptedResult + ")")
                .addGroupBy(PollingStationResult._electionContestant_id)
                .addObjectParam(PollingStationResult._constituencyElection, ce)
                .buildQry().getResultList();

        Map<ElectionContestant, Integer> map = new HashMap<>(summedResult.size());

        for (Object[] objects : summedResult) {
            ElectionContestant contestant = (ElectionContestant) objects[0];
            int total = ObjectValue.get_intValue(objects[1]);
            map.put(contestant, total);
        }

        List<ElectionContestant> contestantsList = electionService.cecs(ce, ElectionType.PRESIDENTIAL);
        for (ElectionContestant contestant : contestantsList) {
            Integer votes = map.get(contestant);
            if (votes != null) {
                contestant.setAcceptedResult(votes);
            }
        }
        runPosition(contestantsList);
        runPct(contestantsList, ce.getVotersCount());

        
        
        
        contestantsList = electionService.cecs(ce, ElectionType.PARLIAMENTARY);
         for (ElectionContestant contestant : contestantsList) {
            Integer votes = map.get(contestant);
            if (votes != null) {
                contestant.setAcceptedResult(votes);
            }
        }
        runPosition(contestantsList);
        runPct(contestantsList, ce.getVotersCount());
    }

    public void runPosition(List<? extends Result> pollingStationResultsList) {

        for (Result compare : pollingStationResultsList) {

            int counter = 0;
            for (Result submitted : pollingStationResultsList) {
                if (submitted.getAcceptedResult() > compare.getAcceptedResult()) {
                    counter++;
                }
            }
            compare.setPosition(counter + 1);
        }
    }

    public void runPct(List<? extends Result> resultsList, int total) {
        if (total != 0) {
            double totalVotes = total;

            for (Result submittedResult : resultsList) {
                double pct = submittedResult.getInputResult() / totalVotes;
                submittedResult.setVotePct(pct);

            }
        }
    }
}
