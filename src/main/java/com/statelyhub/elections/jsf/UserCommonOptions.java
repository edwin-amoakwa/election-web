/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.jsf;

import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.elections.entities.Constituency;
import com.statelyhub.elections.entities.ConstituencyElection;
import com.statelyhub.elections.entities.Election;
import com.statelyhub.elections.entities.PoliticalParty;
import com.statelyhub.elections.entities.Region;
import com.statelyhub.elections.services.CrudService;
import com.statelyhub.elections.entities.UserAccount;
import com.statelyhub.old.service.LoaderService;
import com.statelyhub.old.constants.AmtInWords;
import com.statelyhub.old.entities.BillStatus;
import com.statelyhub.old.entities.ReceivedStatus;
import com.statelyhub.old.constants.AuthorisationRequirement;
import com.statelyhub.old.constants.UserAccountCategory;
import com.statelyhub.old.constants.VoucherCurrency;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

/**
 *
 * @author Edwin
 */
@Named(value = "userCommonOptions")
@RequestScoped
public class UserCommonOptions implements Serializable
{
    @Inject private CrudService crudService;
    @Inject private UserSession userSession;
    @Inject private LoaderService loaderService;
    
    
    private List<Election> electionsList;
    private List<Constituency> constituenciesList;
    
    private List<Region> regionsList;
    private List<PoliticalParty> partysList;
    
     private List<ConstituencyElection> constituencyElectionsList;
    
    private List<UserAccount> usersList;
    
    @PostConstruct
    public void init()
    {
        try
        {
          electionsList =  QryBuilder.get(crudService.getEm(), Election.class)
                .buildQry().getResultList();
          
            regionsList =  QryBuilder.get(crudService.getEm(), Region.class)
                    .orderByAsc(Region._regionName)
                .buildQry().getResultList();
            
            
               partysList =  QryBuilder.get(crudService.getEm(), PoliticalParty.class)
                    .orderByAsc(PoliticalParty._partyName)
                .buildQry().getResultList();
               
                    constituencyElectionsList =  QryBuilder.get(crudService.getEm(), ConstituencyElection.class)
                    .orderByAsc(ConstituencyElection._constituency_constituencyName)
                .buildQry().getResultList();
               
                    constituenciesList =  QryBuilder.get(crudService.getEm(), Constituency.class)
                    .orderByAsc(Constituency._constituencyName)
                .buildQry().getResultList();
          
            
            
//            System.out.println("...... " + accountsList);
//            
//            suppliersList = loaderService.getSuppliersList();
//            fundingSourcesList = loaderService.getFundingSourceList(userSession.getInstitution());
//            accountsList = loaderService.getLedgerAccount(userSession.getInstitution());
//            usersList = loaderService.AdminLogin(userSession.getInstitution());
//            accountsList = loaderService.getLedgerAccount(userSession.getInstitution());
//            departmentsList = loaderService.Department(userSession.getInstitution());
//            
//            cashBankAccountsList = get(AccountType.bankAndCashEquivlent);
//            
//            expenseAccountsList = get(Arrays.asList(AccountType.EXPENSE, AccountType.OTHER_EXPENSE));
//            
//            
//            
//            
//            institutionsList = loaderService.getInstitutionsList();
//            
//            
//            System.out.println("--accountsList-- " + accountsList);
//            System.out.println("-cashBankAccountsList--- " + cashBankAccountsList);
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    public List<UserAccountCategory> getUserAccountCategorysList()
    {
        try
        {
            if(userSession.getAccountUR().isSuperUser())
            {
                return Arrays.asList(UserAccountCategory.values());
            }
            else
            {
                return Arrays.asList(UserAccountCategory.ADMIN,UserAccountCategory.INPUTER,UserAccountCategory.AUTHORISER, UserAccountCategory.VIEWER);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return Collections.EMPTY_LIST;
    }
    
    public List<String> getCurrencysList()
    {
        return VoucherCurrency.list;
    }
    
    public List<AmtInWords> getAmtInWordsList()
    {
        return Arrays.asList(AmtInWords.values());
    }
    
    public List<AuthorisationRequirement> getAuthorisationRequirementList()
    {
        return Arrays.asList(AuthorisationRequirement.values());
    }
    
    public List<BillStatus> getPvStatusList()
    {
        return Arrays.asList(BillStatus.values());
    }
    
    public List<ReceivedStatus> getReceivedStatusList()
    {
        return Arrays.asList(ReceivedStatus.values());
    }
    


    
    public List<Election> getElectionsList() {
        return electionsList;
    }

    public List<Region> getRegionsList() {
        return regionsList;
    }

    public List<PoliticalParty> getPartysList() {
        return partysList;
    }

    public List<ConstituencyElection> getConstituencyElectionsList() {
        return constituencyElectionsList;
    }

    public List<Constituency> getConstituenciesList() {
        return constituenciesList;
    }

    public void setConstituenciesList(List<Constituency> constituenciesList) {
        this.constituenciesList = constituenciesList;
    }

}
