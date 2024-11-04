/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.entities;

import com.stately.modules.jpa2.UniqueEntityModel3;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "regions")
public class Region extends UniqueEntityModel3 {

    public static final String _regionName = "regionName";
    @Column(name = "region_name")
    private String regionName;
    
        
    public static final String _constituencyCount = "constituencyCount";
    @Column(name = "constituency_count")
    private int constituencyCount;
    
    public static final String _pollingStationCount = "pollingStations";
    @Column(name = "polling_stations")
    private int pollingStationCount;

    public static final String _votersCount = "votersCount";
    @Column(name = "voters_count")
    private int votersCount;
    
    

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @Override
    public String toString() {
        return regionName;
    }

    public int getConstituencyCount() {
        return constituencyCount;
    }

    public void setConstituencyCount(int constituencyCount) {
        this.constituencyCount = constituencyCount;
    }

    public int getPollingStationCount() {
        return pollingStationCount;
    }

    public void setPollingStationCount(int pollingStationCount) {
        this.pollingStationCount = pollingStationCount;
    }

    public int getVotersCount() {
        return votersCount;
    }

    public void setVotersCount(int votersCount) {
        this.votersCount = votersCount;
    }

    
   
    
}
