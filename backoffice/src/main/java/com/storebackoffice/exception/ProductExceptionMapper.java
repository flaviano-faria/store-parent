package com.storebackoffice.exception;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@Provider
public class ProductExceptionMapper implements ExceptionMapper<ProductException> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(ProductException exception) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("status", exception.getStatus());
        error.put("code", exception.getCode());
        error.put("message", exception.getMessage());
        error.put("path", uriInfo != null ? uriInfo.getPath() : null);
        error.put("timestamp", Instant.now().toString());

        return Response.status(exception.getStatus())
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(error)
                .build();
    }
}
