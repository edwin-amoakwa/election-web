/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.model;

import com.statelyhub.elections.entities.DistrictAssembly;
import com.statelyhub.elections.entities.PollingStation;

/**
 *
 * @author edwin
 */
public class UploadContainer {
    private String pollingStationCode;
    private String stationName;
    private String regionName;
    private String disctrictName;
    private String constitencyName;
    
    private PollingStation pollingStation;
    private DistrictAssembly assembly;

    public String getPollingStationCode() {
        return pollingStationCode;
    }

    public void setPollingStationCode(String pollingStationCode) {
        this.pollingStationCode = pollingStationCode;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getDisctrictName() {
        return disctrictName;
    }

    public void setDisctrictName(String disctrictName) {
        this.disctrictName = disctrictName;
    }

    public String getConstitencyName() {
        return constitencyName;
    }

    public void setConstitencyName(String constitencyName) {
        this.constitencyName = constitencyName;
    }

    public PollingStation getPollingStation() {
        return pollingStation;
    }

    public void setPollingStation(PollingStation pollingStation) {
        this.pollingStation = pollingStation;
    }

    public DistrictAssembly getAssembly() {
        return assembly;
    }

    public void setAssembly(DistrictAssembly assembly) {
        this.assembly = assembly;
    }
    
    
    
}
