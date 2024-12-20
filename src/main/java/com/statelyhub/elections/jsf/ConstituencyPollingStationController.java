/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.jsf;

import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.elections.constants.UserDomain;
import com.statelyhub.elections.entities.ConstituencyElection;
import com.statelyhub.elections.entities.ElectionPollingStation;
import com.statelyhub.elections.entities.SubmittedResultPicture;
import com.statelyhub.elections.model.ElectionTypeResult;
import com.statelyhub.elections.services.CrudService;
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
//@SessionScoped
//@Named(value = "constituencyPollingStationController")
public class ConstituencyPollingStationController implements Serializable {

    @Inject
    private CrudService crudService;
    @Inject
    private UserSession userSession;

    @Inject
    private ElectionResultService electionResultService;

    private ConstituencyElection selectedConstituencyElection;

    private List<ElectionPollingStation> pollingStationsList;

    private ElectionPollingStation selectedElectionPollingStation;


    @PostConstruct
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
        if (selectedConstituencyElection == null) {
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

}
