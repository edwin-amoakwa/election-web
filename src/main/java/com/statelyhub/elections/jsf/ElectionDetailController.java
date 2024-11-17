/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.jsf;

import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.elections.constants.ElectionType;
import com.statelyhub.elections.entities.ConstituencyElection;
import com.statelyhub.elections.entities.ElectionPollingStation;
import com.statelyhub.elections.entities.PartyElection;
import com.statelyhub.elections.entities.PollingStation;
import com.statelyhub.elections.entities.Region;
import com.statelyhub.elections.services.CrudService;
import com.statelyhub.elections.services.DataUploadService;
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
public class ElectionDetailController implements Serializable {

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

        List<ConstituencyElection> constituencys = QryBuilder.get(crudService.getEm(), ConstituencyElection.class)
                .addObjectParam(ConstituencyElection._election, userSession.getElectionUR()).buildQry().getResultList();

        List<PartyElection> partyElectionsList = QryBuilder.get(crudService.getEm(), PartyElection.class)
                .addObjectParam(PartyElection._election, userSession.getElectionUR()).printQryInfo().buildQry().getResultList();

        for (ConstituencyElection constituency : constituencys) {
//            System.out.println("\n==turn of constituency = "+constituency.getConstituency().getConstituencyName());

            for (PartyElection partyElection : partyElectionsList) {
//                System.out.println("--turn of party = "+partyElection.getParty().getInitials());
                updateStatsService.initConstituencyContestants(constituency, partyElection, ElectionType.PRESIDENTIAL);
                updateStatsService.initConstituencyContestants(constituency, partyElection, ElectionType.PARLIAMENTARY);
//                updateStatsService.initConstituencyContestants(constituency, partyElection);
            }

        }


//        List<PartyElection> partyElectionsList = QryBuilder.get(crudService.getEm(), PartyElection.class)
//                .addObjectParam(PartyElection._election, userSession.getElectionUR()).printQryInfo().buildQry().getResultList();
//
////        for (ConstituencyElection constituency : constituencys) {
////            System.out.println("\n==turn of constituency = "+constituency.getConstituency().getConstituencyName());
//
//            for (PartyElection partyElection : partyElectionsList) {
////                System.out.println("--turn of party = "+partyElection.getParty().getInitials());
//                updateStatsService.initIaliseDefaultContesttants(partyElection, userSession.getElectionUR());
////                updateStatsService.initConstituencyContestants(constituency, partyElection, ElectionType.PARLIAMENTARY);
////                updateStatsService.initConstituencyContestants(constituency, partyElection);
//            }



//        updateStatsService.initIaliseDefaultContesttants(userSession.getElectionUR());
    }
    
        public void initiaiseConsiituency(ConstituencyElection constituencyElection) {

//        List<ConstituencyElection> constituencys = QryBuilder.get(crudService.getEm(), ConstituencyElection.class)
//                .addObjectParam(ConstituencyElection._election, userSession.getElectionUR()).buildQry().getResultList();

        List<PartyElection> partyElectionsList = QryBuilder.get(crudService.getEm(), PartyElection.class)
                .addObjectParam(PartyElection._election, userSession.getElectionUR()).printQryInfo().buildQry().getResultList();

//        for (ConstituencyElection constituency : constituencyElection) {
//            System.out.println("\n==turn of constituency = "+constituency.getConstituency().getConstituencyName());

            for (PartyElection partyElection : partyElectionsList) {
//                System.out.println("--turn of party = "+partyElection.getParty().getInitials());
                updateStatsService.initConstituencyContestants(constituencyElection, partyElection, ElectionType.PRESIDENTIAL);
                updateStatsService.initConstituencyContestants(constituencyElection, partyElection, ElectionType.PARLIAMENTARY);
//                updateStatsService.initConstituencyContestants(constituency, partyElection);
            }

            System.out.println("... comppleted ....");
//        }


//        List<PartyElection> partyElectionsList = QryBuilder.get(crudService.getEm(), PartyElection.class)
//                .addObjectParam(PartyElection._election, userSession.getElectionUR()).printQryInfo().buildQry().getResultList();
//
////        for (ConstituencyElection constituency : constituencys) {
////            System.out.println("\n==turn of constituency = "+constituency.getConstituency().getConstituencyName());
//
//            for (PartyElection partyElection : partyElectionsList) {
////                System.out.println("--turn of party = "+partyElection.getParty().getInitials());
//                updateStatsService.initIaliseDefaultContesttants(partyElection, userSession.getElectionUR());
////                updateStatsService.initConstituencyContestants(constituency, partyElection, ElectionType.PARLIAMENTARY);
////                updateStatsService.initConstituencyContestants(constituency, partyElection);
//            }



//        updateStatsService.initIaliseDefaultContesttants(userSession.getElectionUR());
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
