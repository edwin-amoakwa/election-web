/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.services;

import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.elections.constants.ElectionType;
import com.statelyhub.elections.entities.ConstituencyElection;
import com.statelyhub.elections.entities.ConstituencyResultSet;
import com.statelyhub.elections.entities.Election;
import com.statelyhub.elections.entities.ElectionContestant;
import com.statelyhub.elections.entities.ElectionPollingStation;
import com.statelyhub.elections.entities.PollingStationResult;
import com.statelyhub.elections.entities.PollingStationResultSet;
import com.statelyhub.elections.entities.SubmittedResult;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

/**
 *
 * @author edwin
 */
@Stateless
public class ElectionService {

    @Inject
    private CrudService crudService;

    public Election getCurrentElection() {
        List<Election> elections = crudService.findAll(Election.class);

        return elections.get(0);
    }

    public List<ElectionContestant> cecs(ConstituencyElection constituencyElection, ElectionType electionType) {
        return QryBuilder.get(crudService.getEm(), ElectionContestant.class)
                //                .addObjectParam(ElectionContestant._electionType, electionType)
                .addObjectParam(ElectionContestant._constituencyElection, constituencyElection)
                .orderByAsc(ElectionContestant._viewOrder)
                .buildQry().getResultList();
    }

    public List<PollingStationResult> eps(ElectionPollingStation eps, ElectionType electionType) {
        return QryBuilder.get(crudService.getEm(), PollingStationResult.class)
                .addObjectParam(PollingStationResult._electionContestant_electionType, electionType)
                .addObjectParam(PollingStationResult._electionPollingStation, eps)
                .orderByAsc(PollingStationResult._viewOrder)
                //                  .printQryInfo()
                .buildQry().getResultList();
    }

    public List<SubmittedResult> submitted(ElectionPollingStation eps, ElectionType electionType) {
        return QryBuilder.get(crudService.getEm(), SubmittedResult.class)
                .addObjectParam(SubmittedResult._electionContestant_electionType, electionType)
                .addObjectParam(SubmittedResult._electionPollingStation, eps)
                .orderByAsc(SubmittedResult._viewOrder)
                //                  .printQryInfo()
                .buildQry().getResultList();
    }

    public List<ElectionContestant> consititueny(ConstituencyElection constituencyElection, ElectionType electionType) {

        return QryBuilder.get(crudService.getEm(), ElectionContestant.class)
                .addObjectParam(ElectionContestant._electionType, electionType)
                .addObjectParam(ElectionContestant._constituencyElection, constituencyElection)
                .orderByAsc(ElectionContestant._viewOrder)
                //                  .printQryInfo()
                .buildQry().getResultList();
    }

    public List<ElectionPollingStation> getPollingStations(ConstituencyElection constituencyElection) {

        return QryBuilder.get(crudService.getEm(), ElectionPollingStation.class)
                .addObjectParam(ElectionPollingStation._constituency, constituencyElection.getConstituency())
                .addObjectParam(ElectionPollingStation._election, constituencyElection.getElection())
                .orderByAsc(ElectionPollingStation._pollingStation_stationName)
                .buildQry().getResultList();

    }

    public ConstituencyResultSet getResultSet(ConstituencyElection constituencyElection, ElectionType electionType) {
        ConstituencyResultSet resultSet = QryBuilder.get(crudService.getEm(), ConstituencyResultSet.class)
                .addObjectParam(ConstituencyResultSet._constituencyElection, constituencyElection)
                .addObjectParam(ConstituencyResultSet._electionType, electionType)
                .getSingleResult(ConstituencyResultSet.class);

        return resultSet;
    }

    public PollingStationResultSet getResultSet(ElectionPollingStation electionPollingStation, ElectionType electionType) {
        PollingStationResultSet resultSet = QryBuilder.get(crudService.getEm(), PollingStationResultSet.class)
                .addObjectParam(PollingStationResultSet._electionPollingStation, electionPollingStation)
                .addObjectParam(PollingStationResultSet._electionType, electionType)
                .getSingleResult(PollingStationResultSet.class);

        return resultSet;
    }

    
    public ConstituencyResultSet init(ConstituencyElection constituencyElection, ElectionType electionType) {
        ConstituencyResultSet resultSet = getResultSet(constituencyElection, electionType);

        if (resultSet == null) {
            resultSet = new ConstituencyResultSet();
            resultSet.setConstituencyElection(constituencyElection);
            resultSet.setElectionType(electionType);
            crudService.save(resultSet);
        }

        return resultSet;
    }
    
    
     
    public PollingStationResultSet init(ElectionPollingStation electionPollingStation, ElectionType electionType) {
        PollingStationResultSet resultSet = getResultSet(electionPollingStation, electionType);

        if (resultSet == null) {
            resultSet = new PollingStationResultSet();
            resultSet.setElectionPollingStation(electionPollingStation);
            resultSet.setElectionType(electionType);
            crudService.save(resultSet);
        }

        return resultSet;
    }

}
