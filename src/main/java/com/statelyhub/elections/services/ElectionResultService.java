/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.services;

import com.statelyhub.elections.constants.ElectionType;
import com.statelyhub.elections.entities.ElectionPollingStation;
import com.statelyhub.elections.model.ElectionTypeResult;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author edwin
 */
@Stateless
public class ElectionResultService {

    @Inject
    private CrudService crudService;

    @Inject
    private ElectionService electionService;

    public List<ElectionTypeResult> pollingStationBucket(ElectionPollingStation eps) {

        ElectionTypeResult presidential = new ElectionTypeResult();
        presidential.setElectionType(ElectionType.PRESIDENTIAL);
        presidential.setVotingsList(electionService.eps(eps, ElectionType.PRESIDENTIAL));

        ElectionTypeResult parliamentary = new ElectionTypeResult();
        parliamentary.setElectionType(ElectionType.PARLIAMENTARY);
        parliamentary.setVotingsList(electionService.eps(eps, ElectionType.PARLIAMENTARY));

        List<ElectionTypeResult> list = new LinkedList<>();

        list.add(presidential);
        list.add(parliamentary);

        return list;

    }

    public List<ElectionTypeResult> volunteerEpsBucket(ElectionPollingStation eps) {

        ElectionTypeResult presidential = new ElectionTypeResult();
        presidential.setElectionType(ElectionType.PRESIDENTIAL);
        presidential.setSubmittedResultsList(electionService.submitted(eps, ElectionType.PRESIDENTIAL));

        ElectionTypeResult parliamentary = new ElectionTypeResult();
        parliamentary.setElectionType(ElectionType.PARLIAMENTARY);
        parliamentary.setSubmittedResultsList(electionService.submitted(eps, ElectionType.PARLIAMENTARY));

        List<ElectionTypeResult> list = new LinkedList<>();

        list.add(presidential);
        list.add(parliamentary);

        return list;

    }
}
