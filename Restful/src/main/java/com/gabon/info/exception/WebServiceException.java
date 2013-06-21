package com.gabon.info.exception;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;

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

public class WebServiceException extends Exception {

	private static final long serialVersionUID = 2708255797609531482L;

	
	private ClientResponse clientResponse;
	
	private int statusCode;
	
	private String errorMessage;
	
	private Throwable throwable;
	
	

	public WebServiceException() {
		super();
	}
	
	public WebServiceException(ClientResponse clientResponse) {
		this.clientResponse = clientResponse;
	}
	
	public WebServiceException(int statusCode) {
		this();
		
		this.statusCode = statusCode;
	}
	
	public WebServiceException(String errorMessage) {
		super(errorMessage);
		
		this.errorMessage = errorMessage;
	}
	
	public WebServiceException(Throwable throwable) {
		super(throwable);
		
		this.throwable = throwable;
	}
	
	public WebServiceException(ClientResponse clientResponse, int statusCode) {
		this();
		
		this.clientResponse = clientResponse;
		this.statusCode = clientResponse.getStatus();
		
		if(this.statusCode != 204) {
			try {
				if(clientResponse != null) {
					final WebServiceError webServiceError = clientResponse.getEntity(WebServiceError.class);
					this.errorMessage = webServiceError.getErrorMessage();
				}
			} catch (ClientHandlerException e) {
				this.statusCode = statusCode;
			} 
		}		
	}
	
	public WebServiceException(ClientResponse clientResponse, String errorMessage) {
		super(errorMessage);
		
		this.clientResponse = clientResponse;
		this.errorMessage = errorMessage;
		
		if(clientResponse.getStatus() != 204) {
			try {
				if(clientResponse != null) {
					final WebServiceError webServiceError = clientResponse.getEntity(WebServiceError.class);
					this.errorMessage = webServiceError.getErrorMessage();
				}
			} catch (ClientHandlerException e) {
				this.errorMessage = e.getMessage();
			} 
		}		
	}
	
	public WebServiceException(ClientResponse clientResponse, Throwable throwable) {
		super(throwable);
		
		this.clientResponse = clientResponse;
		this.throwable = throwable;
		
		if(clientResponse.getStatus() != 204) {
			try {
				if(clientResponse != null) {
					final WebServiceError webServiceError = clientResponse.getEntity(WebServiceError.class);
					this.errorMessage = webServiceError.getErrorMessage();
				}
			} catch (ClientHandlerException e) {
				this.errorMessage = e.getMessage();
			} 
		}		
	}
	
	public WebServiceException(int statusCode, String errorMessage) {
		super(errorMessage);
		
		this.statusCode = statusCode;
		this.errorMessage = errorMessage;
		
		if(statusCode != 204) {
			try {
				if(clientResponse != null) {
					final WebServiceError webServiceError = clientResponse.getEntity(WebServiceError.class);
					this.errorMessage = webServiceError.getErrorMessage();
				}
			} catch (ClientHandlerException e) {
				this.errorMessage = e.getMessage();
			} 
		}		
	}
	
	public WebServiceException(int statusCode, Throwable throwable) {
		super(throwable);
		
		this.statusCode = statusCode;
		this.throwable = throwable;
		
		if(statusCode != 204) {
			try {
				if(clientResponse != null) {
					final WebServiceError webServiceError = clientResponse.getEntity(WebServiceError.class);
					this.errorMessage = webServiceError.getErrorMessage();
				}
			} catch (ClientHandlerException e) {
				this.errorMessage = e.getMessage();
			} 
		}		
	}
	
	public WebServiceException(String errorMessage, Throwable throwable) {
		super(errorMessage, throwable);
		
		this.errorMessage = errorMessage;
		this.throwable = throwable;
	}
	
	public WebServiceException(ClientResponse clientResponse, int statusCode, String errorMessage) {
		this();
		
		this.clientResponse = clientResponse;
		this.statusCode = clientResponse.getStatus();
		this.errorMessage = errorMessage;
		
		if(this.statusCode != 204) {
			try {
				if(clientResponse != null) {
					final WebServiceError webServiceError = clientResponse.getEntity(WebServiceError.class);
					this.errorMessage = webServiceError.getErrorMessage();
				}
			} catch (ClientHandlerException e) {
				this.statusCode = statusCode;
				this.errorMessage = e.getMessage();
			} 
		}		
	}
	
	public WebServiceException(ClientResponse clientResponse, int statusCode, Throwable throwable) {
		super(throwable);
		
		this.clientResponse = clientResponse;
		this.statusCode = clientResponse.getStatus();
		this.throwable = throwable;
		
		if(this.statusCode != 204) {
			try {
				if(clientResponse != null) {
					final WebServiceError webServiceError = clientResponse.getEntity(WebServiceError.class);
					this.errorMessage = webServiceError.getErrorMessage();
				}
			} catch (ClientHandlerException e) {
				this.statusCode = statusCode;
				this.errorMessage = e.getMessage();
			} 
		}		
	}
	
	public WebServiceException(ClientResponse clientResponse, String errorMessage, Throwable throwable) {
		super(throwable);
		
		this.clientResponse = clientResponse;
		this.errorMessage = errorMessage;
		this.throwable = throwable;
		
		if(clientResponse.getStatus() != 204) {
			try {
				if(clientResponse != null) {
					final WebServiceError webServiceError = clientResponse.getEntity(WebServiceError.class);
					this.errorMessage = webServiceError.getErrorMessage();
				}
			} catch (ClientHandlerException e) {
				this.errorMessage = e.getMessage();
			} 
		}		
	}
	
	public WebServiceException(int statusCode, String errorMessage, Throwable throwable) {
		super(errorMessage, throwable);
		
		this.statusCode = statusCode;
		this.errorMessage = errorMessage;
		this.throwable = throwable;
		
		if(this.statusCode != 204) {
			try {
				if(clientResponse != null) {
					final WebServiceError webServiceError = clientResponse.getEntity(WebServiceError.class);
					this.errorMessage = webServiceError.getErrorMessage();
				}
			} catch (ClientHandlerException e) {
				this.statusCode = statusCode;
				this.errorMessage = e.getMessage();
			} 
		}		
	}
	
	public WebServiceException(ClientResponse clientResponse, int statusCode, String errorMessage, Throwable throwable) {
		super(errorMessage, throwable);
		
		this.clientResponse = clientResponse;
		this.statusCode = clientResponse.getStatus();
		this.errorMessage = errorMessage;
		this.throwable = throwable;
		
		if(this.statusCode != 204) {
			try {
				if(clientResponse != null) {
					final WebServiceError webServiceError = clientResponse.getEntity(WebServiceError.class);
					this.errorMessage = webServiceError.getErrorMessage();
				}
			} catch (ClientHandlerException e) {
				this.statusCode = statusCode;
				this.errorMessage = e.getMessage();
			} 
		}		
	}

	/**
	 * @return the clientResponse
	 */
	public ClientResponse getClientResponse() {
		return clientResponse;
	}

	/**
	 * @param clientResponse the clientResponse to set
	 */
	public void setClientResponse(ClientResponse clientResponse) {
		this.clientResponse = clientResponse;
	}

	/**
	 * @return the statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the throwable
	 */
	public Throwable getThrowable() {
		return throwable;
	}

	/**
	 * @param throwable the throwable to set
	 */
	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}
}