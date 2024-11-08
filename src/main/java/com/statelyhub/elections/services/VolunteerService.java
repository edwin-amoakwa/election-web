/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.services;

import com.stately.common.data.ProcResponse;
import com.stately.common.security.SecurityHash;
import com.stately.common.sms.SmsService;
import com.stately.common.utils.StringUtil;
import com.statelyhub.elections.constants.VolunteerApprovalStatus;
import com.statelyhub.elections.dto.LoginResponse;
import com.statelyhub.elections.dto.VolunteerDto;
import com.statelyhub.elections.entities.Volunteer;
import com.statelyhub.elections.jsf.UserSession;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

/**
 *
 * @author edwin
 */
@Stateless
public class VolunteerService {

    @Inject private CrudService crudService;
    @Inject private UserSession userSession;

    public LoginResponse loginResponse(Volunteer volunteer) {
        return new LoginResponse(volunteer.getId(), toDto(volunteer));
    }
    
    public ProcResponse approveVolunteer(Volunteer volunteer)
    {
        ProcResponse response = new ProcResponse();
        
        try 
        {
            if(volunteer == null)
            {
                return response.error("No Volunteer Specified");
            }
            
            if(volunteer.getConstituency() == null)
            {
                return response.error("Specify Constituency");
            }
            
            if(StringUtil.isNullOrEmpty(volunteer.getMobileNo()))
            {
                return response.error("Specify Phone Number of Volunteer");
            }
            
            //set password for volunteer
            String token = crudService.generateId().substring(0, 6);
            
            System.out.println("TOKEN:::::::" + token);
            String msg =
                "Congratulations! Your Account Has Been Approved. You can Login with this PIN "
                    + token + ". Kindly reset your password after login";

            // send email
            System.out.println("Password Token: " + token);

            String hashedPassword = SecurityHash.getInstance().shaHash(token);

            volunteer.setUserPassword(hashedPassword);

            volunteer.setApprovalStatus(VolunteerApprovalStatus.APPROVED);
            volunteer.setProcessedBy(userSession.getAccountUR());
            volunteer = crudService.save(volunteer);
            if(volunteer == null)
            {
                return response.error("Error Approving Volunteer");
            }
            
            SmsService.SMS.sendSms(volunteer.getMobileNo(), msg);
            response.setSuccess(true);
            response.setData(volunteer);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return response;
    }
    
    public ProcResponse resetVolunteerPassword(Volunteer volunteer)
    {
        ProcResponse response = new ProcResponse();
        
        try 
        {
            if(volunteer == null)
            {
                return response.error("No Volunteer Specified");
            }
            
            if(volunteer.getConstituency() == null)
            {
                return response.error("Specify Constituency");
            }
            
            if(StringUtil.isNullOrEmpty(volunteer.getMobileNo()))
            {
                return response.error("Specify Phone Number of Volunteer");
            }
            
            //set password for volunteer
            String token = crudService.generateId().substring(0, 6);
            
            System.out.println("TOKEN:::::::" + token);
            String msg =
                "Congratulations! Your PIN Reset Request has been acknowledged. "
                    + "You can Login with this PIN "
                    + token + ". Kindly reset your password after login";

            // send email
            System.out.println("Password Token: " + token);

            String hashedPassword = SecurityHash.getInstance().shaHash(token);

            volunteer.setUserPassword(hashedPassword);

            volunteer = crudService.save(volunteer);
            if(volunteer == null)
            {
                return response.error("Error Ressetting PIN of Volunteer");
            }
            
            SmsService.SMS.sendSms(volunteer.getMobileNo(), msg);
            response.setSuccess(true);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return response;
    }

    public VolunteerDto toDto(Volunteer record) {
        VolunteerDto dto = new VolunteerDto();
        dto.setId(record.getId());
        dto.setEmailAddress(record.getEmailAddress());
        dto.setMobileNo(record.getMobileNo());
        dto.setVolunteerName(record.getVolunteerName());

        try {
            dto.setApprovalStatus(record.getApprovalStatus());
            dto.setApprovalStatusName(record.getApprovalStatus().getLabel());
        } catch (Exception e) {
        }
        try {
            dto.setClassification(record.getClassification());
            dto.setClassificationName(record.getClassification().getLabel());
        } catch (Exception e) {
        }
        try {
            dto.setPollingStationId(record.getPollingStation().getId());
            dto.setPollingStationName(record.getPollingStation().getStationName());
        } catch (Exception e) {
        }
        try {
            dto.setConstituencyId(record.getConstituency().getId());
            dto.setConstituencyName(record.getConstituency().getConstituencyName());
            dto.setRegionId(record.getConstituency().getRegion().getId());
            dto.setRegionName(record.getConstituency().getRegion().getRegionName());
        } catch (Exception e) {
        }
        try {
            dto.setProcessedById(record.getProcessedBy().getId());
            dto.setProcessedByName(record.getProcessedBy().getAccountName());
        } catch (Exception e) {
        }
//        dto.set(record.get);
        return dto;
    }
}
