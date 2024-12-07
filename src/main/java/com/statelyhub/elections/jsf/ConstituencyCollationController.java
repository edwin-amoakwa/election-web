/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.jsf;

import com.stately.modules.jpa2.QryBuilder;
import com.stately.modules.web.jsf.JsfMsg;
import com.statelyhub.elections.constants.ElectionType;
import com.statelyhub.elections.constants.ResultSource;
import com.statelyhub.elections.constants.ResultStatus;
import com.statelyhub.elections.constants.SubmissionStatus;
import com.statelyhub.elections.constants.UserDomain;
import com.statelyhub.elections.entities.ConstituencyElection;
import com.statelyhub.elections.entities.ElectionPollingStation;
import com.statelyhub.elections.entities.PollingStationResult;
import com.statelyhub.elections.entities.PollingStationResultSet;
import com.statelyhub.elections.entities.ResultSubmission;
import com.statelyhub.elections.entities.SubmittedResultPicture;
import com.statelyhub.elections.model.ElectionTypeResult;
import com.statelyhub.elections.services.CrudService;
import com.statelyhub.elections.services.DashboardService;
import com.statelyhub.elections.web.PollingStationSearch;
import com.statelyhub.elections.services.ElectionResultService;
import com.statelyhub.elections.services.ElectionService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author edwin
 */
@SessionScoped
@Named(value = "constituencyCollationController")
public class ConstituencyCollationController implements Serializable {

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
    
    
    @Inject
    private DashboardService dashboardService;

    private ElectionPollingStation electionPollingStation;

//    private List<PollingStationResult> stationResultList;
    private List<PollingStationResult> presidentialList;
    private List<PollingStationResult> parliamentaryList;
    
           private List<SubmittedResultPicture> submittedResultImagesList = new LinkedList<>();

    private List<ElectionTypeResult> resultsList;
    
    private ElectionTypeResult presidential;
    private ElectionTypeResult parliamentary;

//    private List<ElectionTypeResult> constituencyResultList;

    private List<ElectionPollingStation> pollingStationsList;

    private List<ElectionPollingStation> completedPollingStationsList;
    private List<ElectionPollingStation> pendingPollingStationsList;

    private List<PollingStationResultSet> completedList;
    private List<PollingStationResultSet> pendingList;

    private List<PollingStationResult> electionResultsList;
    private List<ResultSubmission> completedSubmissionList;
    private ResultSubmission selectedSubmission;

    private ConstituencyElection selectedConstituencyElection;
    private PollingStationResultSet selectedResultSet;

    private boolean inputVotes;

    @PostConstruct
    public void init() {
        if (userSession.getAccountUR().getUserDomain() == UserDomain.CONSTITUENCY) {
            selectedConstituencyElection = electionService.election(userSession.getAccountUR().getConstituency(), electionService.getCurrentElection());

            load();
        }

    }

    public void load() {
        List<PollingStationResultSet> list = QryBuilder.get(crudService.getEm(), PollingStationResultSet.class)
                .addObjectParam(PollingStationResultSet._electionPollingStation_constituencyElection, selectedConstituencyElection)
                .printQryInfo()
                .buildQry().getResultList();

//         System.out.println("...... " + list);
        pendingList = new LinkedList<>();
        completedList = new LinkedList<>();

        for (PollingStationResultSet item : list) {
            if (item.getResultStatus() == ResultStatus.FINALISED) {
                completedList.add(item);
            } else {
                pendingList.add(item);
            }

        }

        completedSubmissionList = QryBuilder.get(crudService.getEm(), ResultSubmission.class)
                .addObjectParam(ResultSubmission._constituencyElection, selectedConstituencyElection)
                .addObjectParam(ResultSubmission._submissionStatus, SubmissionStatus.ACCEPTED)
                .addObjectParam(ResultSubmission._collated, false)
                .printQryInfo()
                .buildQry().getResultList();

        loadConstituencyResult();
    }
    
    
    public void loadUnCoallatedSubmissions() {
      

        completedSubmissionList = QryBuilder.get(crudService.getEm(), ResultSubmission.class)
                .addObjectParamWhenNotNull(ResultSubmission._constituencyElection, selectedConstituencyElection)
                .addObjectParam(ResultSubmission._submissionStatus, SubmissionStatus.ACCEPTED)
                .addObjectParam(ResultSubmission._collated, false)
                .printQryInfo()
                .buildQry().getResultList();

        loadConstituencyResult();
    }
    
    
    

    public void selectResultSet(PollingStationResultSet station) {
        this.selectedResultSet = station;
        electionPollingStation = station.getElectionPollingStation();
        
        inputVotes = false;

        electionResultsList = electionService.eps(electionPollingStation, station.getElectionType());
        
        String submissionId = selectedResultSet.getElectionPollingStation().getParliamentarySubmissionId();
        if(selectedResultSet.getElectionType() == ElectionType.PRESIDENTIAL)
        {
            submissionId = selectedResultSet.getElectionPollingStation().getPresidentialSubmissionId();
        }
        
        submittedResultImagesList = QryBuilder.get(crudService.getEm(), SubmittedResultPicture.class)
                .addObjectParam(SubmittedResultPicture._resultSubmission_id, submissionId)
                .orderByAsc(SubmittedResultPicture._createdDate)
                .printQryInfo()
                .buildQry().getResultList();
        
        System.out.println(".... imagess... " + submittedResultImagesList.size());

    }

    
    public void pickSubmission(ResultSubmission resultSubmission) 
    {
        this.selectedSubmission = resultSubmission;
        selectedResultSet = crudService.find(PollingStationResultSet.class, selectedSubmission.getResultSetId());
        
        selectResultSet(selectedResultSet);
        
        System.out.println("....... " +selectedResultSet);

        
    }

    public void searchPollingStation() {
        selectPollingStation(pollingStationSearch.getPollingStation());
    }

    public void selectPollingStation(ElectionPollingStation station) {
        this.electionPollingStation = station;

        resultsList = electionResultService.pollingStationBucket(station);

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
            } else {
                pendingPollingStationsList.add(eps);
            }
        }

    }

    public void loadConstituencyResult() {
        
        presidential = electionResultService.constituencyType(selectedConstituencyElection, ElectionType.PRESIDENTIAL);
        parliamentary = electionResultService.constituencyType(selectedConstituencyElection, ElectionType.PARLIAMENTARY);
        
        parliamentary.setDashboard(dashboardService.dashboard(ElectionType.PARLIAMENTARY, selectedConstituencyElection));
        presidential.setDashboard(dashboardService.dashboard(ElectionType.PRESIDENTIAL, selectedConstituencyElection));
        
//        constituencyResultList = electionResultService.constituency(selectedConstituencyElection);
    }

    public void updateConstituencyElectionStatus() {
        crudService.save(selectedConstituencyElection);
        JsfMsg.msg(true);
    }

    public void updateResultSource() {
        electionResultService.updatePollingStationSourceChange(selectedConstituencyElection);
        updateConstituecyFigures();
//        JsfMsg.msg(true);
    }

    public void updateConstituecyFigures() {
        if (selectedConstituencyElection == null) {
            return;
        }
        electionResultService.runConstituency(selectedConstituencyElection);
        loadConstituencyResult();
        JsfMsg.info("Updated");
    }
    
  
    public void approveSubmission() {
        
        if(inputVotes)
        {
            selectedResultSet.setResultSource(ResultSource.INPUTTED);
        }
        
        selectedResultSet.setResultStatus(ResultStatus.FINALISED);
        crudService.save(selectedResultSet);

 
            electionResultService.updateSource(selectedResultSet, electionResultsList);

            completedList.add(selectedResultSet);
            pendingList.remove(selectedResultSet);
        
        
        if(selectedSubmission != null)
        {
            selectedSubmission.setCollated(true);
            crudService.save(selectedSubmission);
            completedSubmissionList.remove(selectedSubmission);
        }

//        JsfMsg.msg(true);
        updateConstituecyFigures();
    }

    public void recordUpdated(PollingStationResult result) {
        System.out.println(
                result.getPartyDetails() + " -"
                + "--result.getInputResult() = " + result.getInputResult()
                + "--result.getOfficialResult() = " + result.getOfficialResult());
        
        if(selectedResultSet.getResultSource() == ResultSource.INPUTTED)
        {
            result.setAcceptedResult(result.getInputResult());
        }
        else  if(selectedResultSet.getResultSource() == ResultSource.OFFICIAL)
        {
            result.setAcceptedResult(result.getOfficialResult());
        }
        
        crudService.save(result);
    }

    public void saveResults() {
        if (resultsList == null) {
            return;
        }

        for (ElectionTypeResult record : resultsList) {
            System.out.println("\n--record.getElectionType() = " + record.getElectionType());
            for (PollingStationResult result : record.getVotingsList()) {
                System.out.println(
                        result.getPartyDetails() + " -"
                        + "--result.getInputResult() = " + result.getInputResult()
                        + "--result.getOfficialResult() = " + result.getOfficialResult());
                crudService.save(result);
            }
        }

        JsfMsg.successSave();
        this.inputVotes = false;
    }

    public List<PollingStationResult> getPresidentialList() {
        return presidentialList;
    }

    public List<PollingStationResult> getParliamentaryList() {
        return parliamentaryList;
    }

    public List<ElectionTypeResult> getResultsList() {
        return resultsList;
    }

    public void setResultsList(List<ElectionTypeResult> resultsList) {
        this.resultsList = resultsList;
    }

    public ElectionPollingStation getElectionPollingStation() {
        return electionPollingStation;
    }

    public void setElectionPollingStation(ElectionPollingStation electionPollingStation) {
        this.electionPollingStation = electionPollingStation;
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

    

    public boolean isInputVotes() {
        return inputVotes;
    }

    public void setInputVotes(boolean inputVotes) {
        this.inputVotes = inputVotes;
    }

    public List<PollingStationResultSet> getCompletedList() {
        return completedList;
    }

    public List<PollingStationResultSet> getPendingList() {
        return pendingList;
    }

    public PollingStationResultSet getSelectedResultSet() {
        return selectedResultSet;
    }

    public void setSelectedResultSet(PollingStationResultSet selectedResultSet) {
        this.selectedResultSet = selectedResultSet;
    }

    public List<PollingStationResult> getElectionResultsList() {
        return electionResultsList;
    }

    public List<ResultSubmission> getCompletedSubmissionList() {
        return completedSubmissionList;
    }

    public ResultSubmission getSelectedSubmission() {
        return selectedSubmission;
    }

    public ElectionTypeResult getPresidential() {
        return presidential;
    }

    public ElectionTypeResult getParliamentary() {
        return parliamentary;
    }

    public List<SubmittedResultPicture> getSubmittedResultImagesList() {
        return submittedResultImagesList;
    }

    
}
