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
import com.statelyhub.gafpv.entities.LedgerQP;
import com.statelyhub.gafpv.service.CrudService;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author Edwin
 */
@SessionScoped
@Named(value = "generalLedgerRecordsController")
public class GeneralLedgerRecordsController implements Serializable
{

    @Inject
    private CrudService crudService;
    @Inject
    private UserSession userSession;

    private LedgerQP cashTransactionQP = new LedgerQP();

    private List<GeneralLedger> cashTransactionsList;
    private List<GeneralLedger> summaryCashTransactionByLedgerList;
    private List<GeneralLedger> summaryCashTransactionByLedgerTypeList;

    private LocalDateRange dateRange = new LocalDateRange();
    private MoneySummary summaryMoneySummary;
    private MoneySummary detailMoneySummary;
    private MoneySummary summaryByTypeMoneySummary;

    private int selectedTabIndex;

    public QryBuilder buildQryBuilder()
    {
        QryBuilder qryBuilder = new QryBuilder(crudService.getEm(), GeneralLedger.class);

   

        if (cashTransactionQP.getAccount()!= null)
        {
            qryBuilder.addObjectParam(GeneralLedger._account, cashTransactionQP.getAccount());
        }

        if (cashTransactionQP.getAccountType()!= null)
        {
            qryBuilder.addObjectParam(GeneralLedger._account_accountType, cashTransactionQP.getAccountType());
        }

        qryBuilder.addObjectParam(GeneralLedger._unit, userSession.getInstitution());

        qryBuilder.addDateRange(dateRange, GeneralLedger._valueDate);

        return qryBuilder;
    }

    public void search()
    {
        selectedTabIndex = 0;
        System.out.println("searching ....... ");

        QryBuilder qryBuilder = buildQryBuilder();

        qryBuilder.orderByAsc(GeneralLedger._valueDate);
        qryBuilder.orderByAsc(GeneralLedger._debitCredit);

        cashTransactionsList = qryBuilder.buildQry().getResultList();

        detailMoneySummary = MoneySummary.calc(cashTransactionsList);
        runningBalance(cashTransactionsList);
    }

    
     public void runningBalance(List<GeneralLedger> transactionsList)
    {
        if (transactionsList == null)
        {
            return;
        }
        

        double balance = 0;
        for (GeneralLedger cashTransaction : transactionsList)
        {
            balance += cashTransaction.getDebit() + cashTransaction.getCredit();
            cashTransaction.setBalance(balance);
        }
    }
    
    public void searchSummary()
    {

        selectedTabIndex = 1;
        QryBuilder qryBuilder = buildQryBuilder();

        List<Object[]> objectsList = qryBuilder
                .addReturnField("e." + GeneralLedger._account)
                .addReturnField("SUM(e.credit)")
                .addReturnField("SUM(e.debit)")
//                .addReturnField("e." + GeneralLedger._client)
                //                .addObjectParam(CashTransaction._client, selectedClient)
//                .addGroupBy(GeneralLedger._client_id)
                .addGroupBy(GeneralLedger._account_id)
                .buildQry().getResultList();

        summaryCashTransactionByLedgerList = new LinkedList<>();
        for (Object[] objects : objectsList)
        {
            GeneralLedger cashTransaction = new GeneralLedger();
            try
            {
                cashTransaction.setAccount((LedgerAccount) objects[0]);
            } catch (Exception e)
            {
                e.printStackTrace();
            }

            cashTransaction.setCredit(ObjectValue.get_doubleValue(objects[1]));
            cashTransaction.setDebit(ObjectValue.get_doubleValue(objects[2]));
            cashTransaction.setAmount(cashTransaction.getDebit() + cashTransaction.getCredit());

            try
            {
//                cashTransaction.setCustomer((Customer) objects[3]);
            } catch (Exception e)
            {
                e.printStackTrace();
            }

            summaryCashTransactionByLedgerList.add(cashTransaction);
        }

        summaryMoneySummary = MoneySummary.calc(summaryCashTransactionByLedgerList);
    }

    public void prepareByLedgerType()
    {
        selectedTabIndex = 2;
        List<Object[]> objectsList = buildQryBuilder()
                .addReturnField("e." + GeneralLedger._account_accountType)
                .addReturnField("SUM(e.credit)")
                .addReturnField("SUM(e.debit)")
//                .addReturnField("e." + GeneralLedger._client)
//                .addGroupBy(GeneralLedger._client_id)
                .addGroupBy(GeneralLedger._account_accountType)
                .buildQry().getResultList();

        summaryCashTransactionByLedgerTypeList = new LinkedList<>();
        for (Object[] objects : objectsList)
        {
            GeneralLedger cashTransaction = new GeneralLedger();
            try
            {
                cashTransaction.setAccountType((AccountType) objects[0]);
            } catch (Exception e)
            {
                e.printStackTrace();
            }

            cashTransaction.setCredit(ObjectValue.get_doubleValue(objects[1]));
            cashTransaction.setDebit(ObjectValue.get_doubleValue(objects[2]));
            cashTransaction.setAmount(cashTransaction.getDebit() + cashTransaction.getCredit());

            try
            {
//                cashTransaction.setCustomer((Customer) objects[3]);
            } catch (Exception e)
            {
                e.printStackTrace();
            }

            summaryCashTransactionByLedgerTypeList.add(cashTransaction);
        }

        summaryByTypeMoneySummary = MoneySummary.calc(summaryCashTransactionByLedgerTypeList);
    }

    public void onTabChange(TabChangeEvent event)
    {
        try
        {
            TabView tabView = (TabView) event.getComponent();
            selectedTabIndex = tabView.getChildren().indexOf(event.getTab());
            String tabId = event.getTab().getId();
//            System.out.println("tab : " + tabId + "  activeTabIndex : " + selectedTabIndex);

        } catch (Exception e)
        {
        }
    }

    public List<GeneralLedger> getCashTransactionsList()
    {
        return cashTransactionsList;
    }

    public LedgerQP getCashTransactionQP()
    {
        return cashTransactionQP;
    }

    public void setCashTransactionQP(LedgerQP cashTransactionQP)
    {
        this.cashTransactionQP = cashTransactionQP;
    }

    public LocalDateRange getDateRange()
    {
        return dateRange;
    }

    public void setDateRange(LocalDateRange dateRange)
    {
        this.dateRange = dateRange;
    }

    public List<GeneralLedger> getSummaryCashTransactionByLedgerList()
    {
        return summaryCashTransactionByLedgerList;
    }

    public int getSelectedTabIndex()
    {
        return selectedTabIndex;
    }

    public void setSelectedTabIndex(int selectedTabIndex)
    {
        this.selectedTabIndex = selectedTabIndex;
    }

    public MoneySummary getSummaryMoneySummary()
    {
        return summaryMoneySummary;
    }

    public MoneySummary getDetailMoneySummary()
    {
        return detailMoneySummary;
    }

    public List<GeneralLedger> getSummaryCashTransactionByLedgerTypeList()
    {
        return summaryCashTransactionByLedgerTypeList;
    }

    public MoneySummary getSummaryByTypeMoneySummary()
    {
        return summaryByTypeMoneySummary;
    }

}
