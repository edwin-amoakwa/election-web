/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.old.service;

import com.statelyhub.elections.services.CrudService;
import com.statelyhub.elections.entities.UserAccount;
import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.elections.constants.UserAccessLevel;
import com.statelyhub.old.entities.Institution;
import java.util.LinkedList;
import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.Collections;

/**
 *
 * @author Edwin
 */
@Stateless
public class AdminService
{

    @Inject
    private CrudService crudService;

    public UserAccount findUser(String userName, String userPassword)
    {

        try
        {
            QryBuilder qryBuilder = new QryBuilder(crudService.getEm(), UserAccount.class);
            qryBuilder.addObjectParam(UserAccount._userPassword, userPassword);

            qryBuilder.addObjectParam(UserAccount._emailAddress, userName);
            
            return qryBuilder.getSingleResult(UserAccount.class);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;

    }
    
    public List<UserAccount> getUserAccountList(Institution institution)
     {
         try
         {
             QryBuilder qryBuilder = new QryBuilder(crudService.getEm(), UserAccount.class);
             
             if(institution != null)
             {
                 qryBuilder.addObjectParam(UserAccount._institution, institution);
             }
             
             
             return qryBuilder.buildQry().getResultList();
             
         } catch (Exception e)
         {
         }
         
         return new LinkedList<>();
     }
    
    
    
    public List<UserAccessLevel> staffAccessLevel(UserAccount staff)
    {
        if (staff == null)
        {
            return Collections.EMPTY_LIST;
        }

        QryBuilder builder = new QryBuilder(crudService.getEm(), UserAccessLevel.class);
        builder.addObjectParam(UserAccessLevel._staff, staff);

        return builder.buildQry().getResultList();
    }

}
