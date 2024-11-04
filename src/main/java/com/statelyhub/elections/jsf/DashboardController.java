/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.jsf;

import com.statelyhub.elections.jsf.UserSession;
import com.statelyhub.old.entities.BillStatus;
import com.statelyhub.elections.services.CrudService;
import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.elections.entities.Election;
import java.io.Serializable;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;


/**
 *
 * @author Edwin
 */
@Named(value = "dashboardController")
@RequestScoped
public class DashboardController implements Serializable
{
    
    
    @Inject CrudService crudService;
    @Inject UserSession userSession;
    
    public DashboardController()
    {
    }
    
    private int totalPvs;
    private int pendingPvs;
    private int approvedPvs;
    private int rejectedPvs;
    
    @PostConstruct
    public void init()
    {
        totalPvs = getTotalMembership(null);
        pendingPvs = getTotalMembership(BillStatus.PENDING);
        approvedPvs = getTotalMembership(BillStatus.APPROVED);
        rejectedPvs = getTotalMembership(BillStatus.REJECTED);
    }
    
    public int getTotalMembership(BillStatus pvStatus)
    {
        QryBuilder builder = QryBuilder.get(crudService.getEm(), Election.class);
//        builder.addObjectParam(Bill._unit, userSession.getInstitution());
//        
//        if (pvStatus != null)
//        {
//            builder.addObjectParam(Bill._billStatus, pvStatus);
//        }

        return builder.count();
    }

    public int getTotalPvs()
    {
        return totalPvs;
    }

    public int getPendingPvs()
    {
        return pendingPvs;
    }

    public int getApprovedPvs()
    {
        return approvedPvs;
    }

    public int getRejectedPvs()
    {
        return rejectedPvs;
    }
    
    
    
    
}
