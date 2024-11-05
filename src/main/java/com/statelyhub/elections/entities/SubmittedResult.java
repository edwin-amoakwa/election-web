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
@Table(name = "submitted_results")
public class SubmittedResult extends Result {

 
//    public static final String _resultSubmission = "resultSubmission";
//    @JoinColumn(name = "result_submission")
//    @ManyToOne
//    private ResultSubmission resultSubmission;

    public static final String _pollingStation = "pollingStation";
    @JoinColumn(name = "polling_station")
    @ManyToOne
    private PollingStation pollingStation;

    public static final String _electionPollingStation = "electionPollingStation";
    @JoinColumn(name = "election_polling_station")
    @ManyToOne
    private ElectionPollingStation electionPollingStation;

    

    
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

//    public ResultSubmission getResultSubmission() {
//        return resultSubmission;
//    }
//
//    public void setResultSubmission(ResultSubmission resultSubmission) {
//        this.resultSubmission = resultSubmission;
//    }

    
}
