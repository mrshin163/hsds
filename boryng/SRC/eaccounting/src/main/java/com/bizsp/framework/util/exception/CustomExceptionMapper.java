package com.bizsp.framework.util.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

@Provider
@Component
public class CustomExceptionMapper implements ExceptionMapper<CustomException> {

	public CustomExceptionMapper() {
	}

	@Override
	public Response toResponse(CustomException customException) {
		return Response.status(Response.Status.BAD_REQUEST).entity(customException.getMessage()).type(MediaType.APPLICATION_JSON).build();
	}

}
