/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.entities;

import com.statelyhub.elections.constants.PartyType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "election_contestant")
public class ElectionContestant extends Result {

    public static final String _party = "party";
    @JoinColumn(name = "party")
    @ManyToOne
    private PoliticalParty party;
    
    public static final String _candidateType = "candidateType";
    @Column(name = "candidate_type")
    @Enumerated(EnumType.STRING)
    private PartyType candidateType;
    
    public boolean isPoliticalParty()
    {
      if(getCandidateType()== null)return false;
      return getCandidateType().isPoliticalPartyType();   
    }

    public PoliticalParty getParty() {
        return party;
    }

    public void setParty(PoliticalParty party) {
        this.party = party;
    }

    public PartyType getCandidateType() {
        return candidateType;
    }

    public void setCandidateType(PartyType candidateType) {
        this.candidateType = candidateType;
    }
   
    
}
