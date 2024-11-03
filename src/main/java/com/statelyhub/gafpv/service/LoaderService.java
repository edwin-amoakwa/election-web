/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.gafpv.service;

import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.gafpv.entities.BankAccount;
import com.statelyhub.gafpv.entities.UserAccount;
import com.statelyhub.gafpv.entities.LedgerAccount;
import com.statelyhub.gafpv.entities.FundingSource;
import com.statelyhub.gafpv.entities.Institution;
import com.statelyhub.gafpv.entities.Supplier;
import com.statelyhub.gafpv.entities.Department;
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
    
     public List<Supplier> getSuppliersList()
     {
         try
         {
             QryBuilder qryBuilder = new QryBuilder(crudService.getEm(), Supplier.class);
                         
             return qryBuilder.buildQry().getResultList();
             
         } catch (Exception e)
         {
         }
         
         return new LinkedList<>();
     }
    
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
    
     public List<LedgerAccount> getLedgerAccount(Institution institution)
     {
         try
         {
             QryBuilder qryBuilder = new QryBuilder(crudService.getEm(), LedgerAccount.class);
             qryBuilder.addObjectParam(LedgerAccount._unit, institution);
             qryBuilder.orderByAsc(LedgerAccount._accountName);
             
             return qryBuilder.buildQry().getResultList();
             
         } catch (Exception e)
         {
         }
         
         return new LinkedList<>();
     }
    
     public List<BankAccount> getBankAccounts(Institution institution)
     {
         try
         {
             QryBuilder qryBuilder = new QryBuilder(crudService.getEm(), BankAccount.class);
             qryBuilder.addObjectParam(BankAccount._unit, institution);
             qryBuilder.orderByAsc(BankAccount._accountName);
             return qryBuilder.buildQry().getResultList();
             
         } catch (Exception e)
         {
         }
         
         return new LinkedList<>();
     }
    
}
