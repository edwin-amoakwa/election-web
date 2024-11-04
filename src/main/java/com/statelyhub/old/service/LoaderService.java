/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.old.service;

import com.statelyhub.elections.services.CrudService;
import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.elections.entities.UserAccount;
import com.statelyhub.old.entities.FundingSource;
import com.statelyhub.old.entities.Institution;
import com.statelyhub.old.entities.Department;
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

    
     public List<FundingSource> getFundingSourceList(Institution institution)
     {
         try
         {
             QryBuilder qryBuilder = new QryBuilder(crudService.getEm(), FundingSource.class);
              qryBuilder.addObjectParam(FundingSource._institution, institution);
                         qryBuilder.orderByAsc(FundingSource._sourceName);
             return qryBuilder.buildQry().getResultList();
             
         } catch (Exception e)
         {
         }
         
         return new LinkedList<>();
     }
    
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
    
     public List<Department> Department(Institution institution)
     {
         try
         {
             QryBuilder qryBuilder = new QryBuilder(crudService.getEm(), Department.class);
             qryBuilder.addObjectParam(Department._unit, institution);
             qryBuilder.orderByAsc(Department._departmentName);
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
