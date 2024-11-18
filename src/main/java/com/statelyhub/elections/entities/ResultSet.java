/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.entities;

import com.stately.modules.jpa2.UniqueEntityModel3;
import com.statelyhub.elections.constants.ElectionType;
import com.statelyhub.elections.constants.ResultSource;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author Edwin
 */
@MappedSuperclass
public class ResultSet extends UniqueEntityModel3 {

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
    @NotNull
    @Column(name = "election_type")
    @Enumerated(EnumType.STRING)
    private ElectionType electionType;

    public static final String _election = "election";
    @JoinColumn(name = "election")
    @ManyToOne
    private Election election;

    public static final String _resultSource = "resultSource";
    @Column(name = "result_source")
    @Enumerated(EnumType.STRING)
    private ResultSource resultSource = ResultSource.SUBMITTED;

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

    public Election getElection() {
        return election;
    }

    public void setElection(Election election) {
        this.election = election;
    }

}
