/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.gafpv.jsf;

import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.gafpv.constants.AccountType;
import com.statelyhub.gafpv.entities.BankAccount;
import com.statelyhub.gafpv.entities.LedgerAccount;
import com.statelyhub.gafpv.entities.Department;
import com.statelyhub.gafpv.service.CrudService;
import com.statelyhub.gafpv.entities.UserAccount;
import com.statelyhub.gafpv.entities.FundingSource;
import com.statelyhub.gafpv.entities.Institution;
import com.statelyhub.gafpv.entities.Supplier;
import com.statelyhub.gafpv.service.LoaderService;
import com.statelyhub.gafpv.constants.AmtInWords;
import com.statelyhub.gafpv.entities.BillStatus;
import com.statelyhub.gafpv.entities.ReceivedStatus;
import com.statelyhub.gafpv.constants.AuthorisationRequirement;
import com.statelyhub.gafpv.constants.UserAccountCategory;
import com.statelyhub.gafpv.constants.VoucherCurrency;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

/**
 *
 * @author Edwin
 */
@Named(value = "userCommonOptions")
@RequestScoped
public class UserCommonOptions implements Serializable
{
    @Inject private CrudService crudService;
    @Inject private UserSession userSession;
    @Inject private LoaderService loaderService;
    
    private List<Supplier> suppliersList;
    private List<FundingSource> fundingSourcesList;
    private List<LedgerAccount> accountsList;
    private List<LedgerAccount> expenseAccountsList;
    private List<BankAccount> bankAccountsList;
    private List<UserAccount> usersList;
    private List<Department> departmentsList;
    private List<Institution> institutionsList;
    
    
    private List<LedgerAccount> cashBankAccountsList;
    
    
    public UserCommonOptions()
    {
    }
    
    private List<LedgerAccount> get(List<AccountType> accountTypes)
    {
        return QryBuilder.get(crudService.getEm(), LedgerAccount.class)
                .addInParam(LedgerAccount._accountType, accountTypes)
                .buildQry().getResultList();
    }
    
    @PostConstruct
    public void init()
    {
        try
        {
            System.out.println("...... " + accountsList);
            
            suppliersList = loaderService.getSuppliersList();
            fundingSourcesList = loaderService.getFundingSourceList(userSession.getInstitution());
            accountsList = loaderService.getLedgerAccount(userSession.getInstitution());
            usersList = loaderService.AdminLogin(userSession.getInstitution());
            accountsList = loaderService.getLedgerAccount(userSession.getInstitution());
            departmentsList = loaderService.Department(userSession.getInstitution());
            bankAccountsList = loaderService.getBankAccounts(userSession.getInstitution());
            
            cashBankAccountsList = get(AccountType.bankAndCashEquivlent);
            
            expenseAccountsList = get(Arrays.asList(AccountType.EXPENSE, AccountType.OTHER_EXPENSE));
            
            
            
            
            institutionsList = loaderService.getInstitutionsList();
            
            
            System.out.println("--accountsList-- " + accountsList);
            System.out.println("-cashBankAccountsList--- " + cashBankAccountsList);
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    public List<UserAccountCategory> getUserAccountCategorysList()
    {
        try
        {
            if(userSession.getAccountUR().isSuperUser())
            {
                return Arrays.asList(UserAccountCategory.values());
            }
            else
            {
                return Arrays.asList(UserAccountCategory.ADMIN,UserAccountCategory.INPUTER,UserAccountCategory.AUTHORISER, UserAccountCategory.VIEWER);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return Collections.EMPTY_LIST;
    }
    
    public List<String> getCurrencysList()
    {
        return VoucherCurrency.list;
    }
    
    public List<AmtInWords> getAmtInWordsList()
    {
        return Arrays.asList(AmtInWords.values());
    }
    
    public List<AuthorisationRequirement> getAuthorisationRequirementList()
    {
        return Arrays.asList(AuthorisationRequirement.values());
    }
    
    public List<BillStatus> getPvStatusList()
    {
        return Arrays.asList(BillStatus.values());
    }
    
    public List<ReceivedStatus> getReceivedStatusList()
    {
        return Arrays.asList(ReceivedStatus.values());
    }

    public List<Supplier> getSuppliersList()
    {
        return suppliersList;
    }

    public List<FundingSource> getFundingSourcesList()
    {
        return fundingSourcesList;
    }

    public List<LedgerAccount> getAccountsList()
    {
        return accountsList;
    }

    public List<UserAccount> getUsersList()
    {
        return usersList;
    }

    public List<Department> getDepartmentsList()
    {
        return departmentsList;
    }

    public List<Institution> getInstitutionsList()
    {
        return institutionsList;
    }

    public List<BankAccount> getBankAccountsList()
    {
        return bankAccountsList;
    }

    public List<LedgerAccount> getCashBankAccountsList() {
        return cashBankAccountsList;
    }

    public List<LedgerAccount> getExpenseAccountsList() {
        return expenseAccountsList;
    }

    
    
    
    
    
    
    
    
}
