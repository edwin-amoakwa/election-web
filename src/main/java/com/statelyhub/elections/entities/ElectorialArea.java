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
@Table(name = "electorial_area")
public class ElectorialArea extends UniqueEntityModel3 {

    public static final String _stationName = "stationName";
    @Column(name = "area_name")
    private String areaName;

    public static final String _constituency = "constituency";
    @JoinColumn(name = "constituency")
    @ManyToOne
    private Constituency constituency;

    

    
}
