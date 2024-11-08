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
import com.statelyhub.elections.constants.ResultStatus;
import com.statelyhub.elections.entities.Election;
import com.statelyhub.elections.entities.ElectionPollingStation;
import java.io.Serializable;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.List;


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
    
    
    private int totalPollingStation;
    private int pendingPollingStations;
    private int finalisedPollingStations;
    private int rejectedPvs;
    
    @PostConstruct
    public void init()
    {
        totalPollingStation = getTotalMembership(null);
        pendingPollingStations = getTotalMembership(ResultStatus.PENDING);
        finalisedPollingStations = getTotalMembership(ResultStatus.FINALISED);
        
        
//        Object[] result = QryBuilder.get(crudService.getEm(), ElectionPollingStation.class)
//                .addReturnField("COUNT(e.id)")
//                .addReturnField("e."+ElectionPollingStation._resultStatus)
//                .addGroupBy(ElectionPollingStation._resultStatus)
//                .getSingleResult(Object[].class);
//        
//        if(result != null)
//        {
//            
//        }

        
        
    }
    
    public int getTotalMembership(ResultStatus resultStatus)
    {
        QryBuilder builder = QryBuilder.get(crudService.getEm(), ElectionPollingStation.class);
        builder.addObjectParamWhenNotNull(ElectionPollingStation._resultStatus, resultStatus);
//        
//        if (pvStatus != null)
//        {
//            builder.addObjectParam(Bill._billStatus, pvStatus);
//        }

        return builder.count();
    }

    public int getTotalPollingStation()
    {
        return totalPollingStation;
    }

    public int getPendingPollingStations()
    {
        return pendingPollingStations;
    }

    public int getFinalisedPollingStations()
    {
        return finalisedPollingStations;
    }

    public int getRejectedPvs()
    {
        return rejectedPvs;
    }
    
    
    
    
}
