/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.gafpv.jsf;

import com.stately.modules.web.jsf.JsfUtil;
import com.statelyhub.gafpv.entities.LedgerAccount;
import com.statelyhub.gafpv.service.CrudService;
import com.stately.modules.web.jsf.Msg;
import com.statelyhub.gafpv.service.LoaderService;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

/**
 *
 * @author Edwin
 */
@Named(value = "accountController")
@SessionScoped
public class LedgerAccountController implements Serializable
{
    @Inject private UserSession userSession;
    @Inject private CrudService crudService;
    @Inject private LoaderService loaderService;
        
    private List<LedgerAccount> bankAccountsList;
    
    private LedgerAccount bankAccount = new LedgerAccount();
    
    public LedgerAccountController()
    {
    }

    
    public void editBankAccount(LedgerAccount bankAccount)
    {
        this.bankAccount = bankAccount;
    }
    
    public void deleteBankAccount(LedgerAccount bankAccount)
    {
        try
        {
            crudService.delete(bankAccount);
        } catch (Exception e)
        {
        }
        
    }
    
    @PostConstruct
    public void clear()
    {
        bankAccount = new LedgerAccount();
        bankAccount.setUnit(userSession.getInstitution());
        JsfUtil.resetViewRoot();
    }
    
    public void saveCounterparty()
    {
        if(crudService.save(bankAccount) != null)
        {
            Msg.msg(true);
            clear();
        }
        else
        {
            Msg.msg(false);
        }
    }
    
    public List<LedgerAccount> getBankAccountsList()
    {
        bankAccountsList = loaderService.getLedgerAccount(userSession.getInstitution());
        return bankAccountsList;
    }

    public LedgerAccount getBankAccount()
    {
        return bankAccount;
    }

    public void setBankAccount(LedgerAccount bankAccount)
    {
        this.bankAccount = bankAccount;
    }

    
    
    
}
