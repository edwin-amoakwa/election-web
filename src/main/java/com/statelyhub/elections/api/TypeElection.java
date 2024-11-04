/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.api;

import com.statelyhub.elections.constants.ElectionType;
import java.util.List;

/**
 *
 * @author edwin
 */
public class TypeElection {
    private ElectionType seat;
    private int rejectedBallot;
    private List<CandidateResultDto> candidateResultList;

    public ElectionType getSeat() {
        return seat;
    }

    public void setSeat(ElectionType seat) {
        this.seat = seat;
    }

    public int getRejectedBallot() {
        return rejectedBallot;
    }

    public void setRejectedBallot(int rejectedBallot) {
        this.rejectedBallot = rejectedBallot;
    }

    public List<CandidateResultDto> getCandidateResultList() {
        return candidateResultList;
    }

    public void setCandidateResultList(List<CandidateResultDto> candidateResultList) {
        this.candidateResultList = candidateResultList;
    }
    
}
