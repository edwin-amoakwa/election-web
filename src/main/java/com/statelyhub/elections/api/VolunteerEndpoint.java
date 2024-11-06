/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.api;

import com.stately.common.data.MappingResult;
import com.stately.common.utils.StringUtil;
import com.stately.modules.api.ApiResponse;
import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.elections.constants.VolunteerApprovalStatus;
import com.statelyhub.elections.constants.VolunteerClassification;
import com.statelyhub.elections.dto.VolunteerDto;
import com.statelyhub.elections.entities.Constituency;
import com.statelyhub.elections.entities.Volunteer;
import com.statelyhub.elections.services.CrudService;
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
                return ApiResponse.ok(toDto(volunteer));
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
    
    public VolunteerDto toDto(Volunteer record)
    {
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