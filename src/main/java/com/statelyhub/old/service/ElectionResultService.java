/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.old.service;

import com.statelyhub.elections.constants.ElectionType;
import com.statelyhub.elections.entities.ElectionPollingStation;
import com.statelyhub.elections.model.ElectionTypeResult;
import com.statelyhub.elections.services.CrudService;
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
    
    public List<ElectionTypeResult>  pollingStationBucket(ElectionPollingStation eps)
    {
        
           ElectionTypeResult presidential = new ElectionTypeResult();
           presidential.setElectionType(ElectionType.PRESIDENTIAL);
           presidential.setResultsList(electionService.eps(eps, ElectionType.PRESIDENTIAL));
           
           
           
           ElectionTypeResult parliamentary = new ElectionTypeResult();
           parliamentary.setElectionType(ElectionType.PARLIAMENTARY);
           parliamentary.setResultsList(electionService.eps(eps, ElectionType.PARLIAMENTARY));
           
           
           List<ElectionTypeResult> list = new LinkedList<>();
           
           list.add(presidential);
           list.add(parliamentary);
           
           
           return list;
           
    }
}
