/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.jsf;

import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.elections.constants.ResultStatus;
import com.statelyhub.elections.entities.ConstituencyElection;
import com.statelyhub.elections.entities.ElectionContestant;
import com.statelyhub.elections.entities.Region;
import com.statelyhub.elections.model.ElectionTypeResult;
import com.statelyhub.elections.services.CrudService;
import com.statelyhub.elections.services.ElectionResultService;
import com.statelyhub.elections.services.ElectionService;
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
@SessionScoped
@Named(value = "parliamentaryCandidateController")
public class ParliamentaryCandidateController implements Serializable {

    @Inject
    private CrudService crudService;

    @Inject
    private ElectionService electionService;

    @Inject
    private ElectionResultService electionResultService;

    private List<ElectionContestant> electionContestantsList;

    private List<ConstituencyElection> constituencyElectionList;
    

    private ConstituencyElection selectedConstituencyElection;
    
    private Region selectedRegion;
    


    public void loadConstituency() {
        constituencyElectionList = QryBuilder.get(crudService.getEm(), ConstituencyElection.class)
                .addObjectParamWhenNotNull(ConstituencyElection._region, selectedRegion)
                .orderByDesc(ConstituencyElection._constituency_constituencyName)
                .buildQry().getResultList();
        
    }

    public void loadConstituencyResult(ConstituencyElection constituencyElection) {
        this.selectedConstituencyElection = constituencyElection;
        
        electionContestantsList = QryBuilder.get(crudService.getEm(), ElectionContestant.class)
                .addObjectParam(ElectionContestant._constituencyElection, constituencyElection)
                .buildQry().getResultList();
        
    }


    public void updateConstituecyFigures()
    {
        electionResultService.runConstituency(selectedConstituencyElection);
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

    

      
}
