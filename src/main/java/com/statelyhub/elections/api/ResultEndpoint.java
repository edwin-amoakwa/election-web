/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.api;

import com.stately.modules.api.ApiResponse;
import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.elections.constants.ResultStatus;
import com.statelyhub.elections.constants.SubmissionStatus;
import com.statelyhub.elections.entities.ResultSubmission;
import com.statelyhub.elections.entities.Volunteer;
import com.statelyhub.elections.services.AppConfigService;
import com.statelyhub.elections.services.CrudService;
import com.statelyhub.elections.services.ElectionService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author edwin
 */
@Path("v1/result")
@Stateless
public class ResultEndpoint {

    @Inject
    private CrudService crudService;
    @Inject
    private ElectionService electionService;

    @Inject
    private AppConfigService appConfigService;

    @GET
    @Path("/summary")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getColunteres() {
        int totalVolunters = QryBuilder.get(crudService.getEm(), Volunteer.class).count();

        int submisstion = QryBuilder.get(crudService.getEm(), ResultSubmission.class)
                .addObjectParamNotEqual(ResultSubmission._submissionStatus, SubmissionStatus.PENDING).count();

        JsonObjectBuilder data = Json.createObjectBuilder()
                .add("volunteers", totalVolunters)
                .add("totalSubmission", submisstion);

        JsonObjectBuilder result = Json.createObjectBuilder()
                .add("success", true)
                .add("statusCode", 200)
                .add("data", data);

        return Response.status(Response.Status.OK).entity(result.build()).build();

//         return ApiResponse.ok(total);
    }

}
