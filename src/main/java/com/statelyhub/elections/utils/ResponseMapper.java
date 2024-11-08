
package com.statelyhub.elections.utils;

import com.stately.common.data.ProcResponse;
import com.stately.common.data.ErrorModel;
import com.stately.modules.api.ApiResponse;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author Edwin
 */
public class ResponseMapper {
    
    public static ApiResponse toApiResponse(ProcResponse response)
    {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setSuccess(response.isSuccess());
        apiResponse.setStatusCode(response.getStatusCode());
        apiResponse.setMessage(response.getMessage());
        apiResponse.setData(response.getData());
        apiResponse.setMeta(response.getMeta());
        
        if(response.hasErrors())
        {
             apiResponse.setErrors(response.getErrors());
             apiResponse.setStatusCode(400);
             
             if(apiResponse.getMessage() == null || apiResponse.getMessage().isEmpty())
             {
                 ErrorModel errorModel = (ErrorModel) response.getErrors().get(0);
                  apiResponse.setMessage(errorModel.getMessage());
             }
        }
//        
//        return ApiResponse.error(((ProcResponse.Message)(response.getErrors().get(0))).getMsg());
//        
        return apiResponse;
    }
    
    public static Response toResponse(ProcResponse response)
    {
        return toApiResponse(response).toResponse();
    }
}
