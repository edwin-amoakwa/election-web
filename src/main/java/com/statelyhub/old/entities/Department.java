/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.old.entities;

import com.stately.modules.jpa2.UniqueEntityModel2;
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
@Table(name = "departments")
public class Department extends UniqueEntityModel2
{
    public static final String _accountNo = "accountNo";
    @Column(name = "dept_id")
    private String deptId;
    
    public static final String _departmentName = "departmentName";
    @Column(name = "department_name")
    private String departmentName;
    
       
    public static final String _unit = "unit";
    @JoinColumn(name = "unit")
    @ManyToOne
    private Institution unit;

    public String getDeptId()
    {
        return deptId;
    }

    public void setDeptId(String deptId)
    {
        this.deptId = deptId;
    }

    public String getDepartmentName()
    {
        return departmentName;
    }

    public void setDepartmentName(String departmentName)
    {
        this.departmentName = departmentName;
    }

    @Override
    public String toString()
    {
        return departmentName;
    }

    public Institution getUnit()
    {
        return unit;
    }

    public void setUnit(Institution unit)
    {
        this.unit = unit;
    }
    
    
    
    
    
    
}
