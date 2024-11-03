/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.gafpv.entities;

import com.statelyhub.gafpv.constants.UserAccountCategory;
import com.stately.modules.jpa2.UniqueEntityModel2;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "user_accounts")
public class UserAccount extends UniqueEntityModel2
{    
    
    public static final String SUPER_ID = "epv.super";
    public static final String SUPER_USER_NAME = "super@epv.net";
    public static final String SUPER_PASSWORD = "epv.super";
    
    public static final String _institution = "institution";
    @JoinColumn(name = "institution")
    @ManyToOne
    private Institution institution;
    
    public static final String _emailAddress = "emailAddress";
    @Column(name = "email_address")
    private String emailAddress;
    
    public static final String _userPassword = "userPassword";
    @Column(name = "user_password")
    private String userPassword;
    
    public static final String _accountName = "accountName";
    @Column(name = "account_name")
    private String accountName;
    
    @Column(name = "account_category")
    @Enumerated(EnumType.STRING)
    private UserAccountCategory accountCategory;

    public boolean isSuperUser()
    {
        return accountCategory == UserAccountCategory.SUPER_ADMIN;
    }

    public boolean isViewerUser()
    {
        return accountCategory == UserAccountCategory.VIEWER;
    }

    public boolean isAdminUser()
    {
        return accountCategory == UserAccountCategory.ADMIN;
    }
    
    public boolean isAuthoriserUser()
    {
        return accountCategory == UserAccountCategory.AUTHORISER;
    }
    
    public boolean isInputterUser()
    {
        return accountCategory == UserAccountCategory.INPUTER;
    }
        
    public String getEmailAddress()
    {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public String getUserPassword()
    {
        return userPassword;
    }

    public void setUserPassword(String userPassword)
    {
        this.userPassword = userPassword;
    }

    public String getAccountName()
    {
        return accountName;
    }

    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }

    public UserAccountCategory getAccountCategory()
    {
        return accountCategory;
    }

    public void setAccountCategory(UserAccountCategory accountCategory)
    {
        this.accountCategory = accountCategory;
    }

    public Institution getInstitution()
    {
        return institution;
    }

    public void setInstitution(Institution institution)
    {
        this.institution = institution;
    }

    @Override
    public String toString()
    {
        return accountName;
    }
    
    
    
    
}
