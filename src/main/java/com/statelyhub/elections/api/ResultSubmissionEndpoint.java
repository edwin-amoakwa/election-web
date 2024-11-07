package com.statelyhub.elections.api;

import com.stately.common.utils.StringUtil;
import com.statelyhub.elections.dto.ResultSubmissionDto;
import com.stately.modules.api.ApiResponse;
import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.elections.constants.SubmissionLevel;
import com.statelyhub.elections.constants.SubmissionStatus;
import com.statelyhub.elections.dto.ElectionTypeResultDto;
import com.statelyhub.elections.dto.SubmittedResultDto;
import com.statelyhub.elections.entities.ElectionPollingStation;
import com.statelyhub.elections.entities.PollingStationResult;
import com.statelyhub.elections.entities.ResultSubmission;
import com.statelyhub.elections.entities.SubmittedResult;
import com.statelyhub.elections.entities.Volunteer;
import com.statelyhub.elections.model.ElectionTypeResult;
import com.statelyhub.elections.services.CrudService;
import com.statelyhub.elections.services.ElectionResultService;
import com.statelyhub.elections.services.ElectionService;
import jakarta.inject.Inject;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Edwin
 */
@Path("v1/submission")
public class ResultSubmissionEndpoint {

    @Inject
    private CrudService crudService;

    @Inject
    private ElectionService electionService;

    @Inject
    private ElectionResultService electionResultService;

    @GET
    @Path("/container/{pollingStationCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getResultContainer(@BeanParam DefaultHeaders qryparam, @PathParam("pollingStationCode") String pollingStationCode) {

        ElectionPollingStation eps = QryBuilder.get(crudService.getEm(), ElectionPollingStation.class)
                .addObjectParam(ElectionPollingStation._election, electionService.getCurrentElection())
                .addObjectParam(ElectionPollingStation._pollingStation_stationCode, pollingStationCode)
                .printQryInfo()
                .getSingleResult(ElectionPollingStation.class);

        if (eps == null) {
            return ApiResponse.badRequest("Code is wrong " + pollingStationCode);
        }

        System.out.println("....... " + qryparam.getUserId());
        Volunteer volunteer = crudService.find(Volunteer.class, qryparam.getUserId());

        if (volunteer == null) {
            return ApiResponse.ok("Unknown User");
        }

        // create one for volunteer
        ResultSubmission resultSubmission = QryBuilder.get(crudService.getEm(), ResultSubmission.class)
                .addObjectParam(ResultSubmission._volunteer, volunteer)
                .addObjectParam(ResultSubmission._electionPollingStation, eps)
                .getSingleResult(ResultSubmission.class);

        if (resultSubmission == null) {
            resultSubmission = new ResultSubmission();
            resultSubmission.setElectionPollingStation(eps);
            resultSubmission.setVolunteer(volunteer);
            resultSubmission.setSubmissionStatus(SubmissionStatus.PENDING);
            resultSubmission.setSubmissionLevel(SubmissionLevel.POLLING_STATION);

            crudService.save(resultSubmission);

            List<ElectionTypeResult> template = electionResultService.pollingStationBucket(eps);

            List<PollingStationResult> pollingStationResult = new LinkedList<>();
            for (ElectionTypeResult electionTypeResult : template) {
                pollingStationResult.addAll(electionTypeResult.getVotingsList());
            }

            for (PollingStationResult stationResult : pollingStationResult) {

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

        List<ElectionTypeResult> volunteerBuckets = electionResultService.volunteerEpsBucket(eps);

        List<ElectionTypeResultDto> dtos = new LinkedList<>();

        for (ElectionTypeResult volunteerBucket : volunteerBuckets) {
            ElectionTypeResultDto dto = new ElectionTypeResultDto();
            dto.setElectionType(volunteerBucket.getElectionType());

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
            dto.setCandidatesList(submittedList);

            dtos.add(dto);
        }
        ResultSubmissionDto submissionDto = new ResultSubmissionDto();
        submissionDto.setId(resultSubmission.getId());
        try {
            submissionDto.setVolunteerId(resultSubmission.getVolunteer().getId());
        } catch (Exception e) {
        }
        try {
            submissionDto.setEpsId(resultSubmission.getElectionPollingStation().getId());
            submissionDto.setPollingStationId(resultSubmission.getElectionPollingStation().getPollingStation().getId());
        } catch (Exception e) {
        }
        submissionDto.setVotingsList(dtos);

        return ApiResponse.ok(submissionDto);
    }

    @POST
    @Path("/submit")
    @Produces(MediaType.APPLICATION_JSON)
    public Response submitResult(@BeanParam DefaultHeaders qryparam, ResultSubmissionDto submissionDto) {

        ResultSubmission resultSubmission = crudService.find(ResultSubmission.class, submissionDto.getId());

        if (resultSubmission == null) {
            return ApiResponse.ok("Cannot Process Submission. Referesh and try again");
        }

        if (resultSubmission.getSubmissionStatus() == SubmissionStatus.LOCKED) {
//            return ApiResponse.ok("Your Submission is locked. You are not allowed to submit at this time");
        }

        for (ElectionTypeResultDto electionTypeResultDto : submissionDto.getVotingsList()) {
            List<SubmittedResult> resultsList = new LinkedList<>();

            for (SubmittedResultDto submittedResultDto : electionTypeResultDto.getCandidatesList()) {
                SubmittedResult submittedResult = crudService.find(SubmittedResult.class, submittedResultDto.getId());
                submittedResult.setInputResult(submittedResultDto.getVotes());
                resultsList.add(submittedResult);
//                crudService.save(submittedResult);
            }

            for (SubmittedResult compare : resultsList) 
            {
                int counter = 0;
                for (SubmittedResult submitted : resultsList) {
                    if (submitted.getInputResult() > compare.getInputResult()) {
                        counter++;
                    }
                }
                compare.setPosition(counter + 1);
            }

            if (resultSubmission.getElectionPollingStation().getVotersCount() != 0) 
            {
                double totalVotes = resultSubmission.getElectionPollingStation().getVotersCount();
                for (SubmittedResult submittedResult : resultsList) 
                {
                    double pct = submittedResult.getInputResult() / totalVotes;
                    submittedResult.setVotePct(pct);
                }
            }

            for (SubmittedResult submittedResult : resultsList) 
            {
                crudService.save(submittedResult);
            }

        }

        //run position logic;
        resultSubmission.setSubmissionStatus(SubmissionStatus.OPEN);

        crudService.save(resultSubmission);

        return ApiResponse.ok(submissionDto);
    }

}
