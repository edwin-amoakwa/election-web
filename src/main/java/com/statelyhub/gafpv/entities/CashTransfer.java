/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.gafpv.entities;

import com.stately.common.constants.DebitCredit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 *
 * @author Stately
 */
@Entity 
@Table(name = "cash_transfers")
public class CashTransfer  extends UnitDatedRecord 
{
    public static final String _fromAccount = "fromAccount";
    public static final String _fromAccount_id = _fromAccount+"."+LedgerAccount._id;
    @JoinColumn(name="from_account")
    @ManyToOne
    private LedgerAccount fromAccount;
    
    public static final String _toAccount = "toAccount";
    public static final String _toAccount_id = _toAccount+"."+LedgerAccount._id;
    @JoinColumn(name="to_account")
    @ManyToOne
    private LedgerAccount toAccount;
    
    public static final String _amount = "amount";
    @Column(name = "amount")
    private double amount;
    
    public static final String _narration = "narration";
    @Column(name = "narration")
    private String narration;

    public LedgerAccount getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(LedgerAccount fromAccount) {
        this.fromAccount = fromAccount;
    }

    public LedgerAccount getToAccount() {
        return toAccount;
    }

    public void setToAccount(LedgerAccount toAccount) {
        this.toAccount = toAccount;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public GeneralLedger fromLedger()
    {
        GeneralLedger ledger = new GeneralLedger();
        ledger.setBatch(this.getId());
        ledger.setAmount(getAmount());
        ledger.setUnit(getUnit());
        ledger.setCredit(getAmount());
        ledger.setDebitCredit(DebitCredit.CREDIT);
        ledger.setAccount(getFromAccount());
        ledger.setValueDate(getValueDate());
        ledger.setNarration(narration);
        return ledger;
    }
    
    public GeneralLedger toLedger()
    {
        GeneralLedger ledger = new GeneralLedger();
        ledger.setBatch(this.getId());
        ledger.setAmount(getAmount());
        ledger.setUnit(getUnit());
        ledger.setDebit(-getAmount());
        ledger.setDebitCredit(DebitCredit.DEBIT);
        ledger.setAccount(getToAccount());
        ledger.setValueDate(getValueDate());
         ledger.setNarration(narration);
        return ledger;
    }
    
}
