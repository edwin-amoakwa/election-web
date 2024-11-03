/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.gafpv.entities;

import com.stately.modules.jpa2.DateRecord3;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

/**
 *
 * @author Edwin
 */
@MappedSuperclass
public class UnitDatedRecord extends DateRecord3
{    
    
    public static final String _department = "department";
    @JoinColumn(name = "department")
    @ManyToOne
    private Department department;
    
       
    public static final String _unit = "unit";
    @JoinColumn(name = "unit")
    @ManyToOne
    private Institution unit;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Institution getUnit() {
        return unit;
    }

    public void setUnit(Institution unit) {
        this.unit = unit;
    }
    
}
