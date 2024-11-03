/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.gafpv.web;

import com.statelyhub.gafpv.jsf.UserSession;
import com.google.common.base.Strings;
import com.stately.common.model.DateRange;
import com.stately.modules.web.jsf.Msg;
import com.statelyhub.gafpv.entities.Bill;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;

/**
 *
 * @author Edwin
 */
@Named(value = "merchantSearch")
@SessionScoped
public class MemberSearch implements Serializable
{
    
//    @Inject private MemberService quotationService;
    @Inject private UserSession userSession;
    
    private String field;
    private String searchText;
    private List<Bill> quotationsList;
    
    private Bill quotation;
    private boolean showDialog = false;
    private List<Bill> selectedPersonnelList = new LinkedList<>();
//    private PersonnelStatus accountStatus = PersonnelStatus.ACTIVE;
//    private InsuranceBroker selectedInsuranceCompany;
    
    private final DateRange dateRange = new DateRange();
    
    
    
    public void clear()
    {
        searchText = "";
        quotationsList = null;
    }
    
    public void searchQuotation()
    {
        try 
        {
//            IipghMember quotationQP = new IipghMember();
//            quotationQP.set(searchText);
//            quotationQP.setClientName(searchText);
//            quotationQP.setQuotationId(searchText);
//            quotationQP.setBrokerAgent(userSession.getUserCompany());
//            personnelQP.setOthernames(searchText);
//            personnelQP.setPersonnelStatus(accountStatus);

            if(Strings.isNullOrEmpty(field))
            {
                String msg = "Pleae select a filter";
                Msg.error(msg);
                return;
            }
            
//            quotationsList = quotationService.searchMerchant(field, searchText);
//            System.out.println(quotationsList);
        }
        catch (Exception e)
        {
        }
    }
    
    public void addSelectedPersonnel(Bill personnel)
    {
        if(!selectedPersonnelList.contains(personnel))
        {
            selectedPersonnelList.add(personnel);
        }
        
    }
    
    public void selectCounterparty(Bill counterparty)
    {
        this.quotation = counterparty;
        showDialog = false;
    }

    public String getSearchText() 
    {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public List<Bill> getQuotationsList() {
        return quotationsList;
    }

    public void setQuotationsList(List<Bill> quotationsList) {
        this.quotationsList = quotationsList;
    }

    public Bill getQuotation() {
        return quotation;
    }

    public void setQuotation(Bill counterparty) {
        this.quotation = counterparty;
    }

    public boolean isShowDialog() {
        return showDialog;
    }

    public void setShowDialog(boolean showDialog) {
        this.showDialog = showDialog;
    }

    public List<Bill> getSelectedPersonnelList() {
        return selectedPersonnelList;
    }

    public void setSelectedPersonnelList(List<Bill> selectedPersonnelList) {
        this.selectedPersonnelList = selectedPersonnelList;
    }

    public String getField()
    {
        return field;
    }

    public void setField(String field)
    {
        this.field = field;
    }
    
}
