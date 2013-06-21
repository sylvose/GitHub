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

public class NotUpdateException extends WebApplicationException implements ExceptionMapper<WebApplicationException> {

	private static final long serialVersionUID = 5305842479404025529L;

	/**
	 * Create a HTTP 400 (Bad Request) exception.
	 */
	public NotUpdateException() {
		super(Responses.notModified().build());
	}

	/**
	 * Create a HTTP 400 (Bad Request) exception.
	 * 
	 * @param message
	 *            the String that is the entity of the 400 response.
	 */
	public NotUpdateException(String message) {
		super(Response.status(Status.BAD_REQUEST).entity(message).type(MediaType.TEXT_PLAIN).build());
	}
	
	public Response toResponse(NotUpdateException e) {
		return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
	}
	
	public Response toResponse(WebApplicationException exception) {
		return exception.getResponse();
	}
}