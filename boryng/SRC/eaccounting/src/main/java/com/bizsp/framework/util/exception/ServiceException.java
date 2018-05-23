package com.bizsp.framework.util.exception;

import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ServiceException extends WebApplicationException {

	private static final long serialVersionUID = -3556380730185914213L;

	public ServiceException(Throwable throwable) {
		super(Response.status(200).entity(throwable).type(MediaType.APPLICATION_JSON_TYPE).build());
	}

	public ServiceException(Map<String, String> map) {
		super(Response.status(200).entity(map).type(MediaType.APPLICATION_JSON_TYPE).build());
	}
}