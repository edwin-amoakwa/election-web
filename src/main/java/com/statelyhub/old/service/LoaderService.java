/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.old.service;

import com.statelyhub.elections.services.CrudService;
import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.elections.entities.UserAccount;
import com.statelyhub.old.entities.Institution;
import java.util.LinkedList;
import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

/**
 *
 * @author Edwin
 */
@Stateless
public class LoaderService
{

     @Inject private CrudService crudService;

    
   
     public List<Institution> getInstitutionsList()
     {
         try
         {
             QryBuilder qryBuilder = new QryBuilder(crudService.getEm(), Institution.class);
                         
             return qryBuilder.buildQry().getResultList();
             
         } catch (Exception e)
         {
         }
         
         return new LinkedList<>();
     }

    
     public List<UserAccount> AdminLogin(Institution institution)
     {
         try
         {
             QryBuilder qryBuilder = new QryBuilder(crudService.getEm(), UserAccount.class);
                         qryBuilder.addObjectParam(UserAccount._institution, institution);
                         qryBuilder.orderByAsc(UserAccount._accountName);
                         
             return qryBuilder.buildQry().getResultList();
             
         } catch (Exception e)
         {
         }
         
         return new LinkedList<>();
     }
   
    
}
