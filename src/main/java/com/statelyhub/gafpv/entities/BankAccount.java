/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.gafpv.entities;

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
@Table(name = "bank_accounts")
public class BankAccount extends UniqueEntityModel2
{
    public static final String _department = "department";
    @JoinColumn(name = "department")
    @ManyToOne
    private Department department;
    
    public static final String _accountNo = "accountNo";
    @Column(name = "account_no")
    private String accountNo;
    
    public static final String _accountName = "accountName";
    @Column(name = "account_name")
    private String accountName;
    
    public static final String _bankName = "bankName";
    @Column(name = "bank_name")
    private String bankName;
       
    public static final String _unit = "unit";
    @JoinColumn(name = "unit")
    @ManyToOne
    private Institution unit;
    
    
    public static final String _ledgerAccount = "ledgerAccount";
    @JoinColumn(name = "ledger_account")
    @ManyToOne
    private LedgerAccount ledgerAccount;

    public String getAccountNo()
    {
        return accountNo;
    }

    public void setAccountNo(String accountNo)
    {
        this.accountNo = accountNo;
    }

    public String getAccountName()
    {
        return accountName;
    }

    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }

    public String getBankName()
    {
        return bankName;
    }

    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }

    @Override
    public String toString()
    {
        return accountNo + " - " + accountName;
    }

    public Department getDepartment()
    {
        return department;
    }

    public void setDepartment(Department department)
    {
        this.department = department;
    }

    public Institution getUnit()
    {
        return unit;
    }

    public void setUnit(Institution unit)
    {
        this.unit = unit;
    }

    public LedgerAccount getLedgerAccount() {
        return ledgerAccount;
    }

    public void setLedgerAccount(LedgerAccount ledgerAccount) {
        this.ledgerAccount = ledgerAccount;
    }
    
    
    
    
}
