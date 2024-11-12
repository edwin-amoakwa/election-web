/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.entities;

import com.statelyhub.elections.constants.ResultSource;
import com.statelyhub.elections.constants.ResultStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "constituency_election")
public class ConstituencyElection extends ResultSet {

//    public static final String _election = "election";
//    @JoinColumn(name = "election")
//    @ManyToOne
//    private Election election;

    public static final String _constituency = "constituency";
    public static final String _constituency_constituencyName = _constituency + "." + Constituency._constituencyName;
    @JoinColumn(name = "constituency")
    @ManyToOne
    private Constituency constituency;

    public static final String _region = "region";
    @JoinColumn(name = "region")
    @ManyToOne
    private Region region;
    
    
//    public static final String _resultSource = "resultSource";
//    @Column(name = "result_source")
//    @Enumerated(EnumType.STRING)
//    private Elec resultSource;

    public static final String _resultSource = "resultSource";
    @Column(name = "result_source")
    @Enumerated(EnumType.STRING)
    private ResultSource resultSource;

    public static final String _resultStatus = "resultStatus";
    @Column(name = "result_status")
    @Enumerated(EnumType.STRING)
    private ResultStatus resultStatus;

    public static final String _pollingStationCount = "pollingStations";
    @Column(name = "polling_stations")
    private int pollingStationCount;

//    public Election getElection() {
//        return election;
//    }
//
//    public void setElection(Election election) {
//        this.election = election;
//    }

    public Constituency getConstituency() {
        return constituency;
    }

    public void setConstituency(Constituency constituency) {
        this.constituency = constituency;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public ResultSource getResultSource() {
        return resultSource;
    }

    public void setResultSource(ResultSource resultSource) {
        this.resultSource = resultSource;
    }

    public ResultStatus getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(ResultStatus resultStatus) {
        this.resultStatus = resultStatus;
    }

    public int getPollingStationCount() {
        return pollingStationCount;
    }

    public void setPollingStationCount(int pollingStationCount) {
        this.pollingStationCount = pollingStationCount;
    }

    @Override
    public String toString() {
        return constituency + "";
    }

}
