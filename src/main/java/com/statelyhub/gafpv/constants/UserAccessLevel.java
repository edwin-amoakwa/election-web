/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.statelyhub.gafpv.constants;

import com.stately.modules.jpa2.UniqueEntityModel3;
import com.statelyhub.gafpv.entities.UserAccount;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 *
 * @author Edwin Kwame Amoakwa
 * @email edwin.amoakwa@gmail.com
 * @contact 0277115150
 */
@Entity
@Table(name = "user_access_levels")
public class UserAccessLevel extends UniqueEntityModel3
{    
    public static final String _staff = "userAccount";
    @JoinColumn(name="user_account")
    @ManyToOne
    private UserAccount userAccount;
    
    public static final String _userGroup = "userGroup";
    @Column(name = "user_group")
    @Enumerated(EnumType.STRING)
    private UserAccountCategory userGroup;
    
    public static final String _staffRole = "userRole";
    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private Role userRole;
    
    
    @Override
    public String toString()
    {
        return userAccount + " " + userGroup + " " + userRole;
    }

    public UserAccount getUserAccount()
    {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount)
    {
        this.userAccount = userAccount;
    }

    public UserAccountCategory getUserGroup()
    {
        return userGroup;
    }

    public void setUserGroup(UserAccountCategory userGroup)
    {
        this.userGroup = userGroup;
    }

    public Role getUserRole()
    {
        return userRole;
    }

    public void setUserRole(Role userRole)
    {
        this.userRole = userRole;
    }
    
    
}
