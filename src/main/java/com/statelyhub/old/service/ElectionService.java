/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.old.service;

import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.elections.constants.ElectionType;
import com.statelyhub.elections.entities.ConstituencyElection;
import com.statelyhub.elections.entities.ElectionContestant;
import com.statelyhub.elections.entities.ElectionPollingStation;
import com.statelyhub.elections.entities.PollingStationResult;
import com.statelyhub.elections.services.CrudService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

/**
 *
 * @author edwin
 */
@Stateless
public class ElectionService {
    
      @Inject private CrudService crudService;
    
      
      public List<ElectionContestant> cecs(ConstituencyElection constituencyElection, ElectionType electionType)
      {
          return QryBuilder.get(crudService.getEm(), ElectionContestant.class)
                  .addObjectParam(ElectionContestant._electionType, electionType)
                  .addObjectParam(ElectionContestant._constituencyElection, constituencyElection)
                  .buildQry().getResultList();
      }
      
      public List<PollingStationResult> eps(ElectionPollingStation eps, ElectionType electionType)
      {
          return QryBuilder.get(crudService.getEm(), PollingStationResult.class)
                  .addObjectParam(PollingStationResult._electionContestant_electionType, electionType)
                  .addObjectParam(PollingStationResult._electionPollingStation, eps)
                  .printQryInfo()
                  .buildQry().getResultList();
      }
      
}
