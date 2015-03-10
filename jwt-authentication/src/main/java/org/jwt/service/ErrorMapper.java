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

// TODO move to org.jwt
@Provider
@Slf4j
public class ErrorMapper implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception exception) {
		return createResponse(createErrorMessage(exception));
	}

	private Response createResponse(ErrorMessage errorMessage) {
		return Response
				.status(errorMessage.getStatus())
				.entity(errorMessage.getMessage())
				.type(MediaType.TEXT_PLAIN)
				.header(errorMessage.getHeaderName(),
						errorMessage.getHeaderValue()).build();
	}

	private ErrorMessage createErrorMessage(Exception exception) {
		ErrorMessage errorMessage = new ErrorMessage();
		if (exception instanceof WebApplicationException) {
			errorMessage.setStatus(((WebApplicationException) exception)
					.getResponse().getStatus());
			errorMessage.setMessage(exception.getMessage());
		} else if (exception instanceof AuthenticationException || exception instanceof ServletException) {
			errorMessage.setStatus(Status.UNAUTHORIZED.getStatusCode());
			errorMessage.setMessage(exception.getMessage());
			errorMessage.setHeaderName("WWW-Authenticate");
			errorMessage.setHeaderValue("JWT");
		} else {
			errorMessage.setStatus(Response.Status.INTERNAL_SERVER_ERROR
					.getStatusCode());
			errorMessage.setMessage(exception.getMessage());
		}
		log.debug(errorMessage.toString());
		return errorMessage;
	}
}
