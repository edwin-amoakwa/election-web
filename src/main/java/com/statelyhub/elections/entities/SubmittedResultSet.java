/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.entities;

import com.stately.modules.jpa2.UniqueEntityModel3;
import com.statelyhub.elections.constants.ElectionType;
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
@Table(name = "submitted_result_set")
public class SubmittedResultSet extends UniqueEntityModel3 {

    public static final String _validVotes = "validVotes";
    @Column(name = "valid_votes")
    private int validVotes;

    public static final String _rejectedBallots = "rejectedBallots";
    @Column(name = "rejected_ballots")
    private int rejectedBallots;

    public static final String _spoiltBallots = "spoiltBallots";
    @Column(name = "spoilt_ballots")
    private int spoiltBallots;

    public static final String _totalVotesCast = "totalVotesCast";
    @Column(name = "total_votes_cast")
    private int totalVotesCast;

    public static final String _electionType = "electionType";
    @Column(name = "election_type")
    @Enumerated(EnumType.STRING)
    private ElectionType electionType;

    public static final String _electionPollingStation = "electionPollingStation";
    @JoinColumn(name = "election_polling_station")
    @ManyToOne
    private ElectionPollingStation electionPollingStation;

    public static final String _resultSubmission = "resultSubmissionId";
    @Column(name = "result_submission")
    private String resultSubmissionId;

    public int getValidVotes() {
        return validVotes;
    }

    public void setValidVotes(int validVotes) {
        this.validVotes = validVotes;
    }

    public int getRejectedBallots() {
        return rejectedBallots;
    }

    public void setRejectedBallots(int rejectedBallots) {
        this.rejectedBallots = rejectedBallots;
    }

    public int getSpoiltBallots() {
        return spoiltBallots;
    }

    public void setSpoiltBallots(int spoiltBallots) {
        this.spoiltBallots = spoiltBallots;
    }

    public int getTotalVotesCast() {
        return totalVotesCast;
    }

    public void setTotalVotesCast(int totalVotesCast) {
        this.totalVotesCast = totalVotesCast;
    }

    public ElectionType getElectionType() {
        return electionType;
    }

    public void setElectionType(ElectionType electionType) {
        this.electionType = electionType;
    }

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
