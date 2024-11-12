/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.services;

import com.stately.common.utils.StringUtil;
import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.elections.constants.ElectionType;
import com.statelyhub.elections.constants.SubmissionLevel;
import com.statelyhub.elections.constants.SubmissionStatus;
import com.statelyhub.elections.dto.ElectionResultSetDto;
import com.statelyhub.elections.dto.SubmittedResultDto;
import com.statelyhub.elections.entities.ElectionPollingStation;
import com.statelyhub.elections.entities.PollingStationResult;
import com.statelyhub.elections.entities.PollingStationResult;
import com.statelyhub.elections.entities.ResultSubmission;
import com.statelyhub.elections.entities.SubmittedResult;
import com.statelyhub.elections.entities.Volunteer;
import com.statelyhub.elections.model.ElectionTypeResult;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author edwin
 */
@Stateless
public class SubmisionService {
    
     @Inject
    private CrudService crudService;

    @Inject
    private ElectionService electionService;

    @Inject
    private ElectionResultService electionResultService;
    
    
    public ElectionResultSetDto getResultContainer(Volunteer volunteer ,ElectionPollingStation eps, ElectionType electionType) {


        ElectionResultSetDto electionResultSetDto = new ElectionResultSetDto();
            electionResultSetDto.setElectionType(electionType);


        // create one for volunteer
        ResultSubmission resultSubmission = QryBuilder.get(crudService.getEm(), ResultSubmission.class)
                .addObjectParam(ResultSubmission._volunteer, volunteer)
                .addObjectParam(ResultSubmission._electionPollingStation, eps)
                .addObjectParam(ResultSubmission._electionType, electionType)
                .getSingleResult(ResultSubmission.class);

        if (resultSubmission == null) 
        {
            
//            ConstituencyElection ce = QryBuilder.get(crudService.getEm(), ConstituencyElection.class)
//                    .addObjectParam(ConstituencyElection._constituency, eps)
            
            resultSubmission = new ResultSubmission();
            resultSubmission.setElectionPollingStation(eps);
            resultSubmission.setVolunteer(volunteer);
            resultSubmission.setSubmissionStatus(SubmissionStatus.PENDING);
            resultSubmission.setSubmissionLevel(SubmissionLevel.POLLING_STATION);
            resultSubmission.setElectionType(electionType);

            crudService.save(resultSubmission);

            ElectionTypeResult template = electionResultService.pollingStationBucket(eps,electionType);

//            List<PollingStationResult> pollingStationResult = new LinkedList<>();
//            for (ElectionTypeResult electionTypeResult : template) 
//            {
//                SubmittedResultSet resultSet = new SubmittedResultSet();
//                resultSet.setResultSubmissionId(resultSubmission.getId());
//                resultSet.setElectionType(template.getElectionType());
//                
//                crudService.save(resultSet);
                
//                pollingStationResult.addAll(template.getVotingsList());
//            }

            for (PollingStationResult stationResult : template.getVotingsList()) {

                SubmittedResult submittedResult = new SubmittedResult();
                submittedResult.setElectionType(stationResult.getElectionType());
                submittedResult.setCandidate(stationResult.getCandidate());
                submittedResult.setResultSubmission(resultSubmission);
                submittedResult.setElectionPollingStation(eps);
                submittedResult.setViewOrder(stationResult.getViewOrder());
                submittedResult.setPollingStationResult(stationResult);
                submittedResult.setElectionContestant(stationResult.getElectionContestant());
                crudService.save(submittedResult);
            }
        }
        
        
        
         electionResultSetDto.setId(resultSubmission.getId());

        ElectionTypeResult volunteerBucket = electionResultService.volunteerEpsBucket(eps,electionType);

//        List<ElectionResultSetDto> dtos = new LinkedList<>();

//        for (ElectionTypeResult volunteerBucket : volunteerBuckets) 
//        {
            
            
//            SubmittedResultSet resultSet = QryBuilder.get(crudService.getEm(), SubmittedResultSet.class)
////                    .addObjectParam(SubmittedResultSet._electionType, volunteerBucket.getElectionType())
//                    .addObjectParam(SubmittedResultSet._resultSubmission, resultSubmission.getId())
//                    .getSingleResult(SubmittedResultSet.class);
//            
//            if(resultSet == null)
//            {
//                resultSet = new SubmittedResultSet();
//                resultSet.setResultSubmissionId(resultSubmission.getId());
//                resultSet.setElectionType(volunteerBucket.getElectionType());
//                crudService.save(resultSet);
//            }
             
            electionResultSetDto.setResultSetId(resultSubmission.getId());
            
            electionResultSetDto.setValidVotes(resultSubmission.getValidVotes());
            electionResultSetDto.setRejectedBallots(resultSubmission.getRejectedBallots());
            electionResultSetDto.setSpoiltBallots(resultSubmission.getSpoiltBallots());
            electionResultSetDto.setVotesCast(resultSubmission.getTotalVotesCast());
            

            List<SubmittedResultDto> submittedList = new LinkedList<>();
            for (SubmittedResult submittedResult : volunteerBucket.getSubmittedResultsList()) {
                SubmittedResultDto submittedDto = new SubmittedResultDto();

                submittedDto.setId(submittedResult.getId());
                submittedDto.setCondidateName(submittedResult.getCandidateName());
                submittedDto.setVotes(submittedResult.getSubmittedResult());
                submittedDto.setParty(submittedResult.getPartyDetails());

                if (StringUtil.isNullOrEmpty(submittedDto.getCondidateName())) {
                    submittedDto.setCondidateName("");
                }

                submittedList.add(submittedDto);
            }
            electionResultSetDto.setCandidatesList(submittedList);


  
            electionResultSetDto.setVolunteerId(resultSubmission.getVolunteer().getId());
            
        try {
            electionResultSetDto.setEpsId(resultSubmission.getElectionPollingStation().getId());
            electionResultSetDto.setPollingStationId(resultSubmission.getElectionPollingStation().getPollingStation().getId());
        } catch (Exception e) {
        }
        

        return electionResultSetDto;
    }

}
