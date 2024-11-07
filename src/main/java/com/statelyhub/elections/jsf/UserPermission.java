/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.jsf;

import com.statelyhub.old.constants.UserAccessLevel;
import com.statelyhub.old.constants.UserAccountCategory;
import com.statelyhub.elections.entities.UserAccount;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Edwin
 */
@Named(value = "userPermission")
@SessionScoped
public class UserPermission implements Serializable
{
    
    private UserAccount accountUR;
    
    private List<UserAccountCategory> categorys = new LinkedList<>();
    
    public void init(List<UserAccessLevel> accessLevels)
    {
        for (UserAccessLevel accessLevel : accessLevels) {
            categorys.add(accessLevel.getUserGroup());
        }
    }
    
        
//    public boolean isAuthoriser()
//    {
//        return categorys.contains(UserAccountCategory.AUTHORISER);
//    }
    
        
    public boolean isAdmin()
    {
        return categorys.contains(UserAccountCategory.ADMIN);
    }
    
     
    public boolean isInputter()
    {
        return categorys.contains(UserAccountCategory.INPUTER);
    }
    
    
    public boolean isSuperAdmin()
    {
        return categorys.contains(UserAccountCategory.SUPER_ADMIN);
    }
    
    
    public boolean isViewer()
    {
        return categorys.contains(UserAccountCategory.VIEWER);
    }
    
   
    
    
}
