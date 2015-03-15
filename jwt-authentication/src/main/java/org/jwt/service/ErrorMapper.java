package org.jwt.service;

import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import lombok.extern.slf4j.Slf4j;

/**
 * Jersey specific ExceptionMapper. Rest services can throw exceptions, and
 * mapper maps them to proper responses.
 */
@Provider
@Slf4j
public class ErrorMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        return createResponse(exception);
    }

    private Response createResponse(Exception exception) {
        if (exception instanceof WebApplicationException) {
            return Response.status(((WebApplicationException) exception).getResponse().getStatus())
                    .entity(exception.getMessage()).type(MediaType.TEXT_PLAIN).build();
        } else if (exception instanceof AuthenticationException || exception instanceof ServletException) {
            return Response.status(Status.UNAUTHORIZED.getStatusCode()).entity(exception.getMessage())
                    .type(MediaType.TEXT_PLAIN).header("WWW-Authenticate", "JWT").build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).entity(exception.getMessage())
                .type(MediaType.TEXT_PLAIN).build();
    }
}
