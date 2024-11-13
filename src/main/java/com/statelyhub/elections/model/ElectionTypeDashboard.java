/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.model;

import com.statelyhub.elections.entities.ElectionContestant;
import java.util.List;

/**
 *
 * @author edwin
 */
public class ElectionTypeDashboard {
    
    private int totalPollingStation;
    private int pendingSubmission;
    private int unprocessedSubmission;
    private int totalProcessed;
    private int rejectedBallot;
    private int totalRegisteredVoters;
    private double processedPct;
    
    private int pollingStationCompleted;
    private int pollingStationPending;
    private double pollingStationPct;
    private int validVotes;
       private int spoiltVotes;
    
    
    
     private int votesCounted;
    
       private List<PresidentialResult> mainResultList;
      
        private List<ElectionContestant> contestantsList;

    public int getTotalPollingStation() {
        return totalPollingStation;
    }

    public void setTotalPollingStation(int totalPollingStation) {
        this.totalPollingStation = totalPollingStation;
    }

    public int getPendingSubmission() {
        return pendingSubmission;
    }

    public void setPendingSubmission(int pendingSubmission) {
        this.pendingSubmission = pendingSubmission;
    }

    public int getUnprocessedSubmission() {
        return unprocessedSubmission;
    }

    public void setUnprocessedSubmission(int unprocessedSubmission) {
        this.unprocessedSubmission = unprocessedSubmission;
    }

    public int getTotalProcessed() {
        return totalProcessed;
    }

    public void setTotalProcessed(int totalProcessed) {
        this.totalProcessed = totalProcessed;
    }

    public int getRejectedBallot() {
        return rejectedBallot;
    }

    public void setRejectedBallot(int rejectedBallot) {
        this.rejectedBallot = rejectedBallot;
    }

    public int getTotalRegisteredVoters() {
        return totalRegisteredVoters;
    }

    public void setTotalRegisteredVoters(int totalRegisteredVoters) {
        this.totalRegisteredVoters = totalRegisteredVoters;
    }

    public double getProcessedPct() {
        return processedPct;
    }

    public void setProcessedPct(double processedPct) {
        this.processedPct = processedPct;
    }

    public List<PresidentialResult> getMainResultList() {
        return mainResultList;
    }

    public void setMainResultList(List<PresidentialResult> mainResultList) {
        this.mainResultList = mainResultList;
    }

    public int getPollingStationCompleted() {
        return pollingStationCompleted;
    }

    public void setPollingStationCompleted(int pollingStationCompleted) {
        this.pollingStationCompleted = pollingStationCompleted;
    }

    public int getPollingStationPending() {
        return pollingStationPending;
    }

    public void setPollingStationPending(int pollingStationPending) {
        this.pollingStationPending = pollingStationPending;
    }

    public double getPollingStationPct() {
        return pollingStationPct;
    }

    public void setPollingStationPct(double pollingStationPct) {
        this.pollingStationPct = pollingStationPct;
    }

    public int getVotesCounted() {
        return votesCounted;
    }

    public void setVotesCounted(int votesCounted) {
        this.votesCounted = votesCounted;
    }

    public int getValidVotes() {
        return validVotes;
    }

    public void setValidVotes(int validVotes) {
        this.validVotes = validVotes;
    }

    public int getSpoiltVotes() {
        return spoiltVotes;
    }

    public void setSpoiltVotes(int spoiltVotes) {
        this.spoiltVotes = spoiltVotes;
    }

    public List<ElectionContestant> getContestantsList() {
        return contestantsList;
    }

    public void setContestantsList(List<ElectionContestant> contestantsList) {
        this.contestantsList = contestantsList;
    }
       
       
       
       
    
}
