/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.gafpv.entities;

import com.stately.modules.jpa2.UniqueEntityModel3;
import com.statelyhub.gafpv.constants.AccountType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "ledger_accounts")
public class LedgerAccount extends UniqueEntityModel3 {

    public static final String _department = "department";
    @JoinColumn(name = "department")
    @ManyToOne
    private Department department;

    public static final String _accountType = "accountType";
    @Column(name = "account_type")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    public static final String _accountCode = "accountCode";
    @Column(name = "account_code")
    private String accountCode;

    public static final String _accountName = "accountName";
    @Column(name = "account_name")
    private String accountName;

    public static final String _unit = "unit";
    @JoinColumn(name = "unit")
    @ManyToOne
    private Institution unit;
    
    @Transient
    private double balance;

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Override
    public String toString() {
        return accountCode + "-" + accountName;
    }

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

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
