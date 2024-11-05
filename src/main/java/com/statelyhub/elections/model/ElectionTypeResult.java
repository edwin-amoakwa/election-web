/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.model;

import com.statelyhub.elections.constants.ElectionType;
import com.statelyhub.elections.entities.PollingStationResult;
import com.statelyhub.elections.entities.SubmittedResult;
import java.util.List;

/**
 *
 * @author edwin
 */
public class ElectionTypeResult {

    private ElectionType electionType;
    
    private List<PollingStationResult> votingsList;
    
    private List<SubmittedResult> submittedResultsList;

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
    

}
