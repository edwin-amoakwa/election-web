/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.dto;

import java.util.List;

/**
 *
 * @author edwin
 */
public class ResultSubmissionDto extends BaseDto
{
    private String volunteerId;
    private String pollingStationId;
    private String epsId;
    
    private List<ElectionResultSetDto> votingsList;
    private List<AttachmentDto> attachmentsList;

    public String getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(String volunteerId) {
        this.volunteerId = volunteerId;
    }

    public String getPollingStationId() {
        return pollingStationId;
    }

    public void setPollingStationId(String pollingStationId) {
        this.pollingStationId = pollingStationId;
    }

    public String getEpsId() {
        return epsId;
    }

    public void setEpsId(String epsId) {
        this.epsId = epsId;
    }

    public List<ElectionResultSetDto> getVotingsList() {
        return votingsList;
    }

    public void setVotingsList(List<ElectionResultSetDto> votingsList) {
        this.votingsList = votingsList;
    }

    public List<AttachmentDto> getAttachmentsList() {
        return attachmentsList;
    }

    public void setAttachmentsList(List<AttachmentDto> attachmentsList) {
        this.attachmentsList = attachmentsList;
    }
    
    
    
}
