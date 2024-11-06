/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.dto;

import com.statelyhub.elections.constants.ElectionType;
import java.util.List;

/**
 *
 * @author edwin
 */
public class ElectionTypeResultDto {
    private ElectionType electionType;
    private int rejectedBallots;
    private int spoiltBallots;
    
    private List<SubmittedResultDto> candidatesList;

    public ElectionType getElectionType() {
        return electionType;
    }

    public void setElectionType(ElectionType electionType) {
        this.electionType = electionType;
    }

    public List<SubmittedResultDto> getCandidatesList() {
        return candidatesList;
    }

    public void setCandidatesList(List<SubmittedResultDto> candidatesList) {
        this.candidatesList = candidatesList;
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
    
}
