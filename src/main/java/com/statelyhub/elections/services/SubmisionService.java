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
import com.statelyhub.elections.dto.AttachmentDto;
import com.statelyhub.elections.dto.ElectionResultSetDto;
import com.statelyhub.elections.dto.SubmittedResultDto;
import com.statelyhub.elections.entities.ElectionPollingStation;
import com.statelyhub.elections.entities.PollingStationResult;
import com.statelyhub.elections.entities.ResultSubmission;
import com.statelyhub.elections.entities.SubmittedResult;
import com.statelyhub.elections.entities.SubmittedResultPicture;
import com.statelyhub.elections.entities.Volunteer;
import com.statelyhub.elections.model.ElectionTypeResult;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.Base64;
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
            resultSubmission = new ResultSubmission();
            resultSubmission.setElectionPollingStation(eps);
            resultSubmission.setVolunteer(volunteer);
            resultSubmission.setSubmissionStatus(SubmissionStatus.PENDING);
            resultSubmission.setSubmissionLevel(SubmissionLevel.POLLING_STATION);
            resultSubmission.setElectionType(electionType);
            resultSubmission.setConstituencyElection(eps.getConstituencyElection());
            resultSubmission.setElection(eps.getElection());

            crudService.save(resultSubmission);

            ElectionTypeResult template = electionResultService.pollingStationBucket(eps,electionType);



            for (PollingStationResult stationResult : template.getVotingsList()) {

                SubmittedResult submittedResult = new SubmittedResult();
                submittedResult.setElectionType(stationResult.getElectionType());
                submittedResult.setCandidate(stationResult.getCandidate());
                submittedResult.setResultSubmission(resultSubmission);
                submittedResult.setElectionPollingStation(eps);
                submittedResult.setViewOrder(stationResult.getViewOrder());
                submittedResult.setPollingStationResult(stationResult);
                submittedResult.setElectionContestant(stationResult.getElectionContestant());
                submittedResult.setCandidateName(stationResult.getElectionContestant().getCandidateName());
                crudService.save(submittedResult);
            }
        }
        
        
        
         electionResultSetDto.setId(resultSubmission.getId());

        ElectionTypeResult volunteerBucket = electionResultService.volunteerEpsBucket(eps,electionType);


            electionResultSetDto.setResultSetId(resultSubmission.getId());
            
            electionResultSetDto.setValidVotes(resultSubmission.getValidVotes());
            electionResultSetDto.setRejectedBallots(resultSubmission.getRejectedBallots());
            electionResultSetDto.setSpoiltBallots(resultSubmission.getSpoiltBallots());
            electionResultSetDto.setVotesCast(resultSubmission.getTotalVotesCast());
            

            List<SubmittedResultDto> submittedList = new LinkedList<>();
            for (SubmittedResult submittedResult : volunteerBucket.getSubmittedResultsList()) {
                SubmittedResultDto submittedDto = new SubmittedResultDto();

                submittedDto.setId(submittedResult.getId());
                submittedDto.setCondidateName(submittedResult.getElectionContestant().getCandidateName());
                submittedDto.setVotes(submittedResult.getSubmittedResult());
                submittedDto.setParty(submittedResult.getPartyDetails());

                if (StringUtil.isNullOrEmpty(submittedDto.getCondidateName())) {
                    submittedDto.setCondidateName("");
                }

                submittedList.add(submittedDto);
            }
            electionResultSetDto.setCandidatesList(submittedList);
            electionResultSetDto.setVolunteerId(resultSubmission.getVolunteer().getId());
            
            
            List<AttachmentDto> attachmentsList = new LinkedList<>();
            List<SubmittedResultPicture> images = QryBuilder.get(crudService.getEm(), SubmittedResultPicture.class)
                    .addObjectParam(SubmittedResultPicture._resultSubmission, resultSubmission)
                    .buildQry().getResultList();
            for (SubmittedResultPicture image : images) 
            {   
                AttachmentDto dto = new AttachmentDto();
//                dto.setDateTime(image.getCreatedDate());
                dto.setId(image.getId());
                try {
                    String base64
                            = image.getImageFormat()
                            + ","
                            + Base64.getEncoder().encodeToString(image.getImage());
                    dto.setFileDataBase64(base64);
                } catch (Exception e) {
                }
                attachmentsList.add(dto);
            }
            electionResultSetDto.setAttachmentsList(attachmentsList);
            
        try {
            electionResultSetDto.setEpsId(resultSubmission.getElectionPollingStation().getId());
            electionResultSetDto.setPollingStationId(resultSubmission.getElectionPollingStation().getPollingStation().getId());
        } catch (Exception e) {
        }
        

        return electionResultSetDto;
    }

}
