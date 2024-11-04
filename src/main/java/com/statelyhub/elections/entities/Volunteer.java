/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.entities;

import com.stately.modules.jpa2.UniqueEntityModel3;
import com.statelyhub.elections.constants.VolunteerApprovalStatus;
import com.statelyhub.elections.constants.VolunteerClassification;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "volunteers")
public class Volunteer extends UniqueEntityModel3 {

    public static final String _candidateName = "candidateName";
    @Column(name = "volunteer_name")
    private String volunteerName;

    public static final String _emailAddress = "emailAddress";
    @Column(name = "email_address")
    private String emailAddress;

    public static final String _mobileNo = "mobileNo";
    @Column(name = "mobile_no")
    private String mobileNo;

    public static final String _userPassword = "userPassword";
    @Column(name = "user_password")
    private String userPassword;

    public static final String _constituency = "constituency";
    @JoinColumn(name = "constituency")
    @ManyToOne
    private Constituency constituency;

    public static final String _pollingStation = "pollingStation";
    @JoinColumn(name = "polling_station")
    @ManyToOne
    private PollingStation pollingStation;

    public static final String _seat = "classification";
    @Column(name = "classification")
    @Enumerated(EnumType.STRING)
    private VolunteerClassification classification;

    public static final String _approvalStatus = "approvalStatus";
    @Column(name = "approval_status")
    @Enumerated(EnumType.STRING)
    private VolunteerApprovalStatus approvalStatus;
    
    public static final String _processedBy = "processedBy";
    @JoinColumn(name = "processed_by")
    @ManyToOne
    private UserAccount processedBy;

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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Constituency getConstituency() {
        return constituency;
    }

    public void setConstituency(Constituency constituency) {
        this.constituency = constituency;
    }

    public PollingStation getPollingStation() {
        return pollingStation;
    }

    public void setPollingStation(PollingStation pollingStation) {
        this.pollingStation = pollingStation;
    }

    public VolunteerClassification getClassification() {
        return classification;
    }

    public void setClassification(VolunteerClassification classification) {
        this.classification = classification;
    }

    public VolunteerApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(VolunteerApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public UserAccount getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(UserAccount processedBy) {
        this.processedBy = processedBy;
    }
    
    
    
}
