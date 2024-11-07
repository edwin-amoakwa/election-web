/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.entities;

import com.statelyhub.old.constants.UserAccountCategory;
import com.stately.modules.jpa2.UniqueEntityModel2;
import com.statelyhub.old.constants.UserDomain;
import com.statelyhub.old.entities.Institution;
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
    
    public static final String SUPER_ID = "election.superz";
    public static final String SUPER_USER_NAME = "election@net";
    public static final String SUPER_PASSWORD = "election@superz";
    
    public static final String _institution = "institution";
    @JoinColumn(name = "institution")
    @ManyToOne
    private Institution institution;
    
    public static final String _mobileNo = "mobileNo";
    @Column(name = "mobile_no")
    private String mobileNo;
    
    public static final String _emailAddress = "emailAddress";
    @Column(name = "email_address")
    private String emailAddress;
    
    public static final String _userPassword = "userPassword";
    @Column(name = "user_password")
    private String userPassword;
    
    public static final String _accountName = "accountName";
    @Column(name = "account_name")
    private String accountName;
    
    @Column(name = "user_domain")
    @Enumerated(EnumType.STRING)
    private UserDomain userDomain;
    
    @Column(name = "account_category")
    @Enumerated(EnumType.STRING)
    private UserAccountCategory accountCategory;
    
    
      
    public static final String _region = "region";
    @JoinColumn(name = "region")
    @ManyToOne
    private Region region;
    
    
      
    public static final String _constituency = "constituency";
    @JoinColumn(name = "constituency")
    @ManyToOne
    private Constituency constituency;
    

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
    
//    public boolean isAuthoriserUser()
//    {
//        return accountCategory == UserAccountCategory.AUTHORISER;
//    }
    
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
    
    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public UserDomain getUserDomain() {
        return userDomain;
    }

    public void setUserDomain(UserDomain userDomain) {
        this.userDomain = userDomain;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Constituency getConstituency() {
        return constituency;
    }

    public void setConstituency(Constituency constituency) {
        this.constituency = constituency;
    }
    
    

    @Override
    public String toString()
    {
        return accountName;
    }
    
    
    
    
}
