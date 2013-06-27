package com.gabon.info.response;

import java.util.Arrays;

import static com.gabon.info.util.Constants.MISSING_QP;
import static com.gabon.info.util.Constants.ID_NOT_FOUND;
import static com.gabon.info.util.Constants.RESOURCE_NOT_FOUND;
import static com.gabon.info.util.Constants.INVALID_DATA;
import static com.gabon.info.util.Constants.ERROR_SAVING;
import static com.gabon.info.util.Constants.ERROR_DELETING;
import static com.gabon.info.util.Constants.ERROR_DESACTIVATING;
import static com.gabon.info.util.Constants.INVALID_VALUE;

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

public class MessagesResponse {

	
	public static String MissingQueryParameter(String queryParam) {
		return String.format(MISSING_QP, queryParam);
	}
	
	public static String IdNotFound(Object id) {
		return String.format(ID_NOT_FOUND, id);
	}
	
	public static String ResourceNotFound(String dtoName, Object id) {
		return String.format(RESOURCE_NOT_FOUND, dtoName, id);
	}
	
	public static String InvalidData(String... mandatoryProperties) {
		return String.format(INVALID_DATA, Arrays.toString(mandatoryProperties));
	}
	
	public static String ErrorSaving(String dtoName) {
		return String.format(ERROR_SAVING, dtoName);
	}
	
	public static String ErrorDeleting(String dtoName) {
		return String.format(ERROR_DELETING, dtoName);
	}
	
	public static String ErrorDesactivating(String dtoName) {
		return String.format(ERROR_DESACTIVATING, dtoName);
	}
	
	public static String InvalidValue(String property, Object[] possibleValues) {
		return String.format(INVALID_VALUE, property, Arrays.toString(possibleValues));
	}
}