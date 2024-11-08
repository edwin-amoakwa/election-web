/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.entities;

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
@Table(name = "submitted_result_set")
public class SubmittedResultSet extends ResultSet {

    public static final String _electionPollingStation = "electionPollingStation";
    @JoinColumn(name = "election_polling_station")
    @ManyToOne
    private ElectionPollingStation electionPollingStation;

    public static final String _resultSubmission = "resultSubmissionId";
    @Column(name = "result_submission")
    private String resultSubmissionId;
    

    public ElectionPollingStation getElectionPollingStation() {
        return electionPollingStation;
    }

    public void setElectionPollingStation(ElectionPollingStation electionPollingStation) {
        this.electionPollingStation = electionPollingStation;
    }

    public String getResultSubmissionId() {
        return resultSubmissionId;
    }

    public void setResultSubmissionId(String resultSubmissionId) {
        this.resultSubmissionId = resultSubmissionId;
    }

    
}
