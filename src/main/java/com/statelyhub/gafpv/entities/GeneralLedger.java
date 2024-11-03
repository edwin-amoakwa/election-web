/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.gafpv.entities;

import com.stately.common.constants.DebitCredit;
import com.stately.common.data.CashTran;
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
@Table(name = "general_ledger")
public class GeneralLedger extends UnitDatedRecord implements CashTran {

    public static final String SUM = "SUM(e.debit + e.credit)";
    public static final String SUM_DEBIT = "SUM(e.debit)";
    public static final String SUM_CREDIT = "SUM(e.credit)";

    public static final String _account = "account";
    public static final String _account_id = _account + "." + LedgerAccount._id;
    public static final String _account_accountType = _account + "." + LedgerAccount._accountType;
    @JoinColumn(name = "account")
    @ManyToOne
    private LedgerAccount account;

    public static final String _debit = "debit";
    @Column(name = "debit")
    private double debit;

    public static final String _credit = "credit";
    @Column(name = "credit")
    private double credit;

    public static final String _amount = "amount";
    @Column(name = "amount")
    private double amount;

    public static final String _debitCredit = "debitCredit";
    @Column(name = "debit_credit")
    @Enumerated(EnumType.STRING)
    private DebitCredit debitCredit = DebitCredit.CREDIT;

    public static final String _narration = "narration";
    @Column(name = "narration")
    private String narration;

    public static final String _counterparty = "counterparty";
    @JoinColumn(name = "counterparty")
    @ManyToOne
    private Supplier counterparty;

    public static final String _batch = "batch";
    @Column(name = "batch")
    private String batch;
    
    @Transient
        private AccountType accountType;
    
        @Transient
    private double balance;

    public LedgerAccount getAccount() {
        return account;
    }

    public void setAccount(LedgerAccount account) {
        this.account = account;
    }

    public double getDebit() {
        return debit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public DebitCredit getDebitCredit() {
        return debitCredit;
    }

    public void setDebitCredit(DebitCredit debitCredit) {
        this.debitCredit = debitCredit;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
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

    @Override
    public String toString() {
        return narration;
    }

}
