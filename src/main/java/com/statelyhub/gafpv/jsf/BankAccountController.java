/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.gafpv.jsf;

import com.stately.modules.web.jsf.JsfUtil;
import com.statelyhub.gafpv.service.CrudService;
import com.stately.modules.web.jsf.Msg;
import com.statelyhub.gafpv.entities.BankAccount;
import com.statelyhub.gafpv.service.LoaderService;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;

import jakarta.inject.Inject;

/**
 *
 * @author Edwin
 */
@Named(value = "bankAccountController")
@SessionScoped
public class BankAccountController implements Serializable
{
    @Inject private UserSession userSession;
    @Inject private CrudService crudService;
    @Inject private LoaderService loaderService;
        
    private List<BankAccount> bankAccountsList;
    
    private BankAccount bankAccount = new BankAccount();
    
    public BankAccountController()
    {
    }

    
    public void editBankAccount(BankAccount bankAccount)
    {
        this.bankAccount = bankAccount;
    }
    
    public void deleteBankAccount(BankAccount bankAccount)
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
        bankAccount = new BankAccount();
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
    
    public List<BankAccount> getBankAccountsList()
    {
        bankAccountsList = loaderService.getBankAccounts(userSession.getInstitution());
        return bankAccountsList;
    }

    public BankAccount getBankAccount()
    {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount)
    {
        this.bankAccount = bankAccount;
    }

    
    
    
}
