/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.jsf;

import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.elections.entities.ConstituencyElection;
import com.statelyhub.elections.entities.ElectionPollingStation;
import com.statelyhub.elections.entities.PollingStation;
import com.statelyhub.elections.entities.Region;
import com.statelyhub.elections.services.CrudService;
import com.statelyhub.elections.services.UpdateStatsService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author edwin
 */
@SessionScoped
@Named(value = "electionDetailController")
public class ElectionDetailController implements Serializable{
    
        private @Inject CrudService crudService;

    private @Inject UserSession userSession;
    private @Inject UpdateStatsService updateStatsService;
    
    private Region selectedRegion;
    private ConstituencyElection selectedConstituency;
    private List<ConstituencyElection> constituencyList;
    private List<ElectionPollingStation> pollingStationsList;
    
    
    
    public void selectRegion(Region selectedRegion)
    {
        this.selectedRegion = selectedRegion;
        
        constituencyList = QryBuilder.get(crudService.getEm(), ConstituencyElection.class)
                .addObjectParam(ConstituencyElection._region, selectedRegion)
                .buildQry().getResultList();
        
//        System.out.println("....... " + constituencyList);
    }
    
     
    public void selectConstituency(ConstituencyElection selectedConstituency)
    {
        this.selectedConstituency = selectedConstituency;
        
        pollingStationsList = QryBuilder.get(crudService.getEm(), ElectionPollingStation.class)
                .addObjectParam(ElectionPollingStation._constituency, selectedConstituency.getConstituency())
                .addObjectParam(ElectionPollingStation._election, userSession.getElectionUR())
                .buildQry().getResultList();
    }
    
        
    public void selectPollingStation(PollingStation pollingStation)
    {
//        this.pollingStation = pollingStation;
//        
//        pollingStationsList = QryBuilder.get(crudService.getEm(), ElectionPollingStation.class)
//                .addObjectParam(ElectionPollingStation._constituency, selectedConstituency.getConstituency())
//                .addObjectParam(ElectionPollingStation._election, userSession.getElectionUR())
//                .buildQry().getResultList();
    }
    
    public void updateStats()
    {
        updateStatsService.update(userSession.getElectionUR());
    }
    
        public void initIaliseDefaultContesttants()
    {
        updateStatsService.initIaliseDefaultContesttants(userSession.getElectionUR());
    }

    public Region getSelectedRegion() {
        return selectedRegion;
    }

    public ConstituencyElection getSelectedConstituency() {
        return selectedConstituency;
    }

    public List<ConstituencyElection> getConstituencyList() {
        return constituencyList;
    }

    public List<ElectionPollingStation> getPollingStationsList() {
        return pollingStationsList;
    }
    
    
    
    
}
