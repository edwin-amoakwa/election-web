/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.services;

import com.stately.common.security.SecurityHash;
import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.elections.entities.UserAccount;
import com.statelyhub.elections.constants.UserAccountCategory;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;

/**
 *
 * @author Edwin
 */
@Singleton
@Startup
public class AppInitStarter
{

    @Inject
    private CrudService crudService;

    @PostConstruct
    public void initDefaultUser()
    {
        UserAccount userAccount = null;

        try
        {

            userAccount = QryBuilder.get(crudService.getEm(), UserAccount.class)
                    .addObjectParam(UserAccount._id, UserAccount.SUPER_ID)
                    .getSingleResult(UserAccount.class);

            if (userAccount != null)
            {
                return;
            }

            String passord = SecurityHash.getInstance().shaHash(userAccount.SUPER_PASSWORD);

            userAccount = new UserAccount();
            userAccount.setId(UserAccount.SUPER_ID);
            userAccount.setEmailAddress(UserAccount.SUPER_USER_NAME);
            userAccount.setAccountName("Super User");
            userAccount.setUserPassword(passord);
            userAccount.setAccountCategory(UserAccountCategory.ADMIN);

            crudService.save(userAccount);

            System.out.println("Default Super User Created .... ");
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}
