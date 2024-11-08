/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.services;

import com.statelyhub.elections.dto.LoginResponse;
import com.statelyhub.elections.dto.VolunteerDto;
import com.statelyhub.elections.entities.Volunteer;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

/**
 *
 * @author edwin
 */
@Stateless
public class VolunteerService {

    @Inject
    private CrudService crudService;

    public LoginResponse loginResponse(Volunteer volunteer) {
        return new LoginResponse(volunteer.getId(), toDto(volunteer));
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
