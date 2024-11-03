/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.gafpv.jsf;

import com.statelyhub.gafpv.service.CrudService;
import com.stately.modules.web.jsf.Msg;
import com.statelyhub.gafpv.entities.Supplier;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import jakarta.inject.Inject;

/**
 *
 * @author Edwin
 */
@Named(value = "supplierController")
@SessionScoped
public class SupplierController implements Serializable
{
    @Inject private CrudService crudService;
    private List<Supplier> bankAccountsList;
    
    private Supplier bankAccount = new Supplier();
    
    public SupplierController()
    {
    }

    
    public void editBankAccount(Supplier bankAccount)
    {
        this.bankAccount = bankAccount;
    }
    
    public void clear()
    {
        bankAccount = new Supplier();
    }
    
    public void saveCounterparty()
    {
        if(crudService.save(bankAccount) != null)
        {
            Msg.msg(true);
            bankAccount = new  Supplier();
        }
        else
        {
            Msg.msg(false);
        }
    }
    
    public List<Supplier> getBankAccountsList()
    {
        bankAccountsList = crudService.findAll(Supplier.class);
        return bankAccountsList;
    }

    public Supplier getBankAccount()
    {
        return bankAccount;
    }

    public void setBankAccount(Supplier bankAccount)
    {
        this.bankAccount = bankAccount;
    }

    
    
    
}
