/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.jsf;

import com.statelyhub.elections.entities.ConstituencyElection;
import com.statelyhub.elections.entities.Election;
import com.statelyhub.elections.entities.UserAccount;
import com.statelyhub.old.entities.Institution;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;

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
    private Election electionUR;
    private ConstituencyElection constituencyElectionUR;
    
        
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

    public Election getElectionUR() {
        return electionUR;
    }

    public void setElectionUR(Election electionUR) {
        this.electionUR = electionUR;
    }

    public ConstituencyElection getConstituencyElectionUR() {
        return constituencyElectionUR;
    }

    public void setConstituencyElectionUR(ConstituencyElection constituencyElectionUR) {
        this.constituencyElectionUR = constituencyElectionUR;
    }
    
    
    
}
