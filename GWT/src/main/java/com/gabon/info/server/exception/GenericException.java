package com.gabon.info.server.exception;

import java.util.ArrayList;
import java.util.List;

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

public class GenericException extends Exception {
	
	private static final long serialVersionUID = -6281728496852102251L;

	
	private String[] errorArgs;
	
	private List<Throwable> allGenericsExceptions;

	private long errorNumber;
	
	
	public GenericException() {
		if (null == allGenericsExceptions) {
			allGenericsExceptions = new ArrayList<Throwable>();
		}
	}

	public GenericException(long en) {
		super();
		this.errorNumber = en;
	}
	
	public GenericException(long en, String[] ea) {
		super();
		this.errorNumber = en;
		this.errorArgs = ea;
	}

	public GenericException(String msg, Throwable t) {
		super(msg, t);
		
		if(t instanceof GenericException) {
			this.setErrorNumber(((GenericException)t).getErrorNumber());
			this.setErrorArgs(((GenericException)t).getErrorArgs());
		}
	}

	public GenericException(long en, String msg, Throwable t) {
		super(msg, t);
		this.errorNumber = en;
	}

	public GenericException(long en, String[] ea, String msg, Throwable t) {
		super(msg, t);
		this.errorNumber = en;
		this.errorArgs = ea;
	}
	
	
	public GenericException(String msg) {
		super(msg);
	}
	
	public GenericException(long en, String msg) {
		super(msg);
		this.errorNumber = en;
	}

	public GenericException(long en, String[] ea, String msg) {
		super(msg);
		this.errorNumber = en;
		this.errorArgs = ea;
	}

	public GenericException(Throwable t) {
		this();
		
		if(t instanceof GenericException) {
			this.setErrorNumber(((GenericException)t).getErrorNumber());
			this.setErrorArgs(((GenericException)t).getErrorArgs());
		}
	}

	public GenericException(long en, Throwable t) {
		super(t);
		this.errorNumber = en;
	}
	
	public GenericException(long en, String[] ea, Throwable t) {
		super(t);
		this.errorNumber = en;
		this.errorArgs = ea;
	}

	public long getErrorNumber() {
		return errorNumber;
	}

	public void setErrorNumber(long en) {
		errorNumber = en;
	}

	public String[] getErrorArgs() {
		return errorArgs;
	}

	public void setErrorArgs(String[] ea) {
		errorArgs = ea;
	}
	
	public List<Throwable> getAllGenericsExceptions() {
		return allGenericsExceptions;
	}
	
	public void setAllGenericsExceptions(List<Throwable> allGenericsExceptions) {
		this.allGenericsExceptions = allGenericsExceptions;
	}
	
	public void addGenericException(GenericException ex) {
		allGenericsExceptions.add(ex);
	}
	
	public boolean isGenericsExceptions() {
		return (null != allGenericsExceptions && !allGenericsExceptions.isEmpty());
	}
}