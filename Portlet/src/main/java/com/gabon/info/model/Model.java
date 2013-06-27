package com.gabon.info.model;

import java.io.Serializable;
import java.util.Date;

import com.gabon.info.util.Constants;

/*
 * This class is a abstract model class.
 * 
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
 * Model entity provides the methods for the base persistence definition of the Models
 */

public interface Model extends Constants, Serializable {

	Date getLastModified();

	void setLastModified(Date lastModified);

	long getVersion();

	void setVersion(long version);

	Date getVersion(Model model);
	
	Long getId();

	void setId(Long id);

	
	@Override
	boolean equals(Object object);

	@Override
	int hashCode();

	@Override
	String toString();
}