/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.old.service;

import com.statelyhub.elections.services.CrudService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

/**
 *
 * @author Edwin
 */
@Stateless
public class InterestScheduler
{
    

    @Inject private CrudService crudService;
    @Inject private NotificationService notificationService;
        
    
    
//    @Schedule(minute="*/30",hour="*", persistent=false)
    public void removeHoldingHoldersStillPending()
    {
        
        
        
        
    }
    
    
}
