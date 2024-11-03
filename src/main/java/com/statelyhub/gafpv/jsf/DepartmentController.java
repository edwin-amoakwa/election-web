/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.gafpv.jsf;

import com.stately.modules.web.jsf.JsfUtil;
import com.statelyhub.gafpv.service.CrudService;
import com.stately.modules.web.jsf.Msg;
import com.statelyhub.gafpv.service.LoaderService;
import com.statelyhub.gafpv.entities.Department;
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
@Named(value = "departmentController")
@SessionScoped
public class DepartmentController implements Serializable
{
    @Inject private UserSession userSession;
    @Inject private CrudService crudService;
    private List<Department> bankAccountsList;
    @Inject private LoaderService loaderService;
    
    private Department bankAccount = new Department();
    
    public DepartmentController()
    {
    }

    
    public void editBankAccount(Department bankAccount)
    {
        this.bankAccount = bankAccount;
    }
    
    @PostConstruct
    public void clear()
    {
        bankAccount = new Department();
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
    
    public List<Department> getBankAccountsList()
    {
        bankAccountsList = loaderService.Department(userSession.getInstitution());
        return bankAccountsList;
    }

    public Department getBankAccount()
    {
        return bankAccount;
    }

    public void setBankAccount(Department bankAccount)
    {
        this.bankAccount = bankAccount;
    }

    
    
    
}
