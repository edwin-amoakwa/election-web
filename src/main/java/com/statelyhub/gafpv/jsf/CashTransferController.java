/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.gafpv.jsf;

import com.stately.common.collection.CollectionUtils;
import com.stately.common.data.ErrorModel;
import com.stately.common.data.ProcResponse;
import com.stately.common.model.LocalDateRange;
import com.stately.modules.web.jsf.JsfMsg;
import com.stately.modules.web.jsf.JsfUtil;
import com.statelyhub.gafpv.entities.CashTransfer;
import com.statelyhub.gafpv.entities.LedgerAccount;
import com.statelyhub.gafpv.service.CashTransferService;
import com.statelyhub.gafpv.service.CrudService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Stately
 */
@Named(value = "cashTransferController")
@SessionScoped
public class CashTransferController implements Serializable
{ 
    @Inject private CrudService crudService;
    @Inject private CashTransferService transactionService;
    @Inject private UserSession userSession;
    
    private CashTransfer cashTransfer = new CashTransfer();
    private List<CashTransfer> cashTransfersList = new LinkedList<>();
//    private LocalDate selectedDate;
    private LedgerAccount selectedFromAccount;
    private LedgerAccount selectedToAccount;
    private LocalDateRange dateRange = new LocalDateRange();
    
    @PostConstruct
    public void initEntry()
    {
        clearFilters();
        initCashTransfer();
        loadCashTransfers();
    }
    
    public void clearFilters()
    {
        dateRange = new LocalDateRange();
        selectedFromAccount = null;
        selectedToAccount = null;
    }
     
    public void loadCashTransfers()
    {
        try{
            cashTransfersList = 
                    transactionService
                            .loadCashTransferList(userSession.getAccountUR().getInstitution(), 
                                    dateRange, selectedFromAccount, 
                                    selectedToAccount);
        } catch (Exception e) {
            e.printStackTrace();
            cashTransfersList = Collections.emptyList();
        }
    }
    
    public void initCashTransfer()
    {
        cashTransfer = new CashTransfer();
        cashTransfer.setUnit(userSession.getInstitution());
        JsfUtil.resetViewRoot();
//        cashTransfer.setCompany(userSession.getCompanyUR());
//        cashTransfer.setBranch(userSession.getBranchUR());
//        cashTransfer.setInputter(userSession.getUser());
    }
    
//    public void closePage()
//    {
//        this.selectedDate = null;
//    }
     
    public void saveCashTransfer()
    {  
//        System.out.println("userSession.getCompanyUR() == "+userSession.getCompanyUR()); 
//        cashTransfer.setCompany(userSession.getCompanyUR());
        if(cashTransfer.getUnit()== null)
        {
            JsfMsg.error("Specify Unit");
            return;
        }
        
        if(cashTransfer.getValueDate() == null)
        {
            JsfMsg.error("Specify Transfer Date");
            return;
        }
        if(cashTransfer.getFromAccount() == null)
        {
            JsfMsg.error("Specify Account to Transfer From");
            return;
        }
        if(cashTransfer.getToAccount() == null)
        {
            JsfMsg.error("Specify Account to Transfer To");
            return;
        }
        if(cashTransfer.getFromAccount() == cashTransfer.getToAccount())
        {
            JsfMsg.error("Cannot Transfer Between Same Account");
            return;
        }  
        if(cashTransfer.getAmount() <= 0)
        {
            JsfMsg.error("Specify Valid Amount to Transfer - Greater than Zero(0)");
            return;
        }
        
        try 
        {
//            System.out.println("cashTransfer.getCompanyUR() == "+cashTransfer.getCompany()); 
            ProcResponse response = transactionService.saveCashTransfer(cashTransfer);
            if(!response.isSuccess())
            {
                try {
                    JsfMsg.print(response);
                } catch (Exception e) {
                }
                JsfMsg.error(response.getMessage());
                return;
            }

            cashTransfer = (CashTransfer)response.getData();
            CollectionUtils.checkAdd(cashTransfersList, cashTransfer);
            JsfMsg.successSave();
            this.initCashTransfer();

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public void selectCashTransfer(CashTransfer transfer)
    {
        this.cashTransfer = transfer;
    }
            
    public void deleteCashTransfer(CashTransfer transfer)
    {
        try 
        {
            ProcResponse response = transactionService.deleteCashTransfer(transfer);
            if(!response.isSuccess())
            {
                try {
//                    JsfMsg.error(response.getErrors().);
                    JsfMsg.error(((ErrorModel)response.getErrors().get(0)).getMessage());
                } catch (Exception e) {
                }
                JsfMsg.error(response.getMessage());
                return; 
            }
   
            transfer = (CashTransfer)response.getData();
            boolean rem = cashTransfersList.remove(transfer);
            JsfMsg.successDelete();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CashTransfer getCashTransfer() {
        return cashTransfer;
    }

    public void setCashTransfer(CashTransfer cashTransfer) {
        this.cashTransfer = cashTransfer;
    }

    public List<CashTransfer> getCashTransfersList() {
        return cashTransfersList;
    }

    public void setCashTransfersList(List<CashTransfer> cashTransfersList) {
        this.cashTransfersList = cashTransfersList;
    }

//    public LocalDate getSelectedDate() {
//        return selectedDate;
//    }
//
//    public void setSelectedDate(LocalDate selectedDate) {
//        this.selectedDate = selectedDate;
//    }

    public LedgerAccount getSelectedFromAccount() {
        return selectedFromAccount;
    }

    public void setSelectedFromAccount(LedgerAccount selectedFromAccount) {
        this.selectedFromAccount = selectedFromAccount;
    }

    public LedgerAccount getSelectedToAccount() {
        return selectedToAccount;
    }

    public void setSelectedToAccount(LedgerAccount selectedToAccount) {
        this.selectedToAccount = selectedToAccount;
    }

    public LocalDateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(LocalDateRange dateRange) {
        this.dateRange = dateRange;
    }
     
}
