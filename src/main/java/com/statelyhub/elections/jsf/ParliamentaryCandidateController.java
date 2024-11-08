/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.jsf;

import com.stately.common.collection.CollectionUtils;
import com.stately.common.utils.StringUtil;
import com.stately.modules.jpa2.QryBuilder;
import com.stately.modules.web.jsf.JsfMsg;
import com.statelyhub.elections.entities.ConstituencyElection;
import com.statelyhub.elections.entities.ElectionContestant;
import com.statelyhub.elections.entities.Region;
import com.statelyhub.elections.services.CrudService;
import com.statelyhub.elections.services.ElectionResultService;
import com.statelyhub.elections.services.ElectionService;
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
@Named(value = "parliamentaryCandidateController")
public class ParliamentaryCandidateController implements Serializable {

    @Inject
    private CrudService crudService;

    @Inject
    private ElectionService electionService;

    @Inject
    private ElectionResultService electionResultService;
    
     @Inject
    private UpdateStatsService updateStatsService;

    private List<ElectionContestant> electionContestantsList;

    private List<ConstituencyElection> constituencyElectionList;
    

    private ConstituencyElection selectedConstituencyElection;
    
    private Region selectedRegion;
    
    private ElectionContestant electionContestant;

    public void loadConstituency() {
        constituencyElectionList = QryBuilder.get(crudService.getEm(), ConstituencyElection.class)
                .addObjectParamWhenNotNull(ConstituencyElection._region, selectedRegion)
                .orderByDesc(ConstituencyElection._constituency_constituencyName)
                .buildQry().getResultList();
        
    }

    public void loadConstituencyResult(ConstituencyElection constituencyElection) {
        this.selectedConstituencyElection = constituencyElection;
        this.clearElectionContestant();
        electionContestantsList = QryBuilder.get(crudService.getEm(), ElectionContestant.class)
                .addObjectParam(ElectionContestant._constituencyElection, constituencyElection)
                .buildQry().getResultList();
        
    }


    public void initialise()
    {
        for (ElectionContestant electionContestant : electionContestantsList) {
             updateStatsService.addContestant(selectedConstituencyElection, electionContestant);
        }
        JsfMsg.msg(true);
//        electionResultService.runConstituency(selectedConstituencyElection);
    }

    
    public void editElectionContestant(ElectionContestant electionContestant)
    {
        this.electionContestant = electionContestant;
    }
    
    public void clearElectionContestant()
    {
        this.electionContestant = new ElectionContestant();
    }
    
    public void saveElectionContestant()
    {
        if(StringUtil.isNullOrEmpty(electionContestant.getCandidateName()))
        {
            JsfMsg.error("Specify Candidate Name");
            return;
        }
        
        if(electionContestant.getCandidateType() == null)
        {
            JsfMsg.error("Specify Candidate Type");
            return;
        }
        
        if(electionContestant.isPoliticalParty() && electionContestant.getParty() == null)
        {
            JsfMsg.error("Specify Political Party Affiliated To");
            return;
        }
        
        electionContestant = crudService.save(electionContestant);
        if(electionContestant == null)
        {
            JsfMsg.error("Error Updating Record");
            return;
        }
        
        CollectionUtils.checkAdd(electionContestantsList, electionContestant);
        JsfMsg.info("Record Updated Successfully");
    }
    
    public void updateElectionContestant(ElectionContestant electionContestant)
    {
        if(StringUtil.isNullOrEmpty(electionContestant.getCandidateName()))
        {
            JsfMsg.error("Specify Candidate Name");
            return;
        }
        
        electionContestant = crudService.save(electionContestant);
        if(electionContestant == null)
        {
            JsfMsg.error("Error Updating Record");
            return;
        }
        
        CollectionUtils.checkAdd(electionContestantsList, electionContestant);
        JsfMsg.info("Record Updated Successfully");
    }
    
    public Region getSelectedRegion() {
        return selectedRegion;
    }

    public void setSelectedRegion(Region selectedRegion) {
        this.selectedRegion = selectedRegion;
    }

    public List<ConstituencyElection> getConstituencyElectionList() {
        return constituencyElectionList;
    }

    
    public ConstituencyElection getSelectedConstituencyElection() {
        return selectedConstituencyElection;
    }

    public List<ElectionContestant> getElectionContestantsList() {
        return electionContestantsList;
    }

    public ElectionContestant getElectionContestant() {
        return electionContestant;
    }

    public void setElectionContestant(ElectionContestant electionContestant) {
        this.electionContestant = electionContestant;
    }

    

      
}
