/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.jsf;

import com.statelyhub.elections.constants.PartyType;
import com.statelyhub.elections.constants.ResultSource;
import com.statelyhub.elections.constants.ResultStatus;
import com.statelyhub.old.constants.AccountStatus;
import com.statelyhub.elections.constants.UserAccountCategory;
import com.statelyhub.elections.services.CrudService;
import com.statelyhub.elections.constants.UserDomain;
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
    
    public List<UserAccountCategory> getUserAccountCategorysList()
    {
        return Arrays.asList(UserAccountCategory.values());
    }
    
    
    
    public List<AccountStatus> getAccountStatusList()
    {
        return Arrays.asList(AccountStatus.values());
    }
    
    
       
    public List<ResultStatus> getResultStatusList()
    {
        return Arrays.asList(ResultStatus.values());
    }
    
    public List<ResultSource> getResultSourceList()
    {
        return Arrays.asList(ResultSource.values());
    }
    
        public List<PartyType> getPartyTypeList()
    {
        return Arrays.asList(PartyType.values());
    }
        
             public List<UserDomain> getUserDomainsList()
    {
        return Arrays.asList(UserDomain.values());
    }
             
             
//              public List<UserAccountCategory> getUserAccountCategorysList()
//    {
//        try
//        {
//            if(userSession.getAccountUR().isSuperUser())
//            {
//                return Arrays.asList(UserAccountCategory.values());
//            }
//            else
//            {
//                return Arrays.asList(UserAccountCategory.ADMIN,UserAccountCategory.INPUTER,UserAccountCategory.AUTHORISER, UserAccountCategory.VIEWER);
//            }
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        
//        return Collections.EMPTY_LIST;
//    }
}
