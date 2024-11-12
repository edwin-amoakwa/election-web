/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.dto;

import com.statelyhub.elections.constants.ElectionType;
import com.statelyhub.elections.utils.FileResourceDto;
import java.util.List;

/**
 *
 * @author edwin
 */
public class ElectionResultSetDto extends BaseDto {

    private String volunteerId;
    private String pollingStationId;
    private String epsId;

    private List<AttachmentDto> attachmentsList;
    private FileResourceDto fileResource;

    private ElectionType electionType;
    private String resultSetId;
    private int votesCast;
    private int rejectedBallots;
    private int spoiltBallots;
    private int validVotes;

    private List<SubmittedResultDto> candidatesList;

    public ElectionType getElectionType() {
        return electionType;
    }

    public void setElectionType(ElectionType electionType) {
        this.electionType = electionType;
    }

    public List<SubmittedResultDto> getCandidatesList() {
        return candidatesList;
    }

    public void setCandidatesList(List<SubmittedResultDto> candidatesList) {
        this.candidatesList = candidatesList;
    }

    public int getRejectedBallots() {
        return rejectedBallots;
    }

    public void setRejectedBallots(int rejectedBallots) {
        this.rejectedBallots = rejectedBallots;
    }

    public int getSpoiltBallots() {
        return spoiltBallots;
    }

    public void setSpoiltBallots(int spoiltBallots) {
        this.spoiltBallots = spoiltBallots;
    }

    public int getVotesCast() {
        return votesCast;
    }

    public void setVotesCast(int votesCast) {
        this.votesCast = votesCast;
    }

    public int getValidVotes() {
        return validVotes;
    }

    public void setValidVotes(int validVotes) {
        this.validVotes = validVotes;
    }

    public String getResultSetId() {
        return resultSetId;
    }

    public void setResultSetId(String resultSetId) {
        this.resultSetId = resultSetId;
    }

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

    public List<AttachmentDto> getAttachmentsList() {
        return attachmentsList;
    }

    public void setAttachmentsList(List<AttachmentDto> attachmentsList) {
        this.attachmentsList = attachmentsList;
    }

    public FileResourceDto getFileResource() {
        return fileResource;
    }

    public void setFileResource(FileResourceDto fileResource) {
        this.fileResource = fileResource;
    }

}
