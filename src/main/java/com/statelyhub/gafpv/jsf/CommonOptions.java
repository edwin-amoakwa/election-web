/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.gafpv.jsf;

import com.statelyhub.gafpv.constants.AccountStatus;
import com.statelyhub.gafpv.constants.AccountType;
import com.statelyhub.gafpv.constants.FormOfPayment;
import com.statelyhub.gafpv.constants.UserAccountCategory;
import com.statelyhub.gafpv.service.CrudService;
import com.stately.common.constants.DebitCredit;
import jakarta.enterprise.context.RequestScoped;
import java.util.Arrays;
import java.util.List;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author Edwin
 */
@Named(value = "commonOptions")
@RequestScoped
public class CommonOptions
{
    @Inject private CrudService crudService;
    
    
       public List<AccountType> getAccountTypesList()
    {
        return Arrays.asList(AccountType.values());
    }
    
    public CommonOptions()
    {
    }
    
    
    public List<UserAccountCategory> getUserAccountCategorysList()
    {
        return Arrays.asList(UserAccountCategory.values());
    }
    
    
    
    public List<FormOfPayment> getFormOfPaymentsList()
    {
        return Arrays.asList(FormOfPayment.values());
    }
    
    
    
    

    
    public List<DebitCredit> getDebitCreditList()
    {
        return Arrays.asList(DebitCredit.values());
    }
    
    public List<AccountStatus> getAccountStatusList()
    {
        return Arrays.asList(AccountStatus.values());
    }
    
    
    
}
