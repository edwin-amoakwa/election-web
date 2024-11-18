/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.services;

import com.stately.common.formating.ObjectValue;
import com.stately.common.utils.StringUtil;
import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.elections.constants.ElectionType;
import com.statelyhub.elections.constants.ResultSource;
import com.statelyhub.elections.constants.ResultStatus;
import com.statelyhub.elections.entities.ConstituencyElection;
import com.statelyhub.elections.entities.ConstituencyResultSet;
import com.statelyhub.elections.entities.ElectionContestant;
import com.statelyhub.elections.entities.ElectionPollingStation;
import com.statelyhub.elections.entities.PollingStationResult;
import com.statelyhub.elections.entities.PollingStationResultSet;
import com.statelyhub.elections.entities.Result;
import com.statelyhub.elections.entities.ResultSet;
import com.statelyhub.elections.entities.ResultSubmission;
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



        List<ElectionTypeResult> list = new LinkedList<>();

        list.add(pollingStationBucket(eps, ElectionType.PRESIDENTIAL));
        list.add(pollingStationBucket(eps, ElectionType.PARLIAMENTARY));

        return list;

    }
    
    
    public ElectionTypeResult pollingStationBucket(ElectionPollingStation eps, ElectionType electionType) {

        ElectionTypeResult electionTypeResult = new ElectionTypeResult();
        electionTypeResult.setElectionType(electionType);
        electionTypeResult.setVotingsList(electionService.eps(eps, electionType));

    

        return electionTypeResult;

    }

    public List<ElectionTypeResult> volunteerEpsBucket(ElectionPollingStation eps) {


//
        List<ElectionTypeResult> list = new LinkedList<>();

        list.add(volunteerEpsBucket(eps, ElectionType.PRESIDENTIAL));
        list.add(volunteerEpsBucket(eps, ElectionType.PARLIAMENTARY));

        return list;

    }
    
    
    
    public ElectionTypeResult volunteerEpsBucket(ElectionPollingStation eps, ElectionType electionType) {

        ElectionTypeResult typeResult = new ElectionTypeResult();
        typeResult.setElectionType(electionType);
        typeResult.setSubmittedResultsList(electionService.submitted(eps, electionType));

     

        return typeResult;

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
    
    
    
    public ElectionTypeResult constituencyType(ConstituencyElection constituencyElection, ElectionType electionType) {

        ElectionTypeResult result = new ElectionTypeResult();
        result.setElectionType(electionType);
        result.setContestantsList(electionService.consititueny(constituencyElection, electionType));
        

        return result;

    }

    public void updatePollingStationSourceChange(ConstituencyElection ce) {

        List<PollingStationResult> pollingStationResultsList = QryBuilder.get(crudService.getEm(), PollingStationResult.class)
                //                .addObjectParam(PollingStationResult._constituencyElection, ce)
                .addObjectParam(PollingStationResult._election, ce.getElection())
                .addObjectParam(PollingStationResult._electionPollingStation_constituency, ce.getConstituency())
                .buildQry().getResultList();
        
//        PollingStationResultSet resultSet =
//        
//        updateSource(pollingStationResultsList);
        
//        for (PollingStationResult result : pollingStationResultsList) {
//
//            if (ce.getResultSource() == ResultSource.SUBMITTED) {
//                result.setAcceptedResult(result.getSubmittedResult());
////                System.out.println(".....  setting result .... ");
//
//            } else if (ce.getResultSource() == ResultSource.OFFICIAL) {
//                result.setAcceptedResult(result.getOfficialResult());
//            } else if (ce.getResultSource() == ResultSource.INPUTTED) {
//                result.setAcceptedResult(result.getInputResult());
//            }
//
//        }

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
    
    public void updateSource(PollingStationResultSet resultSet, List<PollingStationResult> pollingStationResultsList)
    {
        
        if(resultSet.getResultSource() == null)
        {
            resultSet.setResultSource(ResultSource.INPUTTED);
        }
        
        for (PollingStationResult result : pollingStationResultsList) {
             if (resultSet.getResultSource() == ResultSource.SUBMITTED) {
                result.setAcceptedResult(result.getSubmittedResult());
//                System.out.println(".....  setting result .... ");

            } else if (resultSet.getResultSource() == ResultSource.OFFICIAL) {
                result.setAcceptedResult(result.getOfficialResult());
            } else if (resultSet.getResultSource() == ResultSource.INPUTTED) {
                result.setAcceptedResult(result.getInputResult());
            }
        }
        
        
        runPosition(pollingStationResultsList);
        
          for (PollingStationResult result : pollingStationResultsList) {
            crudService.save(result);
        }
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

        StringUtil.printObjectListArray(summedResult);
        Map<ElectionContestant, Integer> map = new HashMap<>(summedResult.size());

        for (Object[] objects : summedResult) {

            ElectionContestant contestant = (ElectionContestant) objects[0];
            int total = ObjectValue.get_intValue(objects[1]);
            map.put(contestant, total);

            System.out.println(contestant.getId() + "  ----   " + contestant.getParty() + " ........" + total);
        }

        List<ElectionContestant> contestantsList = electionService.cecs(ce,electionType);
//          System.out.println("map >>>>>>>>> " + map);
        for (ElectionContestant contestant : contestantsList) {

            Integer votes = map.get(contestant);

//             System.out.println(contestant.getId() + "   ...... " + votes );
            if (votes != null) {
                contestant.setAcceptedResult(votes);
            }
        }
        runPosition(contestantsList);
        runPct(contestantsList, 0);

        for (ElectionContestant electionContestant : contestantsList) {

            crudService.save(electionContestant);
        }
        
        // run result set
         Object[] summarySet = QryBuilder.get(crudService.getEm(), PollingStationResultSet.class)
                .addReturnField("SUM(e." + PollingStationResultSet._totalVotesCast + ")")
                .addReturnField("SUM(e." + PollingStationResultSet._rejectedBallots + ")")
                .addReturnField("SUM(e." + PollingStationResultSet._spoiltBallots + ")")
                .addReturnField("SUM(e." + PollingStationResultSet._validVotes + ")")
                .addGroupBy(PollingStationResultSet._electionType)
                .addObjectParam(PollingStationResultSet._constituencyElection, ce)
                .addObjectParam(PollingStationResultSet._electionType, electionType)
                 .addObjectParam(PollingStationResultSet._resultStatus, ResultStatus.FINALISED)
                 .getSingleResult(Object[].class);
         
         
         ConstituencyResultSet resultSet = electionService.init(ce, electionType);
         
         if(summarySet != null)
         {
             
//         resultSet.setElectionType(electionType);
         resultSet.setTotalVotesCast(ObjectValue.get_intValue(summarySet[0]));
         resultSet.setRejectedBallots(ObjectValue.get_intValue(summarySet[1]));
         resultSet.setSpoiltBallots(ObjectValue.get_intValue(summarySet[2]));
         resultSet.setValidVotes(ObjectValue.get_intValue(summarySet[3]));
         
//         ce.update(resultSet);
          crudService.save(ce);
          crudService.save(resultSet);
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
        
        int countWins = 0;
        for (Result result : pollingStationResultsList) {
            if(result.getPosition() == 1)
            {
                countWins++;
            }
        }
        
        if(countWins != 1)
        {
            return;
        }
        
        for (Result result : pollingStationResultsList) {
            if(result.getPosition() == 1)
            {
                result.setWon(1);
                
            }
            else{
                result.setWon(0);
            }
        }
    }

    public void runPct(List<? extends Result> resultsList, int total) {
        
        
          double totalVotes = resultsList.stream().mapToInt(Result::getAcceptedResult).sum();
//                if(totalVotes != 0)
//                {
//        
//System.out.println("........ " + totalVotes);
        
        if (totalVotes != 0) {
//            double totalVotes = total;

            
            
            for (Result submittedResult : resultsList) {
                double pct = submittedResult.getAcceptedResult() / totalVotes;
                submittedResult.setVotePct(pct);

            }
        }
    }

//    public void updateResultSet(ResultSubmission resultSubmission, ElectionPollingStation electionPollingStation) {
//        updateResultSet(ElectionType.PRESIDENTIAL, resultSubmission, electionPollingStation);
//        updateResultSet(ElectionType.PARLIAMENTARY, resultSubmission, electionPollingStation);
//    }
    
    public PollingStationResultSet get(ElectionType electionType, ElectionPollingStation eps)
    {
        return QryBuilder.get(crudService.getEm(), PollingStationResultSet.class)
                    .addObjectParam(PollingStationResultSet._electionType, ElectionType.PRESIDENTIAL)
                    .addObjectParam(PollingStationResultSet._electionPollingStation, eps)
                    .getSingleResult(PollingStationResultSet.class);
    }

//    public void updateResultSet(ElectionType electionType, ResultSubmission resultSubmission, ElectionPollingStation electionPollingStation) {
//
//        
//
//        if (resultSubmission != null) {
//            PollingStationResultSet resultSet = get(electionType, electionPollingStation);
//
//            if (resultSet == null) {
//                resultSet = new PollingStationResultSet();
//                resultSet.setElectionType(electionType);
//                resultSet.setElectionPollingStation(electionPollingStation);
////                resultSet
//            }
//            /// add the rest
//            resultSet.setRejectedBallots(resultSubmission.getRejectedBallots());
//            resultSet.setTotalVotesCast(resultSubmission.getTotalVotesCast());
//            resultSet.setValidVotes(resultSubmission.getValidVotes());
//            resultSet.setSpoiltBallots(resultSubmission.getSpoiltBallots());
//
//            crudService.save(resultSet);
//        }
//    }

}
