/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.jsf;

import com.stately.modules.web.jsf.JsfMsg;
import com.stately.modules.web.jsf.JsfUtil;
import com.statelyhub.elections.services.CrudService;
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
@Named(value = "politicalPartyController")
@SessionScoped
public class PoliticalPartyController implements Serializable
{
    @Inject private UserSession userSession;
    @Inject private CrudService crudService;
    private List<PoliticalParty> partysList;
    
    private PoliticalParty party = new PoliticalParty();
    
    public PoliticalPartyController()
    {
    }

    
    public void editParty(PoliticalParty party)
    {
        this.party = party;
    }
    
    @PostConstruct
    public void clear()
    {
        partysList = crudService.findAll(PoliticalParty.class);
        
        
        party = new PoliticalParty();
        JsfUtil.resetViewRoot();
    }
    
    public void savePoliticalParty()
    {
        if(crudService.save(party) != null)
        {
            JsfMsg.msg(true);
            clear();
        }
        else
        {
            JsfMsg.msg(false);
        }
    }

    public List<PoliticalParty> getPartysList() {
        return partysList;
    }

    public void setPartysList(List<PoliticalParty> partysList) {
        this.partysList = partysList;
    }

    public PoliticalParty getParty() {
        return party;
    }

    public void setParty(PoliticalParty party) {
        this.party = party;
    }
    
   
    
    
}
