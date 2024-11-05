/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.model;

import com.statelyhub.elections.entities.ElectionPollingStation;
import com.statelyhub.elections.entities.PollingStationResult;
import com.statelyhub.elections.entities.SubmittedResult;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author edwin
 */
public class PollingStationResultContainer {
    
    
    
    
    private ElectionPollingStation pollingStation;
    private List<ElectionTypeResult> electionResults;

    public PollingStationResultContainer(ElectionPollingStation pollingStation, List<ElectionTypeResult> electionResults) {
        this.pollingStation = pollingStation;
        this.electionResults = electionResults;
    }
    
    

    public ElectionPollingStation getPollingStation() {
        return pollingStation;
    }

    public void setPollingStation(ElectionPollingStation pollingStation) {
        this.pollingStation = pollingStation;
    }

    public List<ElectionTypeResult> getElectionResults() {
        return electionResults;
    }

    public void setElectionResults(List<ElectionTypeResult> electionResults) {
        this.electionResults = electionResults;
    }
    
    public List<SubmittedResult> submitedResults()
    {
        List<SubmittedResult> submittedResults = new LinkedList<>();
        
        for (ElectionTypeResult electionResult : electionResults) {
            submittedResults.addAll(electionResult.getSubmittedResultsList());
        }
        
        return submittedResults;
    }
    
     public static  List<SubmittedResult> submitedResults(List<ElectionTypeResult> electionResults)
    {
        List<SubmittedResult> submittedResults = new LinkedList<>();
        
        for (ElectionTypeResult electionResult : electionResults) {
            submittedResults.addAll(electionResult.getSubmittedResultsList());
        }
        
        return submittedResults;
    }
     
     
         
     public static  List<PollingStationResult> stationResult(List<ElectionTypeResult> electionResults)
    {
        List<PollingStationResult> submittedResults = new LinkedList<>();
        
        for (ElectionTypeResult electionResult : electionResults) {
            submittedResults.addAll(electionResult.getVotingsList());
        }
        
        return submittedResults;
    }
    
}
