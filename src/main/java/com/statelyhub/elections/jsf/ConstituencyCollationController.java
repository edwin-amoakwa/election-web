/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.jsf;

import com.stately.modules.jpa2.QryBuilder;
import com.stately.modules.web.jsf.JsfMsg;
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

    private ElectionPollingStation electionPollingStation;

//    private List<PollingStationResult> stationResultList;
    private List<PollingStationResult> presidentialList;
    private List<PollingStationResult> parliamentaryList;

    private List<ElectionTypeResult> resultsList;

    private List<ElectionTypeResult> constituencyResultList;

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
                .addObjectParam(ResultSubmission._submissionStatus, SubmissionStatus.ACCEPTED)
                .printQryInfo()
                .buildQry().getResultList();

        loadConstituencyResult();
    }

    public void selectResultSet(PollingStationResultSet station) {
        this.selectedResultSet = station;
        electionPollingStation = station.getElectionPollingStation();

        electionResultsList = electionService.eps(electionPollingStation, station.getElectionType());

    }

    
    public void pickSubmission(ResultSubmission resultSubmission) 
    {
        this.selectedSubmission = resultSubmission;
        selectedResultSet = crudService.find(PollingStationResultSet.class, selectedSubmission.getResultSetId());
        
        selectResultSet(selectedResultSet);
        
        System.out.println("....... " +selectedResultSet);

//        System.out.println("resultSubmission.getSubmissionPictureImageFormat() = " + resultSubmission.getSubmissionPictureImageFormat());
//        System.out.println("resultSubmission.getSubmissionPicture() = " + resultSubmission.getSubmissionPicture());
//        System.out.println("resultSubmission.getSubmissionPictureSRC() = " + resultSubmission.getSubmissionPictureSRC());

//        selectedPollingStationResultSet = electionService.init(selectedSubmission.getElectionPollingStation(), selectedSubmission.getElectionType());
//        electionPollingStation = selectedSubmission.getElectionPollingStation();
//        
//        submittedResultsList = QryBuilder.get(crudService.getEm(), SubmittedResult.class)
//                .addObjectParam(SubmittedResult._resultSubmission, resultSubmission)
//                .orderByAsc(SubmittedResult._viewOrder)
//                .buildQry().getResultList();
//        
//        
//        
//        electionResultsList =  QryBuilder.get(crudService.getEm(), PollingStationResult.class)
//                .addObjectParam(PollingStationResult._electionType, resultSubmission.getElectionType())
//                .addObjectParam(PollingStationResult._electionPollingStation, resultSubmission.getElectionPollingStation())
//                .orderByAsc(PollingStationResult._viewOrder)
//                .buildQry().getResultList();
//        selectPollingStation(resultSubmission.getElectionPollingStation());
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
        constituencyResultList = electionResultService.constituency(selectedConstituencyElection);
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
        JsfMsg.msg(true);
    }
    
    public void rejectResult()
    {
        
    }

    public void approveSubmission() {
        selectedResultSet.setResultStatus(ResultStatus.FINALISED);
        crudService.save(selectedResultSet);

        if (selectedResultSet.getResultStatus() == ResultStatus.FINALISED) {
            electionResultService.updateSource(electionResultsList);

            completedList.add(selectedResultSet);
            pendingList.remove(selectedResultSet);
        }
        
        if(selectedSubmission != null)
        {
            selectedSubmission.setCollated(true);
            crudService.save(selectedSubmission);
            completedSubmissionList.remove(selectedSubmission);
        }

        JsfMsg.msg(true);
    }

    public void recordUpdated(PollingStationResult result) {
        System.out.println(
                result.getPartyDetails() + " -"
                + "--result.getInputResult() = " + result.getInputResult()
                + "--result.getOfficialResult() = " + result.getOfficialResult());
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

    public List<ElectionTypeResult> getConstituencyResultList() {
        return constituencyResultList;
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

    public List<PollingStationResult> getElectionResultsList() {
        return electionResultsList;
    }

    public List<ResultSubmission> getCompletedSubmissionList() {
        return completedSubmissionList;
    }

    public ResultSubmission getSelectedSubmission() {
        return selectedSubmission;
    }

}
