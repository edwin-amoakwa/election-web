/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.jsf;

import com.stately.common.collection.CollectionUtils;
import com.stately.common.data.ProcResponse;
import com.stately.common.utils.StringUtil;
import com.stately.modules.jpa2.QryBuilder;
import com.stately.modules.web.jsf.JsfMsg;
import com.statelyhub.elections.constants.ElectionType;
import com.statelyhub.elections.constants.PartyType;
import com.statelyhub.elections.entities.ConstituencyElection;
import com.statelyhub.elections.entities.ElectionContestant;
import com.statelyhub.elections.entities.Region;
import com.statelyhub.elections.services.CrudService;
import com.statelyhub.elections.services.ElectionResultService;
import com.statelyhub.elections.services.ElectionService;
import com.statelyhub.elections.services.UpdateStatsService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
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
    private UserSession userSession;

    @Inject
    private ElectionResultService electionResultService;
    
     @Inject
    private UpdateStatsService updateStatsService;

    private List<ElectionContestant> electionContestantsList;
    
     private List<ElectionContestant> presidentialContestantsList;

    private List<ConstituencyElection> constituencyElectionList;
    

    private ConstituencyElection selectedConstituencyElection;
    
    private Region selectedRegion;
    
    private ElectionContestant electionContestant;
    
    @PostConstruct
    public void init()
    {
        selectedConstituencyElection = userSession.getConstituencyElectionUR();
        constituencyElectionList = new LinkedList<>();
        constituencyElectionList.add(selectedConstituencyElection);
        loadConstituencyResult(selectedConstituencyElection);
    }

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
                .addObjectParam(ElectionContestant._electionType, ElectionType.PARLIAMENTARY)
                .orderByAsc(ElectionContestant._viewOrder)
                .buildQry().getResultList();
        
        
        
        presidentialContestantsList= QryBuilder.get(crudService.getEm(), ElectionContestant.class)
                .addObjectParam(ElectionContestant._constituencyElection, constituencyElection)
                .addObjectParam(ElectionContestant._electionType, ElectionType.PRESIDENTIAL)
                .orderByAsc(ElectionContestant._viewOrder)
                .buildQry().getResultList();
        
    }


    public void initialise()
    {
        for (ElectionContestant contestant : electionContestantsList) {
             updateStatsService.addContestant(selectedConstituencyElection, contestant);
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
        this.electionContestant.setConstituencyElection(selectedConstituencyElection);
        this.electionContestant.setElectionType(ElectionType.PARLIAMENTARY);
        this.electionContestant.setCandidateType(PartyType.INDEPENDENT_CANDIDATE);   
        electionContestant.setElectionType(ElectionType.PARLIAMENTARY);
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
        
        if(electionContestant.getCandidateType() == PartyType.INDEPENDENT_CANDIDATE)
        {
            electionContestant.setParty(null);
        }
        
        electionContestant = crudService.save(electionContestant);
        if(electionContestant == null)
        {
            JsfMsg.error("Error Updating Record");
            return;
        }
        
        CollectionUtils.checkAdd(electionContestantsList, electionContestant);
        
        Collections.sort(electionContestantsList, Comparator.comparingInt(ElectionContestant::getViewOrder));
        
        JsfMsg.info("Record Updated Successfully");
        clearElectionContestant();
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
    
    public void removeElectionContestant(ElectionContestant electionContestant)
    {  
        try 
        {
            
            ProcResponse response = electionService.delete(electionContestant);
            if(response.isSuccess()){
                JsfMsg.successDelete();
                electionContestantsList.remove(electionContestant);
                return;
            }
            else
            {
                JsfMsg.error(response.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfMsg.error("Error Removing Candidate. Contact Administrator!");
        }
        
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

    public List<ElectionContestant> getPresidentialContestantsList() {
        return presidentialContestantsList;
    }

    

      
}
