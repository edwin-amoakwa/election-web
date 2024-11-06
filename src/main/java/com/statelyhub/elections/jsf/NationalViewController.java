/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.jsf;

import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.elections.constants.ResultStatus;
import com.statelyhub.elections.entities.ConstituencyElection;
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
@Named(value = "nationalViewController")
public class NationalViewController implements Serializable {

    @Inject
    private CrudService crudService;

    @Inject
    private ElectionService electionService;

    @Inject
    private ElectionResultService electionResultService;

    private List<ElectionTypeResult> constituencyResultList;

    private List<ConstituencyElection> completedConstituencyElectionList;
    private List<ConstituencyElection> pendingConstituencyElectionList;

    private ConstituencyElection selectedConstituencyElection;
    
    private Region selectedRegion;
    


    public void loadConstituency() {
        List<ConstituencyElection> list = QryBuilder.get(crudService.getEm(), ConstituencyElection.class)
                .addObjectParamWhenNotNull(ConstituencyElection._region, selectedRegion)
                .orderByDesc(ConstituencyElection._constituency_constituencyName)
                .buildQry().getResultList();

        completedConstituencyElectionList = new LinkedList<>();
        pendingConstituencyElectionList = new LinkedList<>();

        for (ConstituencyElection eps : list) {
            if (eps.getResultStatus() == ResultStatus.FINALISED) {
                completedConstituencyElectionList.add(eps);
            } else{
                pendingConstituencyElectionList.add(eps);
            }
            
        }

    }

    public void loadConstituencyResult(ConstituencyElection constituencyElection) {
        this.selectedConstituencyElection = constituencyElection;
        constituencyResultList = electionResultService.constituency(selectedConstituencyElection);
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

    public List<ConstituencyElection> getCompletedConstituencyElectionList() {
        return completedConstituencyElectionList;
    }

    public List<ConstituencyElection> getPendingConstituencyElectionList() {
        return pendingConstituencyElectionList;
    }

    public List<ElectionTypeResult> getConstituencyResultList() {
        return constituencyResultList;
    }

    public ConstituencyElection getSelectedConstituencyElection() {
        return selectedConstituencyElection;
    }

    

      
}
