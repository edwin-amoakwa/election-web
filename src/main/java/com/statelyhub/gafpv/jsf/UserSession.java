/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.gafpv.jsf;

import com.statelyhub.gafpv.constants.UserAccountCategory;
import com.statelyhub.gafpv.entities.UserAccount;
import com.statelyhub.gafpv.entities.Institution;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Edwin
 */
@Named(value = "userSession")
@SessionScoped
public class UserSession implements Serializable
{

    boolean userLogin = false;
    
    private UserAccount accountUR;
    
        
    public UserSession()
    {
        accountUR = new UserAccount();
        accountUR.setAccountName("Administrator");
    }
    
    public void login(UserAccount adminLogin)
    {
        accountUR = adminLogin;
        userLogin = true;
    }

    public Institution getInstitution()
    {
        if(accountUR == null)
        {
            return null;
        }
        return accountUR.getInstitution();
    }

    public UserAccount getAccountUR()
    {
        return accountUR;
    }

    public void setAccountUR(UserAccount accountUR)
    {
        this.accountUR = accountUR;
    }
    

    public boolean isUserLogin()
    {
        return userLogin;
    }

    public void setUserLogin(boolean userLogin)
    {
        this.userLogin = userLogin;
    }
    
    
    
}
