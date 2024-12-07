/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.jsf;

import com.stately.modules.jpa2.QryBuilder;
import com.stately.modules.jpa2.UniqueEntityModel3;
import com.statelyhub.elections.constants.ElectionType;
import com.statelyhub.elections.entities.ConstituencyElection;
import com.statelyhub.elections.entities.ElectionPollingStation;
import com.statelyhub.elections.entities.PartyElection;
import com.statelyhub.elections.entities.PollingStation;
import com.statelyhub.elections.entities.Region;
import com.statelyhub.elections.services.CrudService;
import com.statelyhub.elections.services.DataUploadService;
import com.statelyhub.elections.services.UpdateStatsService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author edwin
 */
//@SessionScoped
@RequestScoped
@Named(value = "consituencyController")
public class ConsituencyController implements Serializable {

    private @Inject
    CrudService crudService;

    @Inject
    private UserSession userSession;
    @Inject
    private UpdateStatsService updateStatsService;

    @Inject
    private DataUploadService dataUploadService;

    private Region selectedRegion;
    private ConstituencyElection selectedConstituency;
    private List<ConstituencyElection> constituencyList;
    private List<ElectionPollingStation> pollingStationsList;
    
    @PostConstruct
    public void init()
    {
        constituencyList = QryBuilder.get(crudService.getEm(), ConstituencyElection.class)
//                .orderByAsc(ConstituencyElection._region_regionName)
                .orderByAsc(ConstituencyElection._constituency_constituencyName)
//                .addObjectParam(ConstituencyElection._region, selectedRegion)
                .buildQry().getResultList();
        
        UniqueEntityModel3.countItems(constituencyList);
    }

    public void selectRegion(Region selectedRegion) {
        this.selectedRegion = selectedRegion;

        constituencyList = QryBuilder.get(crudService.getEm(), ConstituencyElection.class)
                .addObjectParam(ConstituencyElection._region, selectedRegion)
                .buildQry().getResultList();

//        System.out.println("....... " + constituencyList);
    }

    public void selectConstituency(ConstituencyElection selectedConstituency) {
        this.selectedConstituency = selectedConstituency;

        pollingStationsList = QryBuilder.get(crudService.getEm(), ElectionPollingStation.class)
                .addObjectParam(ElectionPollingStation._constituency, selectedConstituency.getConstituency())
                .addObjectParam(ElectionPollingStation._election, userSession.getElectionUR())
                .buildQry().getResultList();
    }

    public void selectPollingStation(PollingStation pollingStation) {
//        this.pollingStation = pollingStation;
//        
//        pollingStationsList = QryBuilder.get(crudService.getEm(), ElectionPollingStation.class)
//                .addObjectParam(ElectionPollingStation._constituency, selectedConstituency.getConstituency())
//                .addObjectParam(ElectionPollingStation._election, userSession.getElectionUR())
//                .buildQry().getResultList();
    }

    public void updateStats() {
        updateStatsService.update(userSession.getElectionUR());
    }

    public void initIaliseDefaultContesttants() {

        for (ConstituencyElection constituencyElection : constituencyList) {
            initiaiseConsiituency(constituencyElection);
        }

        
//        List<PartyElection> partyElectionsList = QryBuilder.get(crudService.getEm(), PartyElection.class)
//                .addObjectParam(PartyElection._election, userSession.getElectionUR()).printQryInfo().buildQry().getResultList();
//
////        for (ConstituencyElection constituency : constituencys) {
////            System.out.println("\n==turn of constituency = "+constituency.getConstituency().getConstituencyName());
//        for (PartyElection partyElection : partyElectionsList) {
////                System.out.println("--turn of party = "+partyElection.getParty().getInitials());
//            updateStatsService.initIaliseDefaultContesttants(partyElection, userSession.getElectionUR());
////                updateStatsService.initConstituencyContestants(constituency, partyElection, ElectionType.PARLIAMENTARY);
////                updateStatsService.initConstituencyContestants(constituency, partyElection);
//        }

        
    }

    public void initiaiseConsiituency(ConstituencyElection constituencyElection) {

        List<PartyElection> partyElectionsList = QryBuilder.get(crudService.getEm(), PartyElection.class)
                .addObjectParam(PartyElection._election, userSession.getElectionUR()).printQryInfo().buildQry().getResultList();

        for (PartyElection partyElection : partyElectionsList) {

            updateStatsService.initConstituencyContestants(constituencyElection, partyElection, ElectionType.PRESIDENTIAL);
            updateStatsService.initConstituencyContestants(constituencyElection, partyElection, ElectionType.PARLIAMENTARY);

        }
        
        updateStatsService.updateFigures(constituencyElection);

        System.out.println("... comppleted ....");

    }

    public void deleteRegion(Region region) {
        dataUploadService.deleteRegion(region);
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
