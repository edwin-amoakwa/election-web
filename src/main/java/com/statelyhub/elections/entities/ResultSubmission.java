/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.entities;

import com.stately.modules.jpa2.UniqueEntityModel3;
import com.statelyhub.elections.constants.SubmissionLevel;
import com.statelyhub.elections.constants.SubmissionStatus;
import com.statelyhub.elections.model.ElectionTypeResult;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.List;

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

    public static final String _submissionStatus = "submissionStatus";
    @Column(name = "submission_status")
    @Enumerated(EnumType.STRING)
    private SubmissionStatus submissionStatus;

    public static final String _volunteer = "volunteer";
    @JoinColumn(name = "volunteer")
    @ManyToOne
    private Volunteer volunteer;

    public static final String _electionPollingStation = "electionPollingStation";
    public static final String _electionPollingStation_constituencyElection =_electionPollingStation +  "." + ElectionPollingStation._constituencyElection;
    @JoinColumn(name = "election_polling_station")
    @ManyToOne
    private ElectionPollingStation electionPollingStation;

    @Transient
    private List<ElectionTypeResult> electionResultsList;

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

    public SubmissionStatus getSubmissionStatus() {
        return submissionStatus;
    }

    public void setSubmissionStatus(SubmissionStatus submissionStatus) {
        this.submissionStatus = submissionStatus;
    }

    public List<ElectionTypeResult> getElectionResultsList() {
        return electionResultsList;
    }

    public void setElectionResultsList(List<ElectionTypeResult> electionResultsList) {
        this.electionResultsList = electionResultsList;
    }

}
