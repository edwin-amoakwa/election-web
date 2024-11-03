/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.gafpv.jsf;

import com.stately.modules.web.jsf.JsfUtil;
import com.statelyhub.gafpv.service.CrudService;
import com.stately.modules.web.jsf.Msg;
import com.statelyhub.gafpv.entities.Institution;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import jakarta.inject.Inject;

/**
 *
 * @author Edwin
 */
@Named(value = "institutionController")
@SessionScoped
public class InstitutionController implements Serializable
{
    @Inject private CrudService crudService;
    private List<Institution> bankAccountsList;
    
    private Institution bankAccount = new Institution();
    
    public InstitutionController()
    {
    }

    
    public void editBankAccount(Institution bankAccount)
    {
        this.bankAccount = bankAccount;
    }
    
    public void clear()
    {
        bankAccount = new Institution();
        JsfUtil.resetViewRoot();
    }
    
    public void saveCounterparty()
    {
        if(crudService.save(bankAccount) != null)
        {
            Msg.msg(true);
            bankAccount = new Institution();
        }
        else
        {
            Msg.msg(false);
        }
    }
    
    public List<Institution> getBankAccountsList()
    {
        bankAccountsList = crudService.findAll(Institution.class);
        return bankAccountsList;
    }

    public Institution getBankAccount()
    {
        return bankAccount;
    }

    public void setBankAccount(Institution bankAccount)
    {
        this.bankAccount = bankAccount;
    }

    
    
    
}
