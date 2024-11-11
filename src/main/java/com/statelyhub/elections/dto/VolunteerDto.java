/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.dto;

import com.statelyhub.elections.constants.UserDomain;
import com.statelyhub.elections.constants.VolunteerApprovalStatus;
import com.statelyhub.elections.constants.VolunteerClassification;

/**
 *
 * @author Stately
 */
public class VolunteerDto extends BaseDto
{
    private String volunteerName;
    private String emailAddress;
    private String mobileNo;
    private String constituencyId;
    private String constituencyName;
    private String regionId;
    private String regionName;
    private String pollingStationId;
    private String pollingStationCode;
    private String pollingStationName;
    private VolunteerClassification classification;
    private String classificationName;
    private VolunteerApprovalStatus approvalStatus;
    private String approvalStatusName;
    private String processedById;
    private String processedByName;
    private UserDomain userDomain;

    public String getVolunteerName() {
        return volunteerName;
    }

    public void setVolunteerName(String volunteerName) {
        this.volunteerName = volunteerName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    
    public String getConstituencyId() {
        return constituencyId;
    }

    public void setConstituencyId(String constituencyId) {
        this.constituencyId = constituencyId;
    }

    public String getConstituencyName() {
        return constituencyName;
    }

    public void setConstituencyName(String constituencyName) {
        this.constituencyName = constituencyName;
    }

    public String getPollingStationId() {
        return pollingStationId;
    }

    public void setPollingStationId(String pollingStationId) {
        this.pollingStationId = pollingStationId;
    }

    public String getPollingStationName() {
        return pollingStationName;
    }

    public void setPollingStationName(String pollingStationName) {
        this.pollingStationName = pollingStationName;
    }

    public VolunteerClassification getClassification() {
        return classification;
    }

    public void setClassification(VolunteerClassification classification) {
        this.classification = classification;
    }

    public String getClassificationName() {
        return classificationName;
    }

    public void setClassificationName(String classificationName) {
        this.classificationName = classificationName;
    }

    public VolunteerApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(VolunteerApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getApprovalStatusName() {
        return approvalStatusName;
    }

    public void setApprovalStatusName(String approvalStatusName) {
        this.approvalStatusName = approvalStatusName;
    }

    public String getProcessedById() {
        return processedById;
    }

    public void setProcessedById(String processedById) {
        this.processedById = processedById;
    }

    public String getProcessedByName() {
        return processedByName;
    }

    public void setProcessedByName(String processedByName) {
        this.processedByName = processedByName;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public UserDomain getUserDomain() {
        return userDomain;
    }

    public void setUserDomain(UserDomain userDomain) {
        this.userDomain = userDomain;
    }

    public String getPollingStationCode() {
        return pollingStationCode;
    }

    public void setPollingStationCode(String pollingStationCode) {
        this.pollingStationCode = pollingStationCode;
    }
    
    
}
