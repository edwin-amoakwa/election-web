/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.jsf;

import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.elections.constants.ElectionType;
import com.statelyhub.elections.constants.UserDomain;
import com.statelyhub.elections.entities.ConstituencyElection;
import com.statelyhub.elections.entities.ElectionContestant;
import com.statelyhub.elections.entities.ElectionPollingStation;
import com.statelyhub.elections.entities.SubmittedResultPicture;
import com.statelyhub.elections.model.ElectionTypeResult;
import com.statelyhub.elections.services.CrudService;
import com.statelyhub.elections.services.DashboardService;
import com.statelyhub.elections.services.ElectionResultService;
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
@Named(value = "stationResultController")
public class PollingStationResultController implements Serializable {

    @Inject
    private CrudService crudService;
    @Inject
    private UserSession userSession;

    @Inject
    private ElectionResultService electionResultService;
    
            @Inject DashboardService dashboardService;
    
    private String constituencyName;
    private String stationName;


    private ConstituencyElection selectedConstituencyElection;

    private List<ElectionPollingStation> pollingStationsList;

    private ElectionPollingStation selectedElectionPollingStation;
    
    
    private ElectionTypeResult presidential;
    private ElectionTypeResult parliamentary;
    
    
    public void search()
    {
                pollingStationsList = QryBuilder.get(crudService.getEm(), ElectionPollingStation.class)
//                .addObjectParam(ElectionPollingStation._constituency, selectedConstituencyElection.getConstituency())
                .addObjectParam(ElectionPollingStation._election, userSession.getElectionUR())
                        .addStringQryParam(ElectionPollingStation._pollingStation_stationName, stationName, QryBuilder.ComparismCriteria.LIKE)
                        .addStringQryParam(ElectionPollingStation._constituency_constituencyName, constituencyName, QryBuilder.ComparismCriteria.LIKE)
                .buildQry().setMaxResults(100).getResultList();
    }
    

    
    
      public void loadConstituencyResult() {
        
        presidential = electionResultService.constituencyType(selectedConstituencyElection, ElectionType.PRESIDENTIAL);
        parliamentary = electionResultService.constituencyType(selectedConstituencyElection, ElectionType.PARLIAMENTARY);
        
        parliamentary.setDashboard(dashboardService.dashboard(ElectionType.PARLIAMENTARY, selectedConstituencyElection));
        presidential.setDashboard(dashboardService.dashboard(ElectionType.PRESIDENTIAL, selectedConstituencyElection));
        
//        constituencyResultList = electionResultService.constituency(selectedConstituencyElection);
    }
    

//    @PostConstruct
    public void init() {
        if (userSession.getAccountUR().getUserDomain() == UserDomain.CONSTITUENCY) {
            selectedConstituencyElection = userSession.getConstituencyElectionUR();

            load();
        }

    }

    public void update() {
        crudService.save(selectedElectionPollingStation);
    }

    public void selectPollingStation(ElectionPollingStation eps) {
        selectedElectionPollingStation = eps;
    }

    public void load() {
        if(selectedConstituencyElection == null)
        {
            return;
        }
        
        pollingStationsList = QryBuilder.get(crudService.getEm(), ElectionPollingStation.class)
                .addObjectParam(ElectionPollingStation._constituency, selectedConstituencyElection.getConstituency())
                .addObjectParam(ElectionPollingStation._election, userSession.getElectionUR())
                .buildQry().getResultList();
    }

    public List<ElectionPollingStation> getPollingStationsList() {
        return pollingStationsList;
    }

    public ElectionPollingStation getSelectedElectionPollingStation() {
        return selectedElectionPollingStation;
    }

    public void setSelectedElectionPollingStation(ElectionPollingStation selectedElectionPollingStation) {
        this.selectedElectionPollingStation = selectedElectionPollingStation;
    }

    public String getConstituencyName() {
        return constituencyName;
    }

    public void setConstituencyName(String constituencyName) {
        this.constituencyName = constituencyName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    
    
    
}
