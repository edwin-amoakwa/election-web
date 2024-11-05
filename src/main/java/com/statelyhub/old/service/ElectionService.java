/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.old.service;

import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.elections.constants.ElectionType;
import com.statelyhub.elections.entities.ConstituencyElection;
import com.statelyhub.elections.entities.Election;
import com.statelyhub.elections.entities.ElectionContestant;
import com.statelyhub.elections.entities.ElectionPollingStation;
import com.statelyhub.elections.entities.PollingStationResult;
import com.statelyhub.elections.entities.SubmittedResult;
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
      
      public Election getCurrentElection()
      {
          List<Election> elections = crudService.findAll(Election.class);
          
          return elections.get(0);
      }
    
      
      public List<ElectionContestant> cecs(ConstituencyElection constituencyElection, ElectionType electionType)
      {
          return QryBuilder.get(crudService.getEm(), ElectionContestant.class)
                  .addObjectParam(ElectionContestant._electionType, electionType)
                  .addObjectParam(ElectionContestant._constituencyElection, constituencyElection)
                    .orderByAsc(ElectionContestant._viewOrder)
                  .buildQry().getResultList();
      }
      
      public List<PollingStationResult> eps(ElectionPollingStation eps, ElectionType electionType)
      {
          return QryBuilder.get(crudService.getEm(), PollingStationResult.class)
                  .addObjectParam(PollingStationResult._electionContestant_electionType, electionType)
                  .addObjectParam(PollingStationResult._electionPollingStation, eps)
                    .orderByAsc(PollingStationResult._viewOrder)
//                  .printQryInfo()
                  .buildQry().getResultList();
      }
      
      
      public List<SubmittedResult> submitted(ElectionPollingStation eps, ElectionType electionType)
      {
          return QryBuilder.get(crudService.getEm(), SubmittedResult.class)
                  .addObjectParam(SubmittedResult._electionContestant_electionType, electionType)
                  .addObjectParam(SubmittedResult._electionPollingStation, eps)
                  .orderByAsc(SubmittedResult._viewOrder)
//                  .printQryInfo()
                  .buildQry().getResultList();
      }
      
}
