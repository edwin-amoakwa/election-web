/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.gafpv.jsf;

import com.statelyhub.gafpv.entities.UserAccount;
import com.statelyhub.gafpv.service.AdminService;
import com.stately.common.security.SecurityHash;
import com.stately.modules.web.jsf.Msg;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
import jakarta.inject.Inject;
import org.omnifaces.util.Faces;

/**
 *
 * @author Edwin
 */
@Named(value = "loginController")
@SessionScoped
public class LoginController implements Serializable
{
    
    @Inject private AdminService adminService;
    @Inject private UserSession userSession;
    @Inject private UserPermission userPermission;
    
    private String userPassword;
    private String username;
    
    public LoginController()
    {
    }
    
    public void doLogin()
    {
        try
        {
            
            String hashed = SecurityHash.getInstance().shaHash(userPassword);
            System.out.println(" 0---- " + hashed);
            UserAccount  adminLogin = adminService.findUser(username, hashed);
            
            if(adminLogin != null)
            {
                userPermission.init(adminService.staffAccessLevel(adminLogin));
                
                
                userSession.login(adminLogin);
                System.out.println("login sucefull --- ");
                Faces.redirect(Pages.adminLogin);
                
            }
            else
            {
                String msg = "Password / email is wrong";
                Msg.error(msg);
            }
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void logoutUser()
    {
        try
        {
            userSession.setUserLogin(false);
            Faces.invalidateSession();
        } catch (Exception e)
        {
        }
    }

    public String getUserPassword()
    {
        return userPassword;
    }

    public void setUserPassword(String userPassword)
    {
        this.userPassword = userPassword;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }
    
    
    
}
