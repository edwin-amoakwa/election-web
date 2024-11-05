/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.entities;

import com.stately.modules.jpa2.UniqueEntityModel3;
import com.statelyhub.elections.constants.ElectionType;
import com.statelyhub.elections.constants.ResultSource;
import com.statelyhub.elections.constants.ResultStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

/**
 *
 * @author Edwin
 */
@MappedSuperclass
public class Result extends UniqueEntityModel3 {

    public static final String _electionContestant = "electionContestant";
    public static final String _electionContestant_electionType = _electionContestant + "." + ElectionContestant._electionType;
    @JoinColumn(name = "election_contestant")
    @ManyToOne
    private ElectionContestant electionContestant;

    public static final String _viewOrder = "viewOrder";
    @Column(name = "view_order")
    private int viewOrder;

    public static final String _constituencyElection = "constituencyElection";
    @JoinColumn(name = "constituency_election")
    @ManyToOne
    private ConstituencyElection constituencyElection;

//    public static final String _resultSubmission = "resultSubmission";
//    @JoinColumn(name = "result_submission")
//    @ManyToOne
//    private ResultSubmission resultSubmission;

//    public static final String _volunteerResult = "volunteerResult";
//    @JoinColumn(name = "volunteer_result")
//    @ManyToOne
//    private SubmittedResult volunteerResult;

    public static final String _candidateName = "candidateName";
    @Column(name = "candidate_name")
    private String candidateName;

    public static final String _candidate = "candidate";
    @JoinColumn(name = "candidate")
    @ManyToOne
    private Candidate candidate;

    public static final String _electionType = "electionType";
    @Column(name = "election_type")
    @Enumerated(EnumType.STRING)
    private ElectionType electionType;

    public static final String _resultSource = "resultSource";
    @Column(name = "result_source")
    @Enumerated(EnumType.STRING)
    private ResultSource resultSource;

    public static final String _resultStatus = "resultStatus";
    @Column(name = "result_status")
    @Enumerated(EnumType.STRING)
    private ResultStatus resultStatus;

    public static final String _officialResult = "officialResult";
    @Column(name = "official_result")
    private int officialResult;

    public static final String _submittedResult = "submittedResult";
    @Column(name = "submitted_result")
    private int submittedResult;

    public static final String _inputResult = "inputResult";
    @Column(name = "input_result")
    private int inputResult;

    public static final String _acceptedResult = "acceptedResult";
    @Column(name = "accepted_result")
    private int acceptedResult;

    public static final String _position = "position";
    @Column(name = "position")
    private int position;

    public static final String _votePct = "votePct";
    @Column(name = "vote_pct")
    private double votePct;

    public String getPartyDetails() {
        if (electionContestant != null) {
            if (electionContestant.getParty() != null) {
                return electionContestant.getParty().getInitials();
            } else {
                return "INDB";
            }
        }
        return "";
    }

    public ConstituencyElection getConstituencyElection() {
        return constituencyElection;
    }

    public void setConstituencyElection(ConstituencyElection constituencyElection) {
        this.constituencyElection = constituencyElection;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public ElectionType getElectionType() {
        return electionType;
    }

    public void setElectionType(ElectionType electionType) {
        this.electionType = electionType;
    }

    public int getOfficialResult() {
        return officialResult;
    }

    public void setOfficialResult(int officialResult) {
        this.officialResult = officialResult;
    }

    public int getSubmittedResult() {
        return submittedResult;
    }

    public void setSubmittedResult(int submittedResult) {
        this.submittedResult = submittedResult;
    }

    public int getAcceptedResult() {
        return acceptedResult;
    }

    public void setAcceptedResult(int acceptedResult) {
        this.acceptedResult = acceptedResult;
    }

    public ResultSource getResultSource() {
        return resultSource;
    }

    public void setResultSource(ResultSource resultSource) {
        this.resultSource = resultSource;
    }

    public ResultStatus getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(ResultStatus resultStatus) {
        this.resultStatus = resultStatus;
    }

    public int getViewOrder() {
        return viewOrder;
    }

    public void setViewOrder(int viewOrder) {
        this.viewOrder = viewOrder;
    }

    public int getInputResult() {
        return inputResult;
    }

    public void setInputResult(int inputResult) {
        this.inputResult = inputResult;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public ElectionContestant getElectionContestant() {
        return electionContestant;
    }

    public void setElectionContestant(ElectionContestant electionContestant) {
        this.electionContestant = electionContestant;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public double getVotePct() {
        return votePct;
    }

    public void setVotePct(double votePct) {
        this.votePct = votePct;
    }

//    public ResultSubmission getResultSubmission() {
//        return resultSubmission;
//    }
//
//    public void setResultSubmission(ResultSubmission resultSubmission) {
//        this.resultSubmission = resultSubmission;
//    }

//    public SubmittedResult getVolunteerResult() {
//        return volunteerResult;
//    }
//
//    public void setVolunteerResult(SubmittedResult volunteerResult) {
//        this.volunteerResult = volunteerResult;
//    }

}
