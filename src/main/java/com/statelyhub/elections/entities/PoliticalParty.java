/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.entities;

import com.stately.modules.jpa2.UniqueEntityModel3;
import com.statelyhub.elections.constants.PartyType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "political_party")
public class PoliticalParty extends UniqueEntityModel3 {

        public static final String _partyType = "partyType";
    @Column(name = "party_type")
    @Enumerated(EnumType.STRING)
    private PartyType partyType;

    
     public static final String _initials = "initials";
    @Column(name = "initials")
    private String initials;
    
    
    public static final String _partyName = "partyName";
    @Column(name = "party_name")
    private String partyName;

    public PartyType getPartyType() {
        return partyType;
    }

    public void setPartyType(PartyType partyType) {
        this.partyType = partyType;
    }

    
    
    
    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public boolean isPoliticalParty()
    {
      if(getPartyType() == null)return false;
      return getPartyType().isPoliticalPartyType();   
    }
    
    @Override
    public String toString() {
        return initials;
    }
    

    
}
