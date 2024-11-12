/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.jsf;

import com.stately.modules.jpa2.QryBuilder;
import com.stately.modules.web.jsf.JsfMsg;
import com.statelyhub.elections.constants.ElectionType;
import com.statelyhub.elections.constants.ResultStatus;
import com.statelyhub.elections.constants.SubmissionStatus;
import com.statelyhub.elections.constants.UserDomain;
import com.statelyhub.elections.entities.ConstituencyElection;
import com.statelyhub.elections.entities.ElectionPollingStation;
import com.statelyhub.elections.entities.PollingStationResult;
import com.statelyhub.elections.entities.PollingStationResultSet;
import com.statelyhub.elections.entities.ResultSubmission;
import com.statelyhub.elections.entities.SubmittedResult;
import com.statelyhub.elections.model.ElectionTypeResult;
import com.statelyhub.elections.services.CrudService;
import com.statelyhub.elections.web.PollingStationSearch;
import com.statelyhub.elections.services.ElectionResultService;
import com.statelyhub.elections.services.ElectionService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author edwin
 */
@SessionScoped
@Named(value = "resultSubmissionController")
public class ResultSubmissionController implements Serializable {

    @Inject
    private CrudService crudService;
    
        @Inject
    private ElectionService electionService;

    @Inject
    private UserSession userSession;

    @Inject
    private ElectionResultService electionResultService;

    @Inject
    private PollingStationSearch pollingStationSearch;

    private ElectionPollingStation electionPollingStation;

//    private List<PollingStationResult> stationResultList;
    private List<PollingStationResult> electionResultsList;
//    private List<PollingStationResult> parliamentaryList;
    
      PollingStationResultSet selectedPollingStationResultSet;

//    private List<ElectionTypeResult> resultsList;

    private List<ElectionTypeResult> constituencyResultList;

    private List<ResultSubmission> submissionsList;
    private List<ResultSubmission> unprocessedSubmissionsList = new LinkedList<>();
    private List<ResultSubmission> processedSubmissionsList = new LinkedList<>();

    private List<ElectionPollingStation> pollingStationsList;

    private List<ElectionPollingStation> completedPollingStationsList;
    private List<ElectionPollingStation> pendingPollingStationsList;
    
    private List<SubmittedResult> submittedResultsList;

    private ConstituencyElection selectedConstituencyElection;
    private ResultSubmission selectedSubmission;
    
    private boolean inputVotes;
    
    @PostConstruct
    public void init()
    {
        if(userSession.getAccountUR().getUserDomain() == UserDomain.CONSTITUENCY)
        {
            selectedConstituencyElection = QryBuilder.get(crudService.getEm(), ConstituencyElection.class)
                    .addObjectParam(ConstituencyElection._constituency, userSession.getAccountUR().getConstituency())
                    .getSingleResult(ConstituencyElection.class);
            
            System.out.println("..... " + selectedConstituencyElection);
        }
        
        referesh();
    }
    
    public void referesh()
    {
        pollingStationsList = QryBuilder.get(crudService.getEm(), ElectionPollingStation.class)
                .addObjectParam(ElectionPollingStation._election, selectedConstituencyElection.getElection())
                .orderByDesc(ElectionPollingStation._pollingStation_stationCode)
                .buildQry().getResultList();
        
        loadUnProcessedSubmissions();
        
    }
    
    public void acceptResult()
    {
      
        
        selectedPollingStationResultSet.setTotalVotesCast(selectedSubmission.getTotalVotesCast());
        selectedPollingStationResultSet.setRejectedBallots(selectedSubmission.getRejectedBallots());
        
        
        Map<String, Integer> resultMap = new LinkedHashMap<>();
        for (SubmittedResult result : submittedResultsList) {
            resultMap.put(result.getPollingStationResult().getId(), result.getSubmittedResult());
        }
        System.out.println("map ... " + resultMap);

//        List<PollingStationResult> stationResultsList = PollingStationResultContainer.stationResult(resultsList);

        for (PollingStationResult pollingStationResult : electionResultsList) {
            Integer votes = resultMap.get(pollingStationResult.getId());
            if (votes != null) {
                pollingStationResult.setSubmittedResult(votes);

                crudService.saveEntity(pollingStationResult);
            }

        }
        electionPollingStation.setResultSubmissionId(selectedSubmission.getId());

        selectedSubmission.setSubmissionStatus(SubmissionStatus.LOCKED);
        
        PollingStationResultSet resultSet = electionService.init(electionPollingStation, selectedSubmission.getElectionType());
        resultSet.setValidVotes(selectedSubmission.getValidVotes());
        resultSet.setRejectedBallots(selectedSubmission.getRejectedBallots());
        resultSet.setSpoiltBallots(selectedSubmission.getSpoiltBallots());
        crudService.save(resultSet);
        
//        electionResultService.updateResultSet(selectedSubmission, electionPollingStation);

        crudService.saveEntity(selectedSubmission);
        crudService.saveEntity(electionPollingStation);
        
        unprocessedSubmissionsList.remove(selectedSubmission);
        processedSubmissionsList.add(selectedSubmission);

        JsfMsg.msg(true);
        System.out.println("/// // reslt accepted");
        
    }
    
    public void rejectResult()
    {
        selectedSubmission.setSubmissionStatus(SubmissionStatus.LOCKED);
        crudService.saveEntity(selectedSubmission);
    }
    
    public void searchPollingStation() {
        selectPollingStation(pollingStationSearch.getPollingStation());
    }

    public void selectPollingStation(ElectionPollingStation station) {
        this.electionPollingStation = station;

        
        loadSubmissions();
    }
    
    public void pickSubmission(ResultSubmission resultSubmission)
    {
        this.selectedSubmission = resultSubmission;
        System.out.println("resultSubmission.getSubmissionPictureImageFormat() = "+resultSubmission.getSubmissionPictureImageFormat());
        System.out.println("resultSubmission.getSubmissionPicture() = "+resultSubmission.getSubmissionPicture()); 
        System.out.println("resultSubmission.getSubmissionPictureSRC() = "+resultSubmission.getSubmissionPictureSRC());
        
        selectedPollingStationResultSet = electionService.init(selectedSubmission.getElectionPollingStation(), selectedSubmission.getElectionType());
        electionPollingStation = selectedSubmission.getElectionPollingStation();
        
        submittedResultsList = QryBuilder.get(crudService.getEm(), SubmittedResult.class)
                .addObjectParam(SubmittedResult._resultSubmission, resultSubmission)
                .orderByAsc(SubmittedResult._viewOrder)
                .buildQry().getResultList();
        
        
        
        electionResultsList =  QryBuilder.get(crudService.getEm(), PollingStationResult.class)
                .addObjectParam(PollingStationResult._electionType, resultSubmission.getElectionType())
                .addObjectParam(PollingStationResult._electionPollingStation, resultSubmission.getElectionPollingStation())
                .orderByAsc(PollingStationResult._viewOrder)
                .buildQry().getResultList();
        
//        selectPollingStation(resultSubmission.getElectionPollingStation());
    }
    
    public void loadUnProcessedSubmissions()
    {
        submissionsList  = QryBuilder.get(crudService.getEm(), ResultSubmission.class)
                .addObjectParam(ResultSubmission._constituencyElection, selectedConstituencyElection)
                .buildQry().getResultList();
        
        unprocessedSubmissionsList = new LinkedList<>();
        processedSubmissionsList = new LinkedList<>();
        
        for (ResultSubmission submission : submissionsList) {
            if(submission.getSubmissionStatus() == SubmissionStatus.LOCKED)
            {
                processedSubmissionsList.add(submission);
            }
            else if(submission.getSubmissionStatus() == SubmissionStatus.OPEN)
            {
                unprocessedSubmissionsList.add(submission);
            }
        }
        
        
    }

    public void loadPollingStation() {
        pollingStationsList = QryBuilder.get(crudService.getEm(), ElectionPollingStation.class)
                .addObjectParam(ElectionPollingStation._election, selectedConstituencyElection.getElection())
                .orderByDesc(ElectionPollingStation._pollingStation_stationCode)
                .buildQry().getResultList();

        completedPollingStationsList = new LinkedList<>();
        pendingPollingStationsList = new LinkedList<>();

        for (ElectionPollingStation eps : pollingStationsList) {
            if (eps.getResultStatus() == ResultStatus.FINALISED) {
                completedPollingStationsList.add(eps);
            } else  {
                pendingPollingStationsList.add(eps);
            }
        }

        loadConstituencyResult();
        
        loadUnProcessedSubmissions();
    }

    public void loadConstituencyResult() {
        constituencyResultList = electionResultService.constituency(selectedConstituencyElection);
    }
    
      public void updateConstituencyElectionStatus()
    {
        crudService.save(selectedConstituencyElection);
        JsfMsg.msg(true);
    }
    
    public void updateResultSource()
    {
        electionResultService.updatePollingStationSourceChange(selectedConstituencyElection);
        updateConstituecyFigures();
//        JsfMsg.msg(true);
    }
    
      public void updateConstituecyFigures()
    {
        if(selectedConstituencyElection == null)
        {
            return;
        }
        electionResultService.runConstituency(selectedConstituencyElection);
        loadConstituencyResult();
        JsfMsg.msg(true);
    }

    

    public void loadSubmissions() {
        submissionsList = QryBuilder.get(crudService.getEm(), ResultSubmission.class)
                .addObjectParam(ResultSubmission._electionPollingStation, electionPollingStation)
                .buildQry().getResultList();

        List<ElectionTypeResult> electionTypeResultsList = new LinkedList<>();

        for (ResultSubmission resultSubmission : submissionsList) {
            List<SubmittedResult> results = QryBuilder.get(crudService.getEm(), SubmittedResult.class)
                    .addObjectParam(SubmittedResult._resultSubmission, resultSubmission)
                    .orderByAsc(SubmittedResult._viewOrder)
                    .buildQry().getResultList();

            Map<ElectionType, List<SubmittedResult>> map = results.stream().collect(Collectors.groupingBy(SubmittedResult::getElectionType));

            for (Map.Entry<ElectionType, List<SubmittedResult>> entry : map.entrySet()) {

                ElectionTypeResult electionTypeResult = new ElectionTypeResult();
                electionTypeResult.setElectionType(entry.getKey());
                electionTypeResult.setSubmittedResultsList(entry.getValue());
                electionTypeResultsList.add(electionTypeResult);
            }

            resultSubmission.setElectionResultsList(electionTypeResultsList);
        }
    }
    
    public void updatePollingStatationStatus()
    {
        crudService.save(electionPollingStation);
        JsfMsg.msg(true);
    }

//    public void acceptSubmission(ResultSubmission resultSubmission) {
//
//        List<SubmittedResult> results = PollingStationResultContainer.submitedResults(resultSubmission.getElectionResultsList());
//
//        Map<String, Integer> resultMap = new LinkedHashMap<>();
//        for (SubmittedResult result : results) {
//            resultMap.put(result.getPollingStationResult().getId(), result.getInputResult());
//        }
//
//        List<PollingStationResult> stationResultsList = PollingStationResultContainer.stationResult(resultsList);
//
//        for (PollingStationResult pollingStationResult : stationResultsList) {
//            Integer votes = resultMap.get(pollingStationResult.getId());
//            if (votes != null) {
//                pollingStationResult.setSubmittedResult(votes);
//
//                crudService.saveEntity(pollingStationResult);
//            }
//
//        }
//        electionPollingStation.setResultSubmissionId(resultSubmission.getId());
//
//        resultSubmission.setSubmissionStatus(SubmissionStatus.LOCKED);
//        
//        electionResultService.updateResultSet(resultSubmission, electionPollingStation);
//
//        crudService.saveEntity(resultSubmission);
//        crudService.saveEntity(electionPollingStation);
//
////        System.out.println("ress ..... " + resultSubmission);
//
//        JsfMsg.msg(true);
//    }
    
    public void recordUpdated(PollingStationResult result)
    {
         System.out.println( 
                        result.getPartyDetails()+" -"
                                +"--result.getInputResult() = "+result.getInputResult()
                                +"--result.getOfficialResult() = "+result.getOfficialResult());
         crudService.save(result);   
    }
    
    public void saveResults()
    {
        if(electionResultsList == null) return;
//        
//        for(ElectionTypeResult record: resultsList)
//        {
//            System.out.println("\n--record.getElectionType() = "+record.getElectionType());
            for (PollingStationResult result : electionResultsList) 
            {
                System.out.println( 
                        result.getPartyDetails()+" -"
                                +"--result.getInputResult() = "+result.getInputResult()
                                +"--result.getOfficialResult() = "+result.getOfficialResult());
                crudService.save(result);
            }
//        }
        
        JsfMsg.successSave();
        this.inputVotes = false;
    }

//    public List<PollingStationResult> getPresidentialList() {
//        return presidentialList;
//    }
//
//    public List<PollingStationResult> getParliamentaryList() {
//        return parliamentaryList;
//    }
//
//    public List<ElectionTypeResult> getResultsList() {
//        return resultsList;
//    }
//
//    public void setResultsList(List<ElectionTypeResult> resultsList) {
//        this.resultsList = resultsList;
//    }

    public ElectionPollingStation getElectionPollingStation() {
        return electionPollingStation;
    }

    public void setElectionPollingStation(ElectionPollingStation electionPollingStation) {
        this.electionPollingStation = electionPollingStation;
    }

    public List<ResultSubmission> getSubmissionsList() {
        return submissionsList;
    }

    public void setSubmissionsList(List<ResultSubmission> submissionsList) {
        this.submissionsList = submissionsList;
    }

    public ConstituencyElection getSelectedConstituencyElection() {
        return selectedConstituencyElection;
    }

    public void setSelectedConstituencyElection(ConstituencyElection selectedConstituencyElection) {
        this.selectedConstituencyElection = selectedConstituencyElection;
    }

    public List<ElectionPollingStation> getPollingStationsList() {
        return pollingStationsList;
    }

    public void setPollingStationsList(List<ElectionPollingStation> pollingStationsList) {
        this.pollingStationsList = pollingStationsList;
    }

    public List<ElectionPollingStation> getCompletedPollingStationsList() {
        return completedPollingStationsList;
    }

    public List<ElectionPollingStation> getPendingPollingStationsList() {
        return pendingPollingStationsList;
    }

    public List<ElectionTypeResult> getConstituencyResultList() {
        return constituencyResultList;
    }

    public void setConstituencyResultList(List<ElectionTypeResult> constituencyResultList) {
        this.constituencyResultList = constituencyResultList;
    }

    public boolean isInputVotes() {
        return inputVotes;
    }

    public void setInputVotes(boolean inputVotes) {
        this.inputVotes = inputVotes;
    }

    public List<ResultSubmission> getUnprocessedSubmissionsList() {
        return unprocessedSubmissionsList;
    }

    public ResultSubmission getSelectedSubmission() {
        return selectedSubmission;
    }

    public List<SubmittedResult> getSubmittedResultsList() {
        return submittedResultsList;
    }

    public List<PollingStationResult> getElectionResultsList() {
        return electionResultsList;
    }

    public List<ResultSubmission> getProcessedSubmissionsList() {
        return processedSubmissionsList;
    }
    
    

}
