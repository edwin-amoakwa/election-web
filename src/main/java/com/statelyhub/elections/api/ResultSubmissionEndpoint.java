package com.statelyhub.elections.api;

import com.stately.modules.api.ApiResponse;
import com.statelyhub.elections.services.CrudService;
import jakarta.inject.Inject;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

/**
 * @author Edwin
 */
@Path("v1/results")
public class ResultSubmissionEndpoint {

    @Inject private CrudService crudService;
    

    @GET
    @Path("/{pollingStationId}")
    public Response getPeriodicSales(@BeanParam DefaultHeaders qryparam,
            @QueryParam("version") String versionBuiuld) {


        return ApiResponse.ok("");
    }

    
}
