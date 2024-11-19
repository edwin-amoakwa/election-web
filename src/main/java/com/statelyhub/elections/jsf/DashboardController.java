/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.jsf;

import com.statelyhub.elections.services.CrudService;
import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.elections.constants.ElectionType;
import com.statelyhub.elections.constants.ResultStatus;
import com.statelyhub.elections.entities.Constituency;
import com.statelyhub.elections.entities.ConstituencyElection;
import com.statelyhub.elections.entities.ElectionPollingStation;
import com.statelyhub.elections.model.ElectionTypeDashboard;
import com.statelyhub.elections.services.DashboardService;
import com.statelyhub.elections.services.ElectionService;
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
    @Inject ElectionService electionService;
    
        @Inject DashboardService dashboardService;
    
    
    private int totalPollingStation;
    private int pendingPollingStations;
    private int finalisedPollingStations;
    private int totalVoters;
    
    private ElectionTypeDashboard presidential;
    private ElectionTypeDashboard parliamentary;
    
    private ConstituencyElection constituencyElection;
    
    @PostConstruct
    public void init()
    {
        Constituency constituency = userSession.getAccountUR().getConstituency();
        
        try {
            constituencyElection = electionService.election(constituency, electionService.getCurrentElection());
        } catch (Exception e) {
            constituencyElection = null;
        }
        
        presidential = dashboardService.dashboard(ElectionType.PRESIDENTIAL, constituencyElection);
        parliamentary = dashboardService.dashboard(ElectionType.PARLIAMENTARY, constituencyElection);
        
        
        totalPollingStation = pollingStation(constituencyElection);
        pendingPollingStations = getTotalMembership(ResultStatus.PENDING);
        finalisedPollingStations = getTotalMembership(ResultStatus.FINALISED);
        
        totalVoters = voteres(constituencyElection);

        
        
    }
    
     public int pollingStation(ConstituencyElection constituencyElection)
    {
        QryBuilder builder = QryBuilder.get(crudService.getEm(), ElectionPollingStation.class);
        builder.addObjectParamWhenNotNull(ElectionPollingStation._constituencyElection, constituencyElection);
//        
//        if (pvStatus != null)
//        {
//            builder.addObjectParam(Bill._billStatus, pvStatus);
//        }

        return builder.count();
    }
    
     public int voteres(ConstituencyElection constituencyElection)
    {
        if(constituencyElection != null)
        {
            return constituencyElection.getVotersCount();
        }
        
        return QryBuilder.get(crudService.getEm(), ConstituencyElection.class)
                .addReturnField("SUM(e."+ConstituencyElection._votersCount+")")
                .getResultAsInt();
//        
//        if (pvStatus != null)
//        {
//            builder.addObjectParam(Bill._billStatus, pvStatus);
//        }

//        return 0;
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

    public int getTotalVoters() {
        return totalVoters;
    }

  
    public ElectionTypeDashboard getPresidential() {
        return presidential;
    }

    public ElectionTypeDashboard getParliamentary() {
        return parliamentary;
    }
    
    
    
    
}
