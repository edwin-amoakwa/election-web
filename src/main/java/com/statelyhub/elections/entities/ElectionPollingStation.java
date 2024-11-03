/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.entities;

import com.stately.modules.jpa2.UniqueEntityModel3;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "election_polling_station")
public class ElectionPollingStation extends UniqueEntityModel3 {

    public static final String _votersCount = "votersCount";
    @Column(name = "voters_count")
    private int votersCount;
    
    public static final String _election = "election";
    @JoinColumn(name = "election")
    @ManyToOne
    private Election election;
    
    public static final String _pollingStation = "pollingStation";
    @JoinColumn(name = "polling_station")
    @ManyToOne
    private PollingStation pollingStation;
    
    
    
    
}
