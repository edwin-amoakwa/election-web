/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.gafpv.jsf;

import com.stately.modules.web.jsf.JsfMsg;
import com.stately.modules.web.jsf.JsfUtil;
import com.statelyhub.gafpv.service.CrudService;
import com.statelyhub.gafpv.entities.FundingSource;
import com.statelyhub.gafpv.service.LoaderService;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;

import jakarta.inject.Inject;

/**
 *
 * @author Edwin
 */
@Named(value = "fundingSourceController")
@SessionScoped
public class FundingSourceController implements Serializable
{
    @Inject private UserSession userSession;
    @Inject private CrudService crudService;
    @Inject private LoaderService loaderService;
    private List<FundingSource> bankAccountsList;
    
    private FundingSource fundingSource = new FundingSource();
    
    public FundingSourceController()
    {
    }

    @PostConstruct
    public void init()
    {
        clear();
    }
    
    public void editFundingSource(FundingSource fundingSource)
    {
        this.fundingSource = fundingSource;
    }
    
    public void deleteFundingSource(FundingSource fundingSource)
    {
        try
        {
            crudService.delete(fundingSource);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void clear()
    {
        fundingSource = new FundingSource();
        fundingSource.setInstitution(userSession.getInstitution());
        JsfUtil.resetViewRoot();
    }
    
    public void saveCounterparty()
    {
        if(crudService.save(fundingSource) != null)
        {
            JsfMsg.msg(true);
            clear();
        }
        else
        {
            JsfMsg.msg(false);
        }
    }
    
    public List<FundingSource> getBankAccountsList()
    {
        bankAccountsList = loaderService.getFundingSourceList(userSession.getInstitution());
        return bankAccountsList;
    }

    public FundingSource getFundingSource()
    {
        return fundingSource;
    }

    public void setFundingSource(FundingSource fundingSource)
    {
        this.fundingSource = fundingSource;
    }

    
    
    
}
