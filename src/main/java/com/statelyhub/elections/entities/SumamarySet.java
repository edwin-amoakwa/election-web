/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.entities;

import com.stately.modules.jpa2.UniqueEntityModel3;
import com.statelyhub.elections.constants.ElectionType;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

/**
 *
 * @author Edwin
 */
@MappedSuperclass
public class SumamarySet extends UniqueEntityModel3 {

    public static final String _votersCount = "votersCount";
    @Column(name = "voters_count")
    private int votersCount;
    
    
    public static final String _validVotesPresidential = "validVotesPresidential";
    @Column(name = "valid_votes_presidential")
    private int validVotesPresidential;

    public static final String _totalVotesCastPresidential = "totalVotesCastPresidential";
    @Column(name = "total_votes_cost_presidential")
    private int totalVotesCastPresidential;

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
    
    public static final String _totalVotesCastParliamentary = "totalVotesCastParliamentary";
    @Column(name = "total_votes_cost_parliamentary")
    private int totalVotesCastParliamentary;

    public static final String _spoiltBallotsParliamentary = "spoiltBallotsPresidential";
    @Column(name = "spoilt_ballots_parliamentary")
    private int spoiltBallotsParliamentary;

    public static final String _voterTurnoutParliamentary = "voterTurnoutPresidential";
    @Column(name = "voter_turnout_parliamentary")
    private double voterTurnoutParliamentary;

   public void update(ResultSet resultSet)
   {
       if(resultSet.getElectionType() == ElectionType.PRESIDENTIAL)
       {
           validVotesPresidential = resultSet.getValidVotes();
           rejectedBallotsPresidential = resultSet.getRejectedBallots();
           spoiltBallotsPresidential = resultSet.getSpoiltBallots();
           validVotesPresidential = resultSet.getTotalVotesCast();
           totalVotesCastPresidential = resultSet.getTotalVotesCast();
       }
       else if(resultSet.getElectionType() == ElectionType.PARLIAMENTARY)
       {
           validVotesParliamentary = resultSet.getValidVotes();
           rejectedBallotsParliamentary = resultSet.getRejectedBallots();
           spoiltBallotsParliamentary = resultSet.getSpoiltBallots();
           validVotesParliamentary = resultSet.getTotalVotesCast();
           totalVotesCastParliamentary = resultSet.getTotalVotesCast();
       }
   }
    
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

    public int getTotalVotesCastPresidential() {
        return totalVotesCastPresidential;
    }

    public void setTotalVotesCastPresidential(int totalVotesCastPresidential) {
        this.totalVotesCastPresidential = totalVotesCastPresidential;
    }

    public int getTotalVotesCastParliamentary() {
        return totalVotesCastParliamentary;
    }

    public void setTotalVotesCastParliamentary(int totalVotesCastParliamentary) {
        this.totalVotesCastParliamentary = totalVotesCastParliamentary;
    }

    
 

}
