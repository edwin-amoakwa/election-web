/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.gafpv.entities;

import com.stately.common.constants.DebitCredit;
import com.stately.common.model.LocalDateRange;
import com.statelyhub.gafpv.constants.AccountType;

/**
 *
 * @author Edwin
 */
public class LedgerQP {

    private LocalDateRange dateRange = new LocalDateRange();
    private AccountType accountType;

    private LedgerAccount account;
    
    private DebitCredit debitCredit = DebitCredit.CREDIT;
    
    private String narration;
    
    private Supplier counterparty;

    public LocalDateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(LocalDateRange dateRange) {
        this.dateRange = dateRange;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public LedgerAccount getAccount() {
        return account;
    }

    public void setAccount(LedgerAccount account) {
        this.account = account;
    }

    public DebitCredit getDebitCredit() {
        return debitCredit;
    }

    public void setDebitCredit(DebitCredit debitCredit) {
        this.debitCredit = debitCredit;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public Supplier getCounterparty() {
        return counterparty;
    }

    public void setCounterparty(Supplier counterparty) {
        this.counterparty = counterparty;
    }
    

    
}
