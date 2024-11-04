/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.services;

import com.statelyhub.elections.services.CrudService;
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
  
  
//  public void paymentVoucher(Bill paymentVoucher)
//  {
//      if(Strings.isNullOrEmpty(paymentVoucher.getBillNo()))
//      {
//          LocalDate date = LocalDate.now();
//          int count = QryBuilder.get(crudService.getEm(), Bill.class)
////              .addObjectParam(Bill._created_Date, date)
//               .addObjectParam(Bill._unit, paymentVoucher.getUnit())
////              .addDateParam(FundingRequest._valueDate, fundingRequest.getValueDate(), QryBuilder.ComparismCriteria.EQUAL)
//              .count() + 1;
//          
//          String invoiceId = paymentVoucher.getUnit().getInstitutionId() 
//                  +"-"
//                  +date.format(DateTimeFormatter.ofPattern("yyMMdd"))
//                  + 
//                  Strings.padStart(count+"", 4, '0')
//                  + "";
//          
//          paymentVoucher.setBillNo(invoiceId);
//      
//      
//      }
//  }
//  
//  
//  
//  public void paymentVoucher(PaymentVocher paymentVoucher)
//  {
//      if(Strings.isNullOrEmpty(paymentVoucher.getPaymentNo()))
//      {
//          LocalDate date = LocalDate.now();
//          int count = QryBuilder.get(crudService.getEm(), PaymentVocher.class)
////              .addObjectParam(Bill._created_Date, date)
//               .addObjectParam(PaymentVocher._unit, paymentVoucher.getUnit())
////              .addDateParam(FundingRequest._valueDate, fundingRequest.getValueDate(), QryBuilder.ComparismCriteria.EQUAL)
//              .count() + 1;
//          
//          String invoiceId = paymentVoucher.getUnit().getInstitutionId() 
//                  +"-PV"
//                  +date.format(DateTimeFormatter.ofPattern("yyMMdd"))
//                  + 
//                  Strings.padStart(count+"", 4, '0')
//                  + "";
//          
//          paymentVoucher.setPaymentNo(invoiceId);
//      
//      
//      }
//  }
}
