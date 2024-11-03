/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.api;

import com.stately.common.api.ApiUtils;
import jakarta.ws.rs.HeaderParam;

/**
 *
 * @author Edwin
 */
public class DefaultHeaders  {

    @HeaderParam(value = "electionId")
    String electionId;

    @HeaderParam(value = "userId")
    private String userId;

    @HeaderParam("locationId")
    private String locationId;

    public String getElectionId() {
        return electionId;
    }

    public void setElectionId(String electionId) {
        this.electionId = electionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public void clean() 
    {
        electionId = ApiUtils.getValue(electionId);
        locationId = ApiUtils.getValue(locationId);
        userId = ApiUtils.getValue(userId);
    }
   
    
}
