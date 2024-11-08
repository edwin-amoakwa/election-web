/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.model;

/**
 *
 * @author edwin
 */
public class PresidentialResult {
    private String partyName;
    private int presidentialVotes;
    private int position;
    
    private int presidentialValidVotes;
    private int parliamentValidVotes;
    private double presidentialPct;
    private double parliamentPct;
    private int seatCount;

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public int getPresidentialVotes() {
        return presidentialVotes;
    }

    public void setPresidentialVotes(int presidentialVotes) {
        this.presidentialVotes = presidentialVotes;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public double getPresidentialPct() {
        return presidentialPct;
    }

    public void setPresidentialPct(double presidentialPct) {
        this.presidentialPct = presidentialPct;
    }

    public double getParliamentPct() {
        return parliamentPct;
    }

    public void setParliamentPct(double parliamentPct) {
        this.parliamentPct = parliamentPct;
    }

    public int getPresidentialValidVotes() {
        return presidentialValidVotes;
    }

    public void setPresidentialValidVotes(int presidentialValidVotes) {
        this.presidentialValidVotes = presidentialValidVotes;
    }

    public int getParliamentValidVotes() {
        return parliamentValidVotes;
    }

    public void setParliamentValidVotes(int parliamentValidVotes) {
        this.parliamentValidVotes = parliamentValidVotes;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }
    
    
    
}
