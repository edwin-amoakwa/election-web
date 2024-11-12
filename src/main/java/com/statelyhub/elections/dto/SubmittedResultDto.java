/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.dto;

import com.statelyhub.elections.utils.FileResourceDto;

/**
 *
 * @author edwin
 */
public class SubmittedResultDto extends BaseDto{
    
    private String party;
    private String condidateName;
    private int votes;
    private FileResourceDto fileResource;

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getCondidateName() {
        return condidateName;
    }

    public void setCondidateName(String condidateName) {
        this.condidateName = condidateName;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public FileResourceDto getFileResource() {
        return fileResource;
    }

    public void setFileResource(FileResourceDto fileResource) {
        this.fileResource = fileResource;
    }
    
    
}
