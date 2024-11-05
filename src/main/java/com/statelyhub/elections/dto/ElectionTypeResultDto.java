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
    
}
