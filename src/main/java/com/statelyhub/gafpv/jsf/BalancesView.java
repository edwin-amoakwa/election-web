/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.gafpv.jsf;

import com.stately.common.data.MoneySummary;
import com.stately.common.formating.ObjectValue;
import com.stately.common.model.LocalDateRange;
import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.gafpv.constants.AccountType;
import com.statelyhub.gafpv.entities.GeneralLedger;
import com.statelyhub.gafpv.entities.LedgerAccount;
import com.statelyhub.gafpv.service.CrudService;
import com.statelyhub.gafpv.service.LedgerService;
import jakarta.annotation.PostConstruct;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.Arrays;

/**
 *
 * @author Edwin
 */
//@RequestScoped
@SessionScoped
@Named(value = "balancesView")
public class BalancesView implements Serializable
{

    @Inject private UserSession userSession;
    @Inject private CrudService crudService;
    
    @Inject private LedgerService ledgerService;

    private List<LedgerAccount> bankAccountsList;
    private List<GeneralLedger> ledgerAccountsList;
    private List<LedgerAccount> ledgerTypesList;
    
    
    private MoneySummary glSummary;
    
    
    private LocalDateRange dateRange = new LocalDateRange(null, LocalDate.now());

    @PostConstruct
    public void init()
    {
        bankAccountsList = ledgerService.search(userSession.getInstitution(), Arrays.asList(AccountType.BANK, AccountType.CASH));
        
        for (LedgerAccount account : bankAccountsList)
        {
            account.setBalance(-ledgerService.accountBalance(account, dateRange.getToDate()));
        }

        List<Object[]> objectList = QryBuilder.get(crudService.getEm(), GeneralLedger.class)
                .addReturnField("e." + GeneralLedger._account)
                .addReturnField(GeneralLedger.SUM_CREDIT)
                .addReturnField(GeneralLedger.SUM_DEBIT)
                .addObjectParam(GeneralLedger._unit, userSession.getInstitution())
                .addDateRange(dateRange, GeneralLedger._valueDate)
                .addGroupBy(GeneralLedger._account_id)
                .buildQry().getResultList();

        ledgerAccountsList = new LinkedList<>();
        for (Object[] objects : objectList)
        {
            GeneralLedger ledger = new GeneralLedger();
            LedgerAccount ledgerAccount = (LedgerAccount) objects[0];
            ledger.setAccount(ledgerAccount);
            
            ledger.setCredit(ObjectValue.get_doubleValue(objects[1]));
            ledger.setDebit(ObjectValue.get_doubleValue(objects[2]));
                
                ledgerAccountsList.add(ledger);
        }
        
        glSummary = MoneySummary.calc(ledgerAccountsList);

//        objectList = QryBuilder.get(crudService.getEm(), CashTransaction.class)
//                .addReturnField("e." + CashTransaction._ledgerAccount_ledgerAccountType)
//                .addReturnField(CashTransaction.SUM)
//                .addDateRange(dateRange, CashTransaction._valueDate)
//                .addObjectParam(CashTransaction._branch, userSession.getBranchUR())
//                .addGroupBy(CashTransaction._ledgerAccount_ledgerAccountType)
//                .buildQry().getResultList();
//
//        ledgerTypesList = new LinkedList<>();
//        for (Object[] objects : objectList)
//        {
//            LedgerAccount ledgerAccount = new LedgerAccount();
//            LedgerAccountType tye = (LedgerAccountType) objects[0];
//            
//            if(tye != null)
//            {
//                ledgerAccount.setAccountType(tye);
//                ledgerAccount.setAccountName(tye + "");
//            }
//            
//            
//            ledgerAccount.setBalance(ObjectValue.get_doubleValue(objects[1]));
//            ledgerTypesList.add(ledgerAccount);
//        }
        

        
    }
    
    

    public List<LedgerAccount> getBankAccountsList()
    {
        return bankAccountsList;
    }

    public List<GeneralLedger> getLedgerAccountsList()
    {
        return ledgerAccountsList;
    }
    
    public LocalDateRange getDateRange()
    {
        return dateRange;
    }

    public void setDateRange(LocalDateRange dateRange)
    {
        this.dateRange = dateRange;
    }
    
    public List<LedgerAccount> getLedgerTypesList()
    {
        return ledgerTypesList;
    }

    

    public MoneySummary getGlSummary() {
        return glSummary;
    }

    
}
