/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.gafpv.entities;

import com.stately.modules.jpa2.UniqueEntityModel2;
import com.statelyhub.gafpv.constants.AccountType;
import java.io.Serializable;
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
@Table(name = "suppliers")
public class Supplier extends UniqueEntityModel2 implements Serializable
{
    public static final String _accountType = "accountType";
    @Column(name = "account_type")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    
    @Column(name = "supplier_id")
    private String supplierId;
    
    @Column(name = "supplier_name")
    private String supplierName;
    
    @Column(name = "supplier_address")
    private String supplierAddress;

    public String getSupplierId()
    {
        return supplierId;
    }

    public void setSupplierId(String supplierId)
    {
        this.supplierId = supplierId;
    }

    public String getSupplierName()
    {
        return supplierName;
    }

    public void setSupplierName(String supplierName)
    {
        this.supplierName = supplierName;
    }

    public String getSupplierAddress()
    {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress)
    {
        this.supplierAddress = supplierAddress;
    }

    @Override
    public String toString()
    {
        return supplierName;
    }
    
    
    
}
