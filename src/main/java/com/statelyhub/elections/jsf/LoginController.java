/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.jsf;

import com.statelyhub.elections.jsf.Pages;
import com.statelyhub.elections.jsf.UserPermission;
import com.statelyhub.elections.jsf.UserSession;
import com.statelyhub.elections.entities.UserAccount;
import com.statelyhub.old.service.AdminService;
import com.stately.common.security.SecurityHash;
import com.stately.modules.jpa2.QryBuilder;
import com.stately.modules.web.jsf.Msg;
import com.statelyhub.elections.entities.Election;
import com.statelyhub.elections.services.CrudService;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
import jakarta.inject.Inject;
import java.util.List;
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
        @Inject private CrudService crudService;
    
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
                
                
                List<Election> elections = QryBuilder.get(crudService.getEm(), Election.class).buildQry().getResultList();
                if(elections.size() == 1)
                {
                    userSession.setElectionUR(elections.get(0));
                }
                
                
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
