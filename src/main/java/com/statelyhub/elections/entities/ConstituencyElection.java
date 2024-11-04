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
@Table(name = "constituency_election")
public class ConstituencyElection extends UniqueEntityModel3 {

    public static final String _election = "election";
    @JoinColumn(name = "election")
    @ManyToOne
    private Election election;

    public static final String _constituency = "constituency";
    @JoinColumn(name = "constituency")
    @ManyToOne
    private Constituency constituency;

    public static final String _region = "region";
    @JoinColumn(name = "region")
    @ManyToOne
    private Region region;

    public static final String _constituencyCount = "constituencyCount";
    @Column(name = "constituency_count")
    private int constituencyCount;
    
    public static final String _pollingStationCount = "pollingStations";
    @Column(name = "polling_stations")
    private int pollingStationCount;

    public static final String _votersCount = "votersCount";
    @Column(name = "voters_count")
    private int votersCount;

    public Election getElection() {
        return election;
    }

    public void setElection(Election election) {
        this.election = election;
    }

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
