/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.jsf;

import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.elections.constants.ElectionType;
import com.statelyhub.elections.entities.ElectionPollingStation;
import com.statelyhub.elections.entities.PollingStationResult;
import com.statelyhub.elections.model.ElectionTypeResult;
import com.statelyhub.elections.services.CrudService;
import com.statelyhub.elections.web.PollingStationSearch;
import com.statelyhub.old.service.ElectionResultService;
import com.statelyhub.old.service.ElectionService;
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
@Named(value = "pollingStationCollationController")
public class PollingStationCollationController implements Serializable {

    @Inject
    private CrudService crudService;

    @Inject
    private ElectionService electionService;

    @Inject
    private ElectionResultService electionResultService;

    @Inject
    private PollingStationSearch pollingStationSearch;

    private ElectionPollingStation electionPollingStation;

    private List<PollingStationResult> stationResultList;

    private List<PollingStationResult> presidentialList;
    private List<PollingStationResult> parliamentaryList;

    private List<ElectionTypeResult> resultsList;

    public void searchPollingStation() {
        selectPollingStation(pollingStationSearch.getPollingStation());
    }

    public void selectPollingStation(ElectionPollingStation station) {
        this.electionPollingStation = station;

        stationResultList = QryBuilder.get(crudService.getEm(), ElectionPollingStation.class)
                .addObjectParam(ElectionPollingStation._election, station.getElection())
                .addObjectParam(ElectionPollingStation._pollingStation, station.getPollingStation())
                .buildQry().getResultList();

//        presidentialList = electionService.eps(station, ElectionType.PRESIDENTIAL);
//        parliamentaryList = electionService.eps(station, ElectionType.PARLIAMENTARY);
//
//        ElectionTypeResult typeResult = new ElectionTypeResult();
//        typeResult.setElectionType(ElectionType.PRESIDENTIAL);
//        typeResult.setResultsList(stationResultList);

        resultsList = electionResultService.pollingStationBucket(station);

        System.out.println(".... " + presidentialList);
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

}
