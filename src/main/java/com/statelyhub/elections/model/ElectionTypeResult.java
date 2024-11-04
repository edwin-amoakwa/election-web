/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.model;

import com.statelyhub.elections.constants.ElectionType;
import com.statelyhub.elections.entities.PollingStationResult;
import java.util.List;

/**
 *
 * @author edwin
 */
public class ElectionTypeResult {

    private ElectionType electionType;
    private List<PollingStationResult> resultsList;

    public ElectionType getElectionType() {
        return electionType;
    }

    public void setElectionType(ElectionType electionType) {
        this.electionType = electionType;
    }

    public List<PollingStationResult> getResultsList() {
        return resultsList;
    }

    public void setResultsList(List<PollingStationResult> resultsList) {
        this.resultsList = resultsList;
    }
    

}
