/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.old.service;


import com.statelyhub.elections.services.CrudService;
import com.google.common.base.Strings;
import com.stately.common.sms.SmsMessage;
import com.stately.common.sms.SmsProvider;
import java.net.HttpURLConnection;
import java.net.URL;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

/**
 *
 * @author Edwin
 */
@Stateless
public class NotificationService
{
    @Inject private CrudService crudService;
    
    
    SmsMessage bulkSmsGH = new SmsMessage(SmsProvider.SMSGH);
//    private String message = "";

    @PostConstruct
    public void init() {

        try {

            bulkSmsGH.setSender("EasiCash");

            bulkSmsGH.setClientId("foqvtuxi");
            bulkSmsGH.setClientSecret("iwwovxay");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean sendMessage(String phoneNo, String message) {
        if (Strings.isNullOrEmpty(message) || Strings.isNullOrEmpty(phoneNo)) {
            System.out.println(message + " -- " + phoneNo);
            return false;
        }

        HttpURLConnection conn = null;
        try {

            bulkSmsGH.setTo(phoneNo.trim());
            bulkSmsGH.setMsg(message);

            String fullUrl = bulkSmsGH.getFullPath();
////                String fullUrl = baseUrl + "?" + param;
//
//            System.out.println("fullUrl : " + fullUrl);
//
            URL url = new URL(fullUrl);

            conn = (HttpURLConnection) url.openConnection();

            if (bulkSmsGH.isSucessfull(conn)) {
//                holderScheduledStatement.setSentStatus(SentStatus.SENT);
//                holderScheduledStatement.setSentDate(new Date());
//                holderScheduledStatement.setRemarks("SMS Sent");
//                crudService.save(holderScheduledStatement);

                return true;

            } else {
//                holderScheduledStatement.setRemarks("Not Sent");
//                holderScheduledStatement.setSentStatus(SentStatus.PENDING);
//                holderScheduledStatement.setSentDate(new Date());
//                crudService.save(holderScheduledStatement);

                return false;

            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

//    @Asynchronous
//    public void sendPrincipalReceived(PrincipalRepayment principalRepayment)
//    {
//        try
//        {
//            principalRepayment = crudService.find(PrincipalRepayment.class, principalRepayment.getId());
//        
//        String msg = "Hello " + principalRepayment.getMerchant().getSurname() +","
//                + "\nWe have received your principal payment of " 
//                + NumberFormattingUtils.getFormatedAmount(principalRepayment.getAmount())+ ". "
//                + " Remainig amount is " + NumberFormattingUtils.getFormatedAmount(principalRepayment.getFundingRequest().getAmountLeft());
//        
//        boolean sent = sendMessage(principalRepayment.getMerchant().getSmsNo(), msg);
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }

    
}
