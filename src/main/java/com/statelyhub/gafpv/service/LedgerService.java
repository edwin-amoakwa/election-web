/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.gafpv.service;

import com.stately.common.formating.ObjectValue;
import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.gafpv.constants.AccountType;
import com.statelyhub.gafpv.entities.GeneralLedger;
import com.statelyhub.gafpv.entities.Bill;
import com.statelyhub.gafpv.entities.Institution;
import com.statelyhub.gafpv.entities.LedgerAccount;
import com.statelyhub.gafpv.entities.PaymentVocher;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author edwin
 */
@Stateless
public class LedgerService {
    
    @Inject private CrudService crudService;
    
    public LedgerAccount getPayable(Institution institution)
    {
        LedgerAccount account = QryBuilder.get(crudService.getEm(), LedgerAccount.class)
                .addObjectParam(LedgerAccount._unit, institution)
                .addObjectParam(LedgerAccount._accountType, AccountType.PAYABLE)
                .getSingleResult(LedgerAccount.class);
        
        if(account == null)
        {
            account = new LedgerAccount();
            account.setAccountType(AccountType.PAYABLE);
            account.setUnit(institution);
            account.setAccountCode(AccountType.PAYABLE.name());
            account.setAccountName(AccountType.PAYABLE.getLabel());
            crudService.save(account);
        }
        
        
        return account;
    }
    
    
     public double accountBalance(LedgerAccount account, LocalDate valueDate) {
        try {
            if (account == null) {
                return 0;
            }

            QryBuilder builder = new QryBuilder(crudService.getEm(), GeneralLedger.class);
            builder.addReturnField(GeneralLedger.SUM);
            builder.addObjectParam(GeneralLedger._account, account);
            builder.addDateParam(GeneralLedger._valueDate, valueDate, QryBuilder.ComparismCriteria.LESS_THAN_OR_EQUAL);

            return ObjectValue.get_doubleValue(builder.getSingleResult(Double.class));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
    
    
    public List<LedgerAccount> search(Institution institution, List<AccountType> typesList)
    {
        return QryBuilder.get(crudService.getEm(), LedgerAccount.class)
                .addInParam(LedgerAccount._accountType, typesList)
                .addObjectParam(LedgerAccount._unit, institution)
                .buildQry().getResultList();
    }
    
    
    
    
    
    public void post(Bill bill)
    {
        crudService.deleteAll(GeneralLedger._batch, bill.getId(), GeneralLedger.class);
        
        GeneralLedger payable = bill.getPayble();
        payable.setAccount(getPayable(bill.getUnit()));
        
        GeneralLedger expense = bill.getExpnse();
        
        crudService.save(payable);
        crudService.save(expense);
    }
    
       public void post(PaymentVocher pv)
    {
        crudService.deleteAll(GeneralLedger._batch, pv.getId(), GeneralLedger.class);
        
        GeneralLedger payable = pv.getPayable();
        payable.setAccount(getPayable(pv.getUnit()));
        
        GeneralLedger expense = pv.getExpense();
        
        crudService.save(payable);
        crudService.save(expense);
    }
    
        
    public void unpost(String batchId)
    {
        crudService.deleteAll(GeneralLedger._batch, batchId, GeneralLedger.class);
        
//        GeneralLedger payable = bill.toLedger();
//        payable.setAccount(getPayable(bill.getUnit()));
//        
//        GeneralLedger expense = bill.fromLedger();
//        
//        crudService.save(payable);
//        crudService.save(expense);
        
    }
}
