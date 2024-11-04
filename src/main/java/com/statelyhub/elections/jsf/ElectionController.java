/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.jsf;

import com.stately.modules.web.jsf.JsfUtil;
import com.statelyhub.elections.services.CrudService;
import com.stately.modules.web.jsf.Msg;
import com.statelyhub.elections.entities.Election;
import com.statelyhub.elections.entities.PartyElection;
import com.statelyhub.elections.entities.PoliticalParty;
import com.statelyhub.old.service.LoaderService;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;

import jakarta.inject.Inject;

/**
 *
 * @author Edwin
 */
@Named(value = "electionController")
@SessionScoped
public class ElectionController implements Serializable
{
    @Inject private UserSession userSession;
    @Inject private CrudService crudService;
    @Inject private LoaderService loaderService;
        
    private List<Election> electionsList;
    
        private List<PartyElection> partyElectionsList;
    
    private Election election = new Election();
    
    private PoliticalParty selectedParty;
   
    
    public void editElection(Election election)
    {
        this.election = election;
    }
    
    public void deleteElection(Election election)
    {
        try
        {
            crudService.delete(election);
        } catch (Exception e)
        {
        }
        
    }
    
    @PostConstruct
    public void clear()
    {
        election = new Election();
        JsfUtil.resetViewRoot();
    }
    
    public void saveElection()
    {
        if(crudService.save(election) != null)
        {
            Msg.msg(true);
            clear();
        }
        else
        {
            Msg.msg(false);
        }
    }
    
    public void addParty()
    {
        System.out.println("... " + selectedParty);
        PartyElection partyElection = new PartyElection();
        partyElection.setElection(election);
        partyElection.setParty(selectedParty);
        partyElection.setViewOrder(getPartyElectionsList().size()+1);
        
        crudService.save(partyElection);
    }
    
    public List<Election> getElectionsList()
    {
        electionsList = crudService.findAll(Election.class);
        return electionsList;
    }

    public Election getElection()
    {
        return election;
    }

    public void setElection(Election election)
    {
        this.election = election;
    }

    public PoliticalParty getSelectedParty() {
        return selectedParty;
    }

    public void setSelectedParty(PoliticalParty selectedParty) {
        this.selectedParty = selectedParty;
    }

    public List<PartyElection> getPartyElectionsList() 
    {
        partyElectionsList = crudService.findAll(PartyElection.class);
        return partyElectionsList;
    }

    
    
}
