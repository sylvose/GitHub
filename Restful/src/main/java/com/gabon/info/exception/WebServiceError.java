package com.gabon.info.exception;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

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

@XmlRootElement(name="WebServiceError")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WebServiceError", propOrder = {"errorCode", "errorMessage", "details"})
public class WebServiceError {
	
	@XmlElement(required = true)
	private int errorCode;
	
	@XmlElement(required = true)
	private String errorMessage;
	
	@XmlElement(required = true)
	private String details;
	

	public WebServiceError() {
	}
	
	/**
	 * @param errorCode
	 * @param errorMessage
	 * @param details
	 */
	public WebServiceError(int errorCode, String errorMessage, String details) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.details = details;
	}
	
	public int getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String getDetails() {
		return details;
	}
	
	public void setDetails(String details) {
		this.details = details;
	}
	
	
	@Override
	public String toString() {
	      final StringBuilder stringBuilder = new StringBuilder();
	      
	      stringBuilder.append("WebServiceError");
	      stringBuilder.append("{errorCode=").append(errorCode);
	      stringBuilder.append(", details='").append(details).append('\'');
	      stringBuilder.append(", details='").append(details).append('\'');
	      stringBuilder.append('}');
	      
	      return stringBuilder.toString();
	  }
}