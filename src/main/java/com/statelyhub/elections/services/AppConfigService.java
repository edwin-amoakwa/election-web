/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.services;

import com.stately.common.sms.SmsProvider;
import com.stately.common.sms.SmsService;
import com.statelyhub.elections.Constant;
import com.statelyhub.elections.entities.Election;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import java.util.List;

/**
 *
 * @author edwin
 */
@Singleton
@Startup
public class AppConfigService {
    
       @Inject private CrudService crudService;
       
       public void init()
       {
           SmsService.SMS.setSmsProvider(SmsProvider.ROUTEE);
           SmsService.SMS.setSender(Constant.SENDER_OD);
       }
      
      public Election getCurrentElection()
      {
          List<Election> elections = crudService.findAll(Election.class);
          
          return elections.get(0);
      }
    
}
