/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.web;

import com.google.common.base.Strings;
import com.stately.modules.jpa2.QryBuilder;
import com.stately.modules.web.jsf.JsfMsg;
import com.statelyhub.elections.entities.Constituency;
import com.statelyhub.elections.entities.ElectionPollingStation;
import com.statelyhub.elections.jsf.UserSession;
import com.statelyhub.elections.services.CrudService;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;

/**
 *
 * @author Edwin
 */
@Named(value = "pollingStationSearch")
@SessionScoped
public class PollingStationSearch implements Serializable
{
    @Inject private UserSession userSession;
    @Inject private CrudService crudService;
    
    private String field;
    private String searchText;
    private List<ElectionPollingStation> pollingStationsList;
    private Constituency constituency;
//    private List<Constituency> constituencyList;
    
    private ElectionPollingStation pollingStation;
    private boolean showDialog = false;
    private List<ElectionPollingStation> selectedPollingStationList = new LinkedList<>();
    
    
    public void clear()
    {
        searchText = "";
        pollingStation = null;
    }
    
    public void searchPollingStation()
    {
        try 
        {
            

//            if(Strings.isNullOrEmpty(field))
//            {
//                String msg = "Pleae select a filter";
//                JsfMsg.error(msg);
//                return;
//            }
            
              String qry = "SELECT c FROM " + ElectionPollingStation.class.getSimpleName() + " c "
                    + "WHERE c." + ElectionPollingStation._pollingStation_stationCode + " LIKE '%" + searchText.trim() + "%' "
                    + "OR c." + ElectionPollingStation._pollingStation_stationName+ " LIKE '%" + searchText.trim() + "%' "
                    //                + "OR c.fullname LIKE '%" + staff.getFullname()+ "%' "
                    + "ORDER BY c." + ElectionPollingStation._pollingStation_stationCode + " ASC";

            pollingStationsList =  crudService.getEm().createQuery(qry).getResultList();
            
            
//            pollingStationsList = QryBuilder.get(crudService.getEm(), ElectionPollingStation.class)
//                    .addObjectParam(ElectionPollingStation._election, userSession.getElectionUR())
//                    .addObjectParamWhenNotNull(ElectionPollingStation._constituency, constituency)
//                    .addStringQryParam(ElectionPollingStation._pollingStation_stationCode, searchText, QryBuilder.ComparismCriteria.LIKE)
//                    .addStringQryParam(ElectionPollingStation._pollingStation_stationName, searchText, QryBuilder.ComparismCriteria.LIKE)
//                    .printQryInfo()
//                    .buildQry().getResultList();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void addSPollingStation(ElectionPollingStation pollingStation)
    {
        if(!selectedPollingStationList.contains(pollingStation))
        {
            selectedPollingStationList.add(pollingStation);
        }
        
    }
    
    public void selectPollingStation(ElectionPollingStation pollingStation)
    {
        this.pollingStation = pollingStation;
        showDialog = false;
    }

    public String getSearchText() 
    {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public Constituency getConstituency() {
        return constituency;
    }

    public void setConstituency(Constituency constituency) {
        this.constituency = constituency;
    }

  
    
    public String getField()
    {
        return field;
    }

    public void setField(String field)
    {
        this.field = field;
    }

    public ElectionPollingStation getPollingStation() {
        return pollingStation;
    }

    public void setPollingStation(ElectionPollingStation pollingStation) {
        this.pollingStation = pollingStation;
    }

    public List<ElectionPollingStation> getPollingStationsList() {
        return pollingStationsList;
    }
    
    
}
