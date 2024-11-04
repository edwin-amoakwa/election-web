package com.statelyhub.elections.api;

import com.stately.modules.api.ApiResponse;
import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.elections.entities.ElectionPollingStation;
import com.statelyhub.elections.model.ElectionTypeResult;
import com.statelyhub.elections.services.CrudService;
import com.statelyhub.old.service.ElectionResultService;
import com.statelyhub.old.service.ElectionService;
import jakarta.inject.Inject;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * @author Edwin
 */
@Path("v1/submission")
public class ResultSubmissionEndpoint {

    @Inject private CrudService crudService;
    
    @Inject private ElectionService electionService;
    
    
    @Inject
    private ElectionResultService electionResultService;
    

    @GET
    @Path("/container/{pollingStationCode}")
    public Response getPeriodicSales(@BeanParam DefaultHeaders qryparam, @PathParam("pollingStationCode") String pollingStationCode) {
        
        ElectionPollingStation eps = QryBuilder.get(crudService.getEm(), ElectionPollingStation.class)
                .addObjectParam(ElectionPollingStation._election, electionService.getCurrentElection())
                .addObjectParam(ElectionPollingStation._pollingStation_stationCode, pollingStationCode)
                .getSingleResult(ElectionPollingStation.class);
        
        
        ResultSubmissionDto submissionDto = new ResultSubmissionDto();
        
        List<ElectionTypeResult> results = electionResultService.pollingStationBucket(eps);
        
        //covert to dto - and sent to sumissionDTO


        return ApiResponse.ok(submissionDto);
    }

    
}
