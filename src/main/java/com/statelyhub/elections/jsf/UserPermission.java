/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.jsf;

import com.statelyhub.elections.constants.UserAccessLevel;
import com.statelyhub.elections.entities.UserAccount;
import com.statelyhub.elections.constants.UserGroup;
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
    
    private List<UserGroup> categorys = new LinkedList<>();
    
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
    
        
//    public boolean isAdmin()
//    {
//        return categorys.contains(UserGroup.ADMIN);
//    }
//    
//     
//    public boolean isInputter()
//    {
//        return categorys.contains(UserGroup.INPUTER);
//    }
    
    
    public boolean isSuperAdmin()
    {
        return categorys.contains(UserGroup.SYSTEM_ADMINSTRATORS);
    }
    
    
//    public boolean isViewer()
//    {
//        return categorys.contains(UserGroup.VIEWER);
//    }
//    
   
    
    
}
