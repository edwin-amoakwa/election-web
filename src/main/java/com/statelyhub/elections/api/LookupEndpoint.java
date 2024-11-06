/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.api;

import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.elections.entities.Constituency;
import com.statelyhub.elections.entities.Region;
import com.statelyhub.elections.services.CrudService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author Stately
 */
@Path("v1/data")
@Stateless
public class LookupEndpoint 
{
    @Inject private CrudService crudService;
    
    @GET
    @Path("/regions")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRegions()
    {
        List<Region> dataList = QryBuilder.get(crudService.getEm(), Region.class)
                .orderByAsc(Region._regionName)
                .printQryInfo().buildQry().getResultList();
        
        JsonArrayBuilder dataArray = Json.createArrayBuilder();

        for (Region data : dataList)
       {
           JsonObjectBuilder object = Json.createObjectBuilder()
                   .add("id", data.getId())
                   .add("itemName", data.getRegionName()== null ? "":data.getRegionName() );

           dataArray.add(object);
       }

         JsonObjectBuilder result =  Json.createObjectBuilder()
             .add("success", true)
             .add("statusCode", 200)
             .add("data", dataArray);
                 
         return Response.status(Response.Status.OK).entity(result.build()).build(); 
    }
    
    @GET
    @Path("/constituencies") 
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConstituencies(@QueryParam("region")String regionId)
    {
        List<Constituency> dataList = QryBuilder.get(crudService.getEm(), Constituency.class)
                .addObjectParamWhenNotNull(Constituency._region_id, regionId)
                .orderByAsc(Constituency._constituencyName)
                .printQryInfo().buildQry().getResultList();
        
        JsonArrayBuilder dataArray = Json.createArrayBuilder();

        for (Constituency data : dataList)
       {
           JsonObjectBuilder object = Json.createObjectBuilder()
                   .add("id", data.getId())
                   .add("itemName", data.getConstituencyName()== null ? "":data.getConstituencyName() )
                   .add("constituencyName", data.getConstituencyName()== null ? "":data.getConstituencyName() )
                   .add("regionName", data.getRegion()== null ? "":data.getRegion().getRegionName() )
                   .add("regionId", data.getRegion()== null ? "":data.getRegion().getId());

           dataArray.add(object);
       }

         JsonObjectBuilder result =  Json.createObjectBuilder()
             .add("success", true)
             .add("statusCode", 200)
             .add("data", dataArray);
                 
         return Response.status(Response.Status.OK).entity(result.build()).build(); 
    }
}
