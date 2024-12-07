/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.model;

import com.statelyhub.elections.constants.ElectionType;
import com.statelyhub.elections.entities.ConstituencyResultSet;
import com.statelyhub.elections.entities.ElectionContestant;
import com.statelyhub.elections.entities.PollingStationResult;
import com.statelyhub.elections.entities.SubmittedResult;
import java.util.List;

/**
 *
 * @author edwin
 */
public class ElectionTypeResult {

    private ElectionType electionType;
    private ElectionTypeDashboard dashboard;
    private ConstituencyResultSet constituencySet;
    
    private List<PollingStationResult> votingsList;
    
    private List<SubmittedResult> submittedResultsList;
    
     private List<ElectionContestant> contestantsList;

    public ElectionType getElectionType() {
        return electionType;
    }

    public void setElectionType(ElectionType electionType) {
        this.electionType = electionType;
    }

    public List<PollingStationResult> getVotingsList() {
        return votingsList;
    }

    public void setVotingsList(List<PollingStationResult> votingsList) {
        this.votingsList = votingsList;
    }

    public List<SubmittedResult> getSubmittedResultsList() {
        return submittedResultsList;
    }

    public void setSubmittedResultsList(List<SubmittedResult> submittedResultsList) {
        this.submittedResultsList = submittedResultsList;
    }

    public List<ElectionContestant> getContestantsList() {
        return contestantsList;
    }

    public void setContestantsList(List<ElectionContestant> contestantsList) {
        this.contestantsList = contestantsList;
    }

    public ElectionTypeDashboard getDashboard() {
        return dashboard;
    }

    public void setDashboard(ElectionTypeDashboard dashboard) {
        this.dashboard = dashboard;
    }

    public ConstituencyResultSet getConstituencySet() {
        return constituencySet;
    }

    public void setConstituencySet(ConstituencyResultSet constituencySet) {
        this.constituencySet = constituencySet;
    }

    @Override
    public String toString() {
        return "ElectionTypeResult{" + "electionType=" + electionType + ", votingsList=" + votingsList + ", submittedResultsList=" + submittedResultsList + ", contestantsList=" + contestantsList + '}';
    }
    

}
