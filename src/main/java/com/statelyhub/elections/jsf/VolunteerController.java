/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.jsf;

import com.stately.common.collection.CollectionUtils;
import com.stately.common.data.ProcResponse;
import com.stately.common.security.SecurityHash;
import com.stately.common.utils.StringUtil;
import com.stately.modules.jpa2.QryBuilder;
import com.stately.modules.web.jsf.JsfMsg;
import com.statelyhub.elections.constants.VolunteerApprovalStatus;
import com.statelyhub.elections.constants.VolunteerClassification;
import com.statelyhub.elections.entities.PollingStation;
import com.statelyhub.elections.entities.Volunteer;
import com.statelyhub.elections.services.CrudService;
import com.statelyhub.elections.services.VolunteerService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;

/**
 *
 * @author Stately
 */
@Named(value = "volunteerController")
@SessionScoped
public class VolunteerController implements Serializable
{
    @Inject private UserSession userSession;
    @Inject private CrudService crudService;
    @Inject private VolunteerService volunteerService;
    
    private List<Volunteer> volunteersList;
    private Volunteer volunteer;
    
    
        private String newPassword = "";
    
    private List<PollingStation> pollingStationsList = new LinkedList<>();
    
    @PostConstruct
    public void init()
    {
        this.loadVolunteers();
        this.initVolunteer();
    }
    
    public void loadVolunteers()
    {
        try { 
            volunteersList = QryBuilder.get(crudService.getEm(), Volunteer.class)
                    .orderByAsc(Volunteer._volunteerName)
                    .buildQry().getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            volunteersList = Collections.EMPTY_LIST;
        }
    }
    
    public void initVolunteer()
    {
        volunteer = new Volunteer();
    }
    
    public boolean validateVolunteer()
    {
        if(StringUtil.isNullOrEmpty(volunteer.getVolunteerName()))
        {
            JsfMsg.error("Input Volunteer Name");
            return false;
        }
        if(StringUtil.isNullOrEmpty(volunteer.getMobileNo()))
        {
            JsfMsg.error("Input Phone Contact");
            return false;
        }
        if(volunteer.getConstituency() == null)
        {
            JsfMsg.error("Specify Constituency");
            return false;
        }
        return true;
    }
    
    public void saveVolunteer()
    {
        if(!validateVolunteer()) return;
        
        if(crudService.save(volunteer) == null)
        {
            JsfMsg.error("Error Saving Volunteer");
            return;
        }
        
        JsfMsg.successSave();
        CollectionUtils.checkAdd(volunteersList, volunteer);
        this.initVolunteer();
    }
    
    public void onConstituencyChange()
    {
        System.out.println("here hre");
        if(this.volunteer == null)return;
        if(this.volunteer.getConstituency()== null)
        { 
            System.out.println("here hre null");
            this.pollingStationsList.clear();
            return;
        } 
        System.out.println("here hre not null");
        
        try {
            pollingStationsList = QryBuilder.get(crudService.getEm(), PollingStation.class)
                .addObjectParam(PollingStation._constituency, this.volunteer.getConstituency())
                .orderByAsc(PollingStation._stationName)
                .printQryInfo().buildQry().getResultList(); 
        } catch (Exception e) {
            e.printStackTrace();
            pollingStationsList = Collections.EMPTY_LIST;
        }
    }
    
    public void selectVolunteer(Volunteer volunteer)
    {
        this.volunteer = volunteer;
        this.onConstituencyChange(); 
    }
    
    public void deleteVolunteer(Volunteer volunteer)
    {
        try {
            if(crudService.delete(volunteer))
            {
                JsfMsg.successDelete();
                this.volunteersList.remove(volunteer);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsfMsg.failedDelete();
    }
    
    public void verifyVolunteer(Volunteer volunteer)
    {
        volunteer.setClassification(VolunteerClassification.VERIFIED);
        volunteer.setProcessedBy(userSession.getAccountUR());
        if(crudService.save(volunteer) == null)
        {
            JsfMsg.error("Error Updating Record");
            return;
        }
        JsfMsg.info("Volunteer Verified Successfully");
        CollectionUtils.checkAdd(volunteersList, volunteer);
    }
    
    public void approveVolunteer(Volunteer volunteer)
    {
        ProcResponse response = volunteerService.approveVolunteer(volunteer);
        if(!response.isSuccess())
        {
            JsfMsg.error("Error Approving Record");
            return;
        }
        
        volunteer = (Volunteer)response.getData();
        JsfMsg.info("Volunteer Approved Successfully");
        CollectionUtils.checkAdd(volunteersList, volunteer);
    }
    
    public void unverifyVolunteer(Volunteer volunteer)
    {
        volunteer.setClassification(VolunteerClassification.UNVERIFIED);
        volunteer.setProcessedBy(userSession.getAccountUR());
        if(crudService.save(volunteer) == null)
        {
            JsfMsg.error("Error Updating Record");
            return;
        }
        JsfMsg.info("Volunteer Unverified Successfully");
        CollectionUtils.checkAdd(volunteersList, volunteer);
    }
    
    public void disapproveVolunteer(Volunteer volunteer)
    {
        volunteer.setApprovalStatus(VolunteerApprovalStatus.REJECTED);
        volunteer.setProcessedBy(userSession.getAccountUR());
        if(crudService.save(volunteer) == null)
        {
            JsfMsg.error("Error Updating Record");
            return;
        }
        JsfMsg.info("Volunteer Disapproved Successfully");
        CollectionUtils.checkAdd(volunteersList, volunteer);
    }
    
    
    public void saveNewPassword() {
        try {
            volunteer.setUserPassword(SecurityHash.getInstance().shaHash(newPassword));
            crudService.save(volunteer);

            initVolunteer();

            JsfMsg.msg(true);
        } catch (Exception e) {
        }

    }

    public List<Volunteer> getVolunteersList() {
        return volunteersList;
    }

    public void setVolunteersList(List<Volunteer> volunteersList) {
        this.volunteersList = volunteersList;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public List<PollingStation> getPollingStationsList() {
        return pollingStationsList;
    }

    public void setPollingStationsList(List<PollingStation> pollingStationsList) {
        this.pollingStationsList = pollingStationsList;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    
}
