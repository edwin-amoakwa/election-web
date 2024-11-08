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
import com.statelyhub.elections.entities.PollingStationResultSet;
import com.statelyhub.elections.entities.Result;
import com.statelyhub.elections.entities.ResultSet;
import com.statelyhub.elections.entities.ResultSubmission;
import com.statelyhub.elections.entities.SubmittedResultSet;
import com.statelyhub.elections.model.ElectionTypeResult;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.HashMap;
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
                //                .addObjectParam(PollingStationResult._constituencyElection, ce)
                .addObjectParam(PollingStationResult._election, ce.getElection())
                .addObjectParam(PollingStationResult._electionPollingStation_constituency, ce.getConstituency())
                .buildQry().getResultList();

//        System.out.println("pollingStationResultsList.......... " + pollingStationResultsList);
        for (PollingStationResult result : pollingStationResultsList) {

            if (ce.getResultSource() == ResultSource.SUBMITTED) {
                result.setAcceptedResult(result.getSubmittedResult());
//                System.out.println(".....  setting result .... ");

            } else if (ce.getResultSource() == ResultSource.OFFICIAL) {
                result.setAcceptedResult(result.getOfficialResult());
            } else if (ce.getResultSource() == ResultSource.INPUTTED) {
                result.setAcceptedResult(result.getInputResult());
            }

        }

//        System.out.println("\n\n\n\n\n-pollingStationResultsList.......... " + pollingStationResultsList);
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

        crudService.save(ce);
    }

    public void runConstituency(ConstituencyElection ce) {
        runConstituency(ce, ElectionType.PRESIDENTIAL);
        runConstituency(ce, ElectionType.PARLIAMENTARY);
    }

    public void runConstituency(ConstituencyElection ce, ElectionType electionType) 
    {
        List<Object[]> summedResult = QryBuilder.get(crudService.getEm(), PollingStationResult.class)
                .addReturnField("e." + PollingStationResult._electionContestant)
                .addReturnField("SUM(e." + PollingStationResult._acceptedResult + ")")
                .addGroupBy(PollingStationResult._electionContestant_id)
                .addObjectParam(PollingStationResult._constituencyElection, ce)
                .addObjectParam(PollingStationResult._electionType, electionType)
                .buildQry().getResultList();

//        StringUtil.printObjectListArray(summedResult);
        Map<ElectionContestant, Integer> map = new HashMap<>(summedResult.size());

        for (Object[] objects : summedResult) {

            ElectionContestant contestant = (ElectionContestant) objects[0];
            int total = ObjectValue.get_intValue(objects[1]);
            map.put(contestant, total);

            System.out.println(contestant.getId() + "  ----   " + contestant.getParty() + " ........" + total);
        }

        List<ElectionContestant> contestantsList = electionService.cecs(ce, electionType);
//          System.out.println("map >>>>>>>>> " + map);
        for (ElectionContestant contestant : contestantsList) {

            Integer votes = map.get(contestant);

//             System.out.println(contestant.getId() + "   ...... " + votes );
            if (votes != null) {
                contestant.setAcceptedResult(votes);
            }
        }
        runPosition(contestantsList);
        runPct(contestantsList, ce.getVotersCount());

        for (ElectionContestant electionContestant : contestantsList) {

//              System.out.println(electionContestant.getParty() + " ........ " + electionContestant.getAcceptedResult());
            crudService.save(electionContestant);
        }
        
        // run result set
         Object[] summarySet = QryBuilder.get(crudService.getEm(), PollingStationResultSet.class)
                .addReturnField("SUM(e." + PollingStationResultSet._totalVotesCast + ")")
                .addReturnField("SUM(e." + PollingStationResultSet._rejectedBallots + ")")
                .addReturnField("SUM(e." + PollingStationResultSet._spoiltBallots + ")")
                .addReturnField("SUM(e." + PollingStationResultSet._validVotes + ")")
                .addGroupBy(PollingStationResultSet._electionType)
                .addObjectParam(PollingStationResultSet._electionPollingStation_constituencyElection, ce)
                .addObjectParam(PollingStationResultSet._electionType, electionType)
                 .getSingleResult(Object[].class);
         
         if(summarySet != null)
         {
              ResultSet resultSet = new ResultSet();
         resultSet.setElectionType(electionType);
         resultSet.setTotalVotesCast(ObjectValue.get_intValue(summarySet[0]));
         resultSet.setRejectedBallots(ObjectValue.get_intValue(summarySet[1]));
         resultSet.setSpoiltBallots(ObjectValue.get_intValue(summarySet[2]));
         resultSet.setValidVotes(ObjectValue.get_intValue(summarySet[3]));
         
         ce.update(resultSet);
          crudService.save(ce);
         }
         
        
         
        
         
         

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

    public void updateResultSet(ResultSubmission resultSubmission, ElectionPollingStation electionPollingStation) {
        updateResultSet(ElectionType.PRESIDENTIAL, resultSubmission, electionPollingStation);
        updateResultSet(ElectionType.PARLIAMENTARY, resultSubmission, electionPollingStation);
    }
    
    public PollingStationResultSet get(ElectionType electionType, ElectionPollingStation eps)
    {
        return QryBuilder.get(crudService.getEm(), PollingStationResultSet.class)
                    .addObjectParam(SubmittedResultSet._electionType, ElectionType.PRESIDENTIAL)
                    .addObjectParam(PollingStationResultSet._electionPollingStation, eps)
                    .getSingleResult(PollingStationResultSet.class);
    }

    public void updateResultSet(ElectionType electionType, ResultSubmission resultSubmission, ElectionPollingStation electionPollingStation) {
        SubmittedResultSet submittedResultSet = QryBuilder.get(crudService.getEm(), SubmittedResultSet.class)
                .addObjectParam(SubmittedResultSet._electionType, ElectionType.PRESIDENTIAL)
                .addObjectParam(SubmittedResultSet._resultSubmission, resultSubmission.getId())
                .getSingleResult(SubmittedResultSet.class);

        if (submittedResultSet != null) {
            PollingStationResultSet resultSet = get(electionType, electionPollingStation);

            if (resultSet == null) {
                resultSet = new PollingStationResultSet();
                resultSet.setElectionType(electionType);
                resultSet.setElectionPollingStation(electionPollingStation);
            }
            /// add the rest
            resultSet.setRejectedBallots(submittedResultSet.getRejectedBallots());
            resultSet.setTotalVotesCast(submittedResultSet.getTotalVotesCast());
            resultSet.setValidVotes(submittedResultSet.getValidVotes());
            resultSet.setSpoiltBallots(submittedResultSet.getSpoiltBallots());

            crudService.save(resultSet);
        }
    }

}
