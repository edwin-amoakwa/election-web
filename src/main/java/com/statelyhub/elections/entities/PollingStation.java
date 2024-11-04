/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.entities;

import com.stately.modules.jpa2.UniqueEntityModel3;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "polling_station")
public class PollingStation extends UniqueEntityModel3 {

     public static final String _stationCode = "stationCode";
    @Column(name = "station_code")
    private String stationCode;
    
    public static final String _stationName = "stationName";
    @Column(name = "station_name")
    private String stationName;

    public static final String _electorialArea = "electorialArea";
    @JoinColumn(name = "electorial_area")
    @ManyToOne
    private ElectorialArea electorialArea;

    public static final String _administrativeArea = "administrativeArea";
    @JoinColumn(name = "administrative_area")
    @ManyToOne
    private AdministrativeArea  administrativeArea;

    public static final String _constituency = "constituency";
    @JoinColumn(name = "constituency")
    @ManyToOne
    private Constituency constituency;

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public ElectorialArea getElectorialArea() {
        return electorialArea;
    }

    public void setElectorialArea(ElectorialArea electorialArea) {
        this.electorialArea = electorialArea;
    }

    public AdministrativeArea getAdministrativeArea() {
        return administrativeArea;
    }

    public void setAdministrativeArea(AdministrativeArea administrativeArea) {
        this.administrativeArea = administrativeArea;
    }

    public Constituency getConstituency() {
        return constituency;
    }

    public void setConstituency(Constituency constituency) {
        this.constituency = constituency;
    }

    @Override
    public String toString() {
        return stationName;
    }

    
    
}
