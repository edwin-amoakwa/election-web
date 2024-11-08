/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.dto;

/**
 *
 * @author edwin
 */
public class LoginResponse 
{
    private String sessionId;
    private VolunteerDto volunteer;

    public LoginResponse(String sessionId, VolunteerDto volunteer) {
        this.sessionId = sessionId;
        this.volunteer = volunteer;
    }
    
    

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public VolunteerDto getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(VolunteerDto volunteer) {
        this.volunteer = volunteer;
    }
    
    
}
