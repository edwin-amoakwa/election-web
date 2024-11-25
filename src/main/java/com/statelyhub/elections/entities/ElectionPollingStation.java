/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.entities;

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
@Table(name = "election_polling_station")
public class ElectionPollingStation extends SummarySet {



    public static final String _election = "election";
    @JoinColumn(name = "election")
    @ManyToOne
    private Election election;

    public static final String _pollingStation = "pollingStation";
    public static final String _pollingStation_stationName = _pollingStation + "." + PollingStation._stationName;
    public static final String _pollingStation_stationCode = _pollingStation + "." + PollingStation._stationCode;
    @JoinColumn(name = "polling_station")
    @ManyToOne
    private PollingStation pollingStation;

    public static final String _constituency = "constituency";
    public static final String _constituency_region = _constituency + "." + Constituency._region;
    @JoinColumn(name = "constituency")
    @ManyToOne
    private Constituency constituency;

    public static final String _constituencyElection = "constituencyElection";
    @JoinColumn(name = "constituency_election")
    @ManyToOne
    private ConstituencyElection constituencyElection;
    
    public static final String _parliamentarySubmissionId = "parliamentarySubmissionId";
    @Column(name = "parliamentary_submission_id")
    private String parliamentarySubmissionId;
    
    
    public static final String _presidentialSubmissionId = "presidentialSubmissionId";
    @Column(name = "presidential_submission_id")
    private String presidentialSubmissionId;

    public static final String _resultStatus = "resultStatus";
    @Column(name = "result_status")
    @Enumerated(EnumType.STRING)
    private ResultStatus resultStatus;
    

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

    public Constituency getConstituency() {
        return constituency;
    }

    public void setConstituency(Constituency constituency) {
        this.constituency = constituency;
    }

    public ConstituencyElection getConstituencyElection() {
        return constituencyElection;
    }

    public void setConstituencyElection(ConstituencyElection constituencyElection) {
        this.constituencyElection = constituencyElection;
    }
    
    
    public String getParliamentarySubmissionId() {
        return parliamentarySubmissionId;
    }

    public void setParliamentarySubmissionId(String parliamentarySubmissionId) {
        this.parliamentarySubmissionId = parliamentarySubmissionId;
    }


    public ResultStatus getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(ResultStatus resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getPresidentialSubmissionId() {
        return presidentialSubmissionId;
    }

    public void setPresidentialSubmissionId(String presidentialSubmissionId) {
        this.presidentialSubmissionId = presidentialSubmissionId;
    }

    

    @Override
    public String toString() {
        return pollingStation + "";
    }

}
