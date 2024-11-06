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
@Table(name = "constituency")
public class Constituency extends UniqueEntityModel3 {

    public static final String _constituencyName = "constituencyName";
    @Column(name = "constituency_name")
    private String constituencyName;

    public static final String _region = "region";
    public static final String _region_id = _region+"."+Region._id;
    @JoinColumn(name = "region")
    @ManyToOne
    private Region region;
    
    
    public static final String _district = "district";
    @JoinColumn(name = "district")
    @ManyToOne
    private DistrictAssembly district;

    public String getConstituencyName() {
        return constituencyName;
    }

    public void setConstituencyName(String constituencyName) {
        this.constituencyName = constituencyName;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public DistrictAssembly getDistrict() {
        return district;
    }

    public void setDistrict(DistrictAssembly district) {
        this.district = district;
    }

    @Override
    public String toString() {
        return constituencyName;
    }
    
    

}
