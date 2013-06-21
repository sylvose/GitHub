package com.gabon.info.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import com.sun.jersey.api.Responses;

/*
 * @author <a href="mailto:asylvose@yahoo.fr">Sylvose ALLOGO</a>
 *  
 * Copyright (C) 2013 Sylvose ALLOGO
 * 
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Tous droits reserves. 
 *    
 * Confidentiel
 * 
 */

public class NotSaveException extends WebApplicationException implements ExceptionMapper<WebApplicationException> {

	private static final long serialVersionUID = 7607598054494537440L;

	
	/**
	 * Create a HTTP 409 (Conflict) exception.
	 */
	public NotSaveException() {
		super(Responses.conflict().build());
	}

	/**
	 * Create a HTTP 409 (Conflict) exception.
	 * 
	 * @param message
	 *            the String that is the entity of the 409 response.
	 */
	public NotSaveException(String message) {
		super(Response.status(Status.CONFLICT).entity(message).type(MediaType.TEXT_PLAIN).build());
	}
	
	public Response toResponse(NotSaveException e) {
		return Response.status(Status.CONFLICT).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
	}
	
	public Response toResponse(WebApplicationException exception) {
		return exception.getResponse();
	}
}