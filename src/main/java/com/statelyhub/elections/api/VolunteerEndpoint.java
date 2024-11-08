/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.api;

import com.stately.common.data.MappingResult;
import com.stately.common.sms.SmsProvider;
import com.stately.common.sms.SmsService;
import com.stately.common.utils.StringUtil;
import com.stately.modules.api.ApiResponse;
import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.elections.Constant;
import com.statelyhub.elections.constants.VolunteerApprovalStatus;
import com.statelyhub.elections.constants.VolunteerClassification;
import com.statelyhub.elections.dto.LoginResponse;
import com.statelyhub.elections.dto.VolunteerDto;
import com.statelyhub.elections.entities.Constituency;
import com.statelyhub.elections.entities.PollingStation;
import com.statelyhub.elections.entities.Volunteer;
import com.statelyhub.elections.services.AppConfigService;
import com.statelyhub.elections.services.CrudService;
import com.statelyhub.elections.services.VolunteerService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author Stately
 */
@Path("v1/volunteer")
@Stateless  
public class VolunteerEndpoint 
{
    @Inject private CrudService crudService;
    
    @Inject private VolunteerService volunteerService;
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerVolunteer(
            @BeanParam DefaultHeaders qryparam, 
            VolunteerDto dto
    ) 
    {
        qryparam.clean();
        try 
        {
            MappingResult<Volunteer> result = toEntity(new Volunteer(), dto);
            if(result.hasErrors())
            {
                return ApiResponse.error(result.getErrorList());
            }
            
            Volunteer volunteer = result.getMapping();
            volunteer.setApprovalStatus(VolunteerApprovalStatus.PENDING);
            volunteer.setClassification(VolunteerClassification.UNVERIFIED);
            volunteer = crudService.save(volunteer);
            if(volunteer != null)
            {
                
                String msg = "Thank you for Registering. We will revert when you application is processed via SMS";
                 SmsService.SMS.sendSms(volunteer.getMobileNo(), msg);
                
                 
                 
                 
                return ApiResponse.ok(msg,volunteerService.loginResponse(volunteer));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return ApiResponse.error("Error Processing Data!!");
    }
    
    public MappingResult<Volunteer> toEntity(Volunteer record,VolunteerDto dto)
    {
        MappingResult result = new MappingResult();
        
        try {
            dto.setVolunteerName(dto.getVolunteerName().trim());
        } catch (Exception e) {}
        try {
            dto.setMobileNo(dto.getMobileNo().trim());
        } catch (Exception e) {}
        
        
        try{ 
            Constituency constituency = QryBuilder.get(crudService.getEm(), Constituency.class)
                .addObjectParam(Constituency._id, dto.getConstituencyId())
                .addObjectParam(Constituency._region_id, dto.getRegionId())
                .getSingleResult(Constituency.class);
            record.setConstituency(constituency);
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
        if(!StringUtil.isNullOrEmpty(dto.getPollingStationId()))
        {
            try{ 
                PollingStation pollingStation = QryBuilder.get(crudService.getEm(), PollingStation.class)
                    .addObjectParam(PollingStation._id, dto.getPollingStationId())
                    .addObjectParam(PollingStation._constituency_id, dto.getConstituencyId())
                    .addObjectParam(PollingStation._constituency_region_id, dto.getRegionId())
                        .printQryInfo()
                    .getSingleResult(PollingStation.class);
                record.setPollingStation(pollingStation);
            } catch (Exception e) {
                e.printStackTrace();
            }   
            
//            if(record.getPollingStation() == null)
//            {
//                return result.addError("Specified Polling Station Not Found");
//            }
        }
        
        if(StringUtil.isNullOrEmpty(dto.getVolunteerName()))
        {
            return result.addError("Specify Volunteer Name");
        }
        if(StringUtil.isNullOrEmpty(dto.getMobileNo())
                || dto.getMobileNo().length() < 10)
        {
            return result.addError("Specify Valid Phone Contact");
        }
        
        if(record.getConstituency()== null)
        {
            return result.addError("Specify Constituency");
        }
        
        record.setVolunteerName(dto.getVolunteerName());
        record.setMobileNo(dto.getMobileNo());
//        record.set(dto.get);

        try {
            int count = QryBuilder.get(crudService.getEm(), Volunteer.class)
                    .addObjectParam(Volunteer._mobileNo, record.getMobileNo())
                    .count();
            if(count > 0)
            {
                return result.addError("Volunteer Exists With Same Specified Phone Contact");
            }
        } catch (Exception e) {
        }
        result.setMapping(record);
        
        return result;
    }
 
}
