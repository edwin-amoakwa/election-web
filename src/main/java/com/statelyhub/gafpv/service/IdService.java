/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.gafpv.service;

import com.google.common.base.Strings;
import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.gafpv.entities.Bill;
import com.statelyhub.gafpv.entities.PaymentVocher;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

/**
 *
 * @author Edwin
 */
@Stateless
public class IdService 
{
    
    
  
  @Inject private CrudService crudService;
  
  
  public void paymentVoucher(Bill paymentVoucher)
  {
      if(Strings.isNullOrEmpty(paymentVoucher.getBillNo()))
      {
          LocalDate date = LocalDate.now();
          int count = QryBuilder.get(crudService.getEm(), Bill.class)
//              .addObjectParam(Bill._created_Date, date)
               .addObjectParam(Bill._unit, paymentVoucher.getUnit())
//              .addDateParam(FundingRequest._valueDate, fundingRequest.getValueDate(), QryBuilder.ComparismCriteria.EQUAL)
              .count() + 1;
          
          String invoiceId = paymentVoucher.getUnit().getInstitutionId() 
                  +"-"
                  +date.format(DateTimeFormatter.ofPattern("yyMMdd"))
                  + 
                  Strings.padStart(count+"", 4, '0')
                  + "";
          
          paymentVoucher.setBillNo(invoiceId);
      
      
      }
  }
  
  
  
  public void paymentVoucher(PaymentVocher paymentVoucher)
  {
      if(Strings.isNullOrEmpty(paymentVoucher.getPaymentNo()))
      {
          LocalDate date = LocalDate.now();
          int count = QryBuilder.get(crudService.getEm(), PaymentVocher.class)
//              .addObjectParam(Bill._created_Date, date)
               .addObjectParam(PaymentVocher._unit, paymentVoucher.getUnit())
//              .addDateParam(FundingRequest._valueDate, fundingRequest.getValueDate(), QryBuilder.ComparismCriteria.EQUAL)
              .count() + 1;
          
          String invoiceId = paymentVoucher.getUnit().getInstitutionId() 
                  +"-PV"
                  +date.format(DateTimeFormatter.ofPattern("yyMMdd"))
                  + 
                  Strings.padStart(count+"", 4, '0')
                  + "";
          
          paymentVoucher.setPaymentNo(invoiceId);
      
      
      }
  }
}
