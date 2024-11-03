/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.entities;

import com.statelyhub.elections.constants.ElectionType;
import com.stately.modules.jpa2.UniqueEntityModel3;
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
@Table(name = "candidates")
public class Candidate extends UniqueEntityModel3 {

    public static final String _candidateName = "candidateName";
    @Column(name = "candidate_name")
    private String candidateName;

    public static final String _party = "party";
    @JoinColumn(name = "party")
    @ManyToOne
    private PoliticalParty party;
    
    
    public static final String _seat = "seat";
    @Column(name = "seat")
    @Enumerated(EnumType.STRING)
    private ElectionType seat;

    
}
