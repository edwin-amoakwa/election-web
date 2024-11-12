package com.statelyhub.elections.api;

import com.stately.common.utils.FileUtilities;
import com.stately.modules.api.ApiResponse;
import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.elections.constants.ElectionType;
import com.statelyhub.elections.constants.SubmissionStatus;
import com.statelyhub.elections.dto.ElectionResultSetDto;
import com.statelyhub.elections.dto.SubmittedResultDto;
import com.statelyhub.elections.entities.ElectionPollingStation;
import com.statelyhub.elections.entities.ResultSubmission;
import com.statelyhub.elections.entities.SubmittedResult;
import com.statelyhub.elections.entities.Volunteer;
import com.statelyhub.elections.services.CrudService;
import com.statelyhub.elections.services.ElectionResultService;
import com.statelyhub.elections.services.ElectionService;
import com.statelyhub.elections.services.SubmisionService;
import com.statelyhub.elections.utils.FileUploadDto;
import jakarta.inject.Inject;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Base64;
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

    @Inject
    private SubmisionService submisionService;

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

        List<ElectionResultSetDto> dtos = new LinkedList<>();
        dtos.add(submisionService.getResultContainer(volunteer, eps, ElectionType.PRESIDENTIAL));
        dtos.add(submisionService.getResultContainer(volunteer, eps, ElectionType.PARLIAMENTARY));

        return ApiResponse.ok(dtos);
    }

    @POST
    @Path("/submit")
    @Produces(MediaType.APPLICATION_JSON)
    public Response submitResult(@BeanParam DefaultHeaders qryparam, ElectionResultSetDto resultSetDto) {

        ResultSubmission resultSubmission = crudService.find(ResultSubmission.class, resultSetDto.getId());
//        ElectionPollingStation eps = crudService.find(ElectionPollingStation.class, submissionDto.getEpsId());

        if (resultSubmission == null) {
            return ApiResponse.ok("Cannot Process Submission. Referesh and try again");
        }

        if (resultSubmission.getSubmissionStatus() == SubmissionStatus.LOCKED) {
//            return ApiResponse.ok("Your Submission is locked. You are not allowed to submit at this time");
        }

        List<SubmittedResult> resultsList = new LinkedList<>();

        resultSubmission.setRejectedBallots(resultSetDto.getRejectedBallots());
        resultSubmission.setSpoiltBallots(resultSetDto.getSpoiltBallots());
        resultSubmission.setValidVotes(resultSetDto.getValidVotes());
        resultSubmission.setTotalVotesCast(resultSetDto.getVotesCast());

        processImage(resultSetDto,resultSubmission);
//        crudService.save(resultSet);

        for (SubmittedResultDto submittedResultDto : resultSetDto.getCandidatesList()) 
        {
            SubmittedResult submittedResult = crudService.find(SubmittedResult.class, submittedResultDto.getId());
            System.out.println("..... " + submittedResult);
            submittedResult.setSubmittedResult(submittedResultDto.getVotes());
            resultsList.add(submittedResult);
        }

        for (SubmittedResult compare : resultsList) 
        {
            System.out.println(".... " + compare.getSubmittedResult());
            int counter = 0;
            for (SubmittedResult submitted : resultsList) {
                if (submitted.getSubmittedResult()> compare.getSubmittedResult()) {
                    counter++;
                }
            }
            compare.setPosition(counter + 1);
        }

        double totalVotes = resultsList.stream().mapToInt(SubmittedResult::getSubmittedResult).sum();
        if (totalVotes != 0) {
            for (SubmittedResult submittedResult : resultsList) {
                double pct = submittedResult.getSubmittedResult() / totalVotes;
                submittedResult.setVotePct(pct);
            }
        }

        for (SubmittedResult submittedResult : resultsList) {
            crudService.save(submittedResult);
        }

        //run position logic;
        resultSubmission.setSubmissionStatus(SubmissionStatus.OPEN);

        crudService.save(resultSubmission);
        

        return ApiResponse.ok(resultSetDto);
    }

    public void processImage(ElectionResultSetDto dto,ResultSubmission resultSubmission)
    {
        if(dto == null)return;
        
        if(dto.getFileResource() == null)return;
        if(dto.getFileResource().getFileUpload() == null)return;
        
        System.out.println("dto.getFileResource().getFileUpload().getFileString() = "+dto.getFileResource().getFileUpload().getFileString());
        System.out.println("dto.getFileResource().getFileUpload().getFileName() = "+dto.getFileResource().getFileUpload().getFileName());
        System.out.println("dto.getFileResource().getFileUpload().getFileFormat() = "+dto.getFileResource().getFileUpload().getFileFormat());
    
        FileUploadDto uploadDto = dto.getFileResource().getFileUpload();
        String ext = FileUtilities.getFileExtension(uploadDto.getFileName());
        
        String before = "";
        String after = uploadDto.getFileString();
        
        if(uploadDto.getFileString().contains(","))
        {
               before = uploadDto.getFileString().substring(0,uploadDto.getFileString().indexOf(","));
               after = uploadDto.getFileString().substring(uploadDto.getFileString().indexOf(",") + 1);
        }
 
        byte[] bytes = Base64.getDecoder().decode(after);
        
        resultSubmission.setSubmissionPicture(bytes);
        resultSubmission.setSubmissionPictureImageFormat(before);
    }
}
