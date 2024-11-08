/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.entities;

import com.stately.modules.jpa2.UniqueEntityModel3;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

/**
 *
 * @author Edwin
 */
@MappedSuperclass
public class SumamarySet extends UniqueEntityModel3 {

//    public static final String _election = "election";
//    @JoinColumn(name = "election")
//    @ManyToOne
//    private Election election;
//
//    public static final String _constituency = "constituency";
//    public static final String _constituency_constituencyName = _constituency + "." + Constituency._constituencyName;
//    @JoinColumn(name = "constituency")
//    @ManyToOne
//    private Constituency constituency;
//
//    public static final String _region = "region";
//    @JoinColumn(name = "region")
//    @ManyToOne
//    private Region region;
//
//    public static final String _resultSource = "resultSource";
//    @Column(name = "result_source")
//    @Enumerated(EnumType.STRING)
//    private ResultSource resultSource;

//    public static final String _resultStatus = "resultStatus";
//    @Column(name = "result_status")
//    @Enumerated(EnumType.STRING)
//    private ResultStatus resultStatus;
    

    public static final String _votersCount = "votersCount";
    @Column(name = "voters_count")
    private int votersCount;

    public static final String _validVotesPresidential = "validVotesPresidential";
    @Column(name = "valid_votes_presidential")
    private int validVotesPresidential;

    public static final String _rejectedBallotsPresidential = "rejectedBallotsPresidential";
    @Column(name = "rejected_ballots_presidential")
    private int rejectedBallotsPresidential;

    public static final String _spoiltBallotsPresidential = "spoiltBallotsPresidential";
    @Column(name = "spoilt_ballots_presidential")
    private int spoiltBallotsPresidential;
    
      public static final String _votesCastPresidential = "votesCastPresidential";
    @Column(name = "votes_cast_presidential")
    private int votesCastPresidential;

    public static final String _voterTurnoutPresidential = "voterTurnoutPresidential";
    @Column(name = "voter_turnout_presidential")
    private double voterTurnoutPresidential;
    
    
    public static final String _validVotesParliamentary = "validVotesPresidential";
    @Column(name = "valid_votes_parliamentary")
    private int validVotesParliamentary;

    public static final String _rejectedBallotsParliamentary = "rejectedBallotsPresidential";
    @Column(name = "rejected_ballots_parliamentary")
    private int rejectedBallotsParliamentary;
    
         public static final String _votesCastParliamentary = "votesCastParliamentary";
    @Column(name = "votes_cast_parliamentary")
    private int votesCastParliamentary;

    public static final String _spoiltBallotsParliamentary = "spoiltBallotsPresidential";
    @Column(name = "spoilt_ballots_parliamentary")
    private int spoiltBallotsParliamentary;

    public static final String _voterTurnoutParliamentary = "voterTurnoutPresidential";
    @Column(name = "voter_turnout_parliamentary")
    private double voterTurnoutParliamentary;

   
    
    public int getVotersCount() {
        return votersCount;
    }

    public void setVotersCount(int votersCount) {
        this.votersCount = votersCount;
    }

    public int getValidVotesPresidential() {
        return validVotesPresidential;
    }

    public void setValidVotesPresidential(int validVotesPresidential) {
        this.validVotesPresidential = validVotesPresidential;
    }

    public int getRejectedBallotsPresidential() {
        return rejectedBallotsPresidential;
    }

    public void setRejectedBallotsPresidential(int rejectedBallotsPresidential) {
        this.rejectedBallotsPresidential = rejectedBallotsPresidential;
    }

    public int getSpoiltBallotsPresidential() {
        return spoiltBallotsPresidential;
    }

    public void setSpoiltBallotsPresidential(int spoiltBallotsPresidential) {
        this.spoiltBallotsPresidential = spoiltBallotsPresidential;
    }

    public double getVoterTurnoutPresidential() {
        return voterTurnoutPresidential;
    }

    public void setVoterTurnoutPresidential(double voterTurnoutPresidential) {
        this.voterTurnoutPresidential = voterTurnoutPresidential;
    }

    public int getValidVotesParliamentary() {
        return validVotesParliamentary;
    }

    public void setValidVotesParliamentary(int validVotesParliamentary) {
        this.validVotesParliamentary = validVotesParliamentary;
    }

    public int getRejectedBallotsParliamentary() {
        return rejectedBallotsParliamentary;
    }

    public void setRejectedBallotsParliamentary(int rejectedBallotsParliamentary) {
        this.rejectedBallotsParliamentary = rejectedBallotsParliamentary;
    }

    public int getSpoiltBallotsParliamentary() {
        return spoiltBallotsParliamentary;
    }

    public void setSpoiltBallotsParliamentary(int spoiltBallotsParliamentary) {
        this.spoiltBallotsParliamentary = spoiltBallotsParliamentary;
    }

    public double getVoterTurnoutParliamentary() {
        return voterTurnoutParliamentary;
    }

    public void setVoterTurnoutParliamentary(double voterTurnoutParliamentary) {
        this.voterTurnoutParliamentary = voterTurnoutParliamentary;
    }

    public int getVotesCastPresidential() {
        return votesCastPresidential;
    }

    public void setVotesCastPresidential(int votesCastPresidential) {
        this.votesCastPresidential = votesCastPresidential;
    }

    public int getVotesCastParliamentary() {
        return votesCastParliamentary;
    }

    public void setVotesCastParliamentary(int votesCastParliamentary) {
        this.votesCastParliamentary = votesCastParliamentary;
    }

    
 

}
