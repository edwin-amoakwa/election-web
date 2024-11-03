/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.gafpv.service;


import com.stately.common.data.ProcResponse;
import com.stately.common.model.LocalDateRange;
import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.gafpv.entities.CashTransfer;
import com.statelyhub.gafpv.entities.GeneralLedger;
import com.statelyhub.gafpv.entities.Institution;
import com.statelyhub.gafpv.entities.LedgerAccount;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author edwin
 */
@Stateless
public class CashTransferService {

    @Inject
    private CrudService crudService;

    public ProcResponse deleteCashTransfer(CashTransfer cashTransfer)
    {
        ProcResponse response = new ProcResponse();
        try 
        {
            if(cashTransfer == null)
            {
                return response.error("Error Deleting Transfer");
            }
            
            int del = QryBuilder.get(crudService.getEm(),GeneralLedger.class)
                    .addObjectParam(GeneralLedger._batch, cashTransfer.getId())
                    .delete();
            
            boolean delete = crudService.delete(cashTransfer);
            if(!delete)
            {
                return response.error("Unable to Delete Transfer Record");
            }
              
            response.setSuccess(true);
            response.setData(cashTransfer);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("Error Performing Operation!");
        }
        
        return response;
    }
       
       
       
    public List<CashTransfer> loadCashTransferList(
            Institution company,LocalDateRange dateRange,
            LedgerAccount fromAccount,LedgerAccount toAccount)
    {
        try 
        {
            QryBuilder builder = new QryBuilder(crudService.getEm(),CashTransfer.class)
                    .addObjectParam(CashTransfer._unit, company)
                    .addDateRange(dateRange,CashTransfer._valueDate);
            if(fromAccount != null)
            {
                builder.addObjectParam(CashTransfer._fromAccount, fromAccount);
            }
            if(toAccount != null)
            {
                builder.addObjectParam(CashTransfer._toAccount, toAccount);
            }
            return builder.printQryInfo().buildQry().getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }

    public ProcResponse saveCashTransfer(CashTransfer cashTransfer)
    {
        ProcResponse response = new ProcResponse();
        try 
        {
            if(cashTransfer.getFromAccount() == cashTransfer.getToAccount())
            {
                return response.error("Cannot Transfer Between Same Account");
            }
//            if(cashTransfer.isAuthorised())
//            {
//                return response.error("Cannot Delete An Authorised Transaction");
//            }

            cashTransfer = crudService.save(cashTransfer);
            if(cashTransfer == null)
            {
                return response.error("Error Saving Transfer");
            }
            
            int del = QryBuilder.get(crudService.getEm(),GeneralLedger.class)
                    .addObjectParam(GeneralLedger._batch, cashTransfer.getId())
                    .delete();
            System.out.println("related cash transactions deleted = "+del);
            
            if(crudService.save(cashTransfer.fromLedger()) == null)
            {
                return response.error("Error Performing Corresponding Transaction Operation - from!");
            }
            if(crudService.save(cashTransfer.toLedger()) == null)
            {
                return response.error("Error Performing Corresponding Transaction Operation - to!");
            }
            
            response.setSuccess(true);
            response.setData(cashTransfer);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("Error Performing Operation!");
        }
        
        return response;
    }
}
