package com.gabon.info.response;

import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import com.gabon.info.exception.WebServiceError;

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

public class CreatedResponse extends Response {


	private int status;
	
	private String resourceUrl;
	
	private WebServiceError webServiceError;
	
	
	public CreatedResponse() {
		super();
		
		final WebServiceError webServiceError = new WebServiceError();
		webServiceError.setErrorCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		webServiceError.setErrorMessage("Unknown server error");
	}
	
	public CreatedResponse(int status) {
		this();
		
		this.status = status;
	}
	
	public CreatedResponse(String resourceUrl) {
		this();
		
		this.resourceUrl = resourceUrl;
	}
	
	public CreatedResponse(WebServiceError webServiceError) {
		this();
		
		this.webServiceError = webServiceError;
	}
	
	public CreatedResponse(int status, String resourceUrl) {
		this();
		
		this.status = status;
		this.resourceUrl = resourceUrl;
	}
	
	public CreatedResponse(int status, WebServiceError webServiceError) {
		this();
		
		this.status = status;
		this.webServiceError = webServiceError;
	}
	
	public CreatedResponse(String resourceUrl, WebServiceError webServiceError) {
		this();
		
		this.resourceUrl = resourceUrl;
		this.webServiceError = webServiceError;
	}
	
	public CreatedResponse(int status, String resourceUrl, WebServiceError webServiceError) {
		
		this();
		
		this.status = status;
		this.resourceUrl = resourceUrl;
		this.webServiceError = webServiceError;
	}

	public WebServiceError getWebServiceError() {
		return this.webServiceError;
	}
	
	public void setWebServiceError(WebServiceError webServiceError) {
		this.webServiceError = webServiceError;
	}
	
	public int getStatus() {
		this.status = HttpServletResponse.SC_CREATED;
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the resourceUrl
	 */
	public String getResourceUrl() {
		return resourceUrl;
	}

	/**
	 * @param resourceUrl the resourceUrl to set
	 */
	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public static CreatedResponse NotFoundException(String errorMessage) {
		final WebServiceError webServiceError = new WebServiceError();
		webServiceError.setErrorCode(HttpServletResponse.SC_NOT_FOUND);
		webServiceError.setErrorMessage(errorMessage);
		
		return new CreatedResponse(HttpServletResponse.SC_NOT_FOUND, webServiceError);
	}
	
	public static CreatedResponse BadRequestException(String errorMessage) {
		final WebServiceError webServiceError = new WebServiceError();
		webServiceError.setErrorCode(HttpServletResponse.SC_BAD_REQUEST);
		webServiceError.setErrorMessage(errorMessage);
		
		return new CreatedResponse(HttpServletResponse.SC_BAD_REQUEST, webServiceError);
	}
	
	public static CreatedResponse ServerErrorException(String errorMessage) {
		final WebServiceError webServiceError = new WebServiceError();
		webServiceError.setErrorCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		webServiceError.setErrorMessage(errorMessage);
		
		return new CreatedResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, webServiceError);
	}
	
	public StatusType getStatusInfo() {
		
		return null;
	}
	
	
	public boolean hasEntity() {
		
		return false;
	}

	
	public boolean bufferEntity() {
		
		return false;
	}

	
	public void close() {
		
		
	}

	
	public MediaType getMediaType() {
		
		return null;
	}

	
	public Locale getLanguage() {
		
		return null;
	}

	
	public int getLength() {
		
		return 0;
	}

	
	public Set<String> getAllowedMethods() {
		
		return null;
	}

	
	public Map<String, NewCookie> getCookies() {
		
		return null;
	}

	
	public EntityTag getEntityTag() {
		
		return null;
	}

	
	public Date getDate() {
		
		return null;
	}

	
	public Date getLastModified() {
		
		return null;
	}

	
	public URI getLocation() {
		
		return null;
	}
	
	public boolean hasLink(String relation) {
		
		return false;
	}
	
	public MultivaluedMap<String, String> getStringHeaders() {
		
		return null;
	}

	
	public String getHeaderString(String name) {
		
		return null;
	}

	public MultivaluedMap<String, Object> getMetadata() {
		
		return null;
	}

	public Object getEntity() {
		
		return null;
	}

	public <T> T readEntity(Class<T> entityType) {
		
		return null;
	}

	public <T> T readEntity(Class<T> entityType, Annotation[] annotations) {
		
		return null;
	}
}