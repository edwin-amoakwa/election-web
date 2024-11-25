/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "polling_station_candidate_result")
public class PollingStationResult extends Result {
    

    public static final String _volunteer = "volunteer";
    @JoinColumn(name = "volunteer")
    @ManyToOne
    private Volunteer volunteer;

    public static final String _election = "election";
    @JoinColumn(name = "election")
    @ManyToOne
    private Election election;

    public static final String _pollingStation = "pollingStation";
    @JoinColumn(name = "polling_station")
    @ManyToOne
    private PollingStation pollingStation;

    public static final String _electionPollingStation = "electionPollingStation";
    public static final String _electionPollingStation_constituency = _electionPollingStation + "." + ElectionPollingStation._constituency;
    @JoinColumn(name = "election_polling_station")
    @ManyToOne
    private ElectionPollingStation electionPollingStation;
    
    
    
    public static final String _stationResultSet = "stationResultSet";
    @JoinColumn(name = "station_result_set")
    @ManyToOne
    private PollingStationResultSet stationResultSet;


    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public Election getElection() {
        return election;
    }

    public void setElection(Election election) {
        this.election = election;
    }

    public PollingStation getPollingStation() {
        return pollingStation;
    }

    public void setPollingStation(PollingStation pollingStation) {
        this.pollingStation = pollingStation;
    }

    public ElectionPollingStation getElectionPollingStation() {
        return electionPollingStation;
    }

    public void setElectionPollingStation(ElectionPollingStation electionPollingStation) {
        this.electionPollingStation = electionPollingStation;
    }


    public PollingStationResultSet getStationResultSet() {
        return stationResultSet;
    }

    public void setStationResultSet(PollingStationResultSet stationResultSet) {
        this.stationResultSet = stationResultSet;
    }

    
    
    
}
