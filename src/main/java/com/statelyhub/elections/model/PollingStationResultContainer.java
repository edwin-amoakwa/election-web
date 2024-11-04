/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.model;

import com.statelyhub.elections.entities.ElectionPollingStation;
import java.util.List;

/**
 *
 * @author edwin
 */
public class PollingStationResultContainer {
    private ElectionPollingStation pollingStation;
    private List<ElectionTypeResult> electionResults;

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
    
}
