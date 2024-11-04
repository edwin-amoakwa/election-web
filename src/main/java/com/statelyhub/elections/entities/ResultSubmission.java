/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.entities;

import com.stately.modules.jpa2.UniqueEntityModel3;
import com.statelyhub.elections.constants.SubmissionLevel;
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
@Table(name = "result_submissions")
public class ResultSubmission extends UniqueEntityModel3 {

    public static final String _submissionLevel = "submissionLevel";
    @Column(name = "submission_level")
    @Enumerated(EnumType.STRING)
    private SubmissionLevel submissionLevel;

    public static final String _constituency = "constituency";
    @JoinColumn(name = "volunteer")
    @ManyToOne
    private Volunteer volunteer;

    public static final String _electionPollingStation = "electionPollingStation";
    @JoinColumn(name = "election_polling_station")
    @ManyToOne
    private ElectionPollingStation electionPollingStation;

    public SubmissionLevel getSubmissionLevel() {
        return submissionLevel;
    }

    public void setSubmissionLevel(SubmissionLevel submissionLevel) {
        this.submissionLevel = submissionLevel;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public ElectionPollingStation getElectionPollingStation() {
        return electionPollingStation;
    }

    public void setElectionPollingStation(ElectionPollingStation electionPollingStation) {
        this.electionPollingStation = electionPollingStation;
    }
    
    

}
