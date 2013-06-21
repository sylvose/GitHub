package com.gabon.info.enums.users;

import java.io.Serializable;

import com.gabon.info.model.users.Users;

/*
 * This class is a domain class.
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
 */

public interface UsersType extends Serializable {

	/**
	 * @return the name
	 */
	public String getEnumValue();

	/**
	 * @param name
	 *            the name to set
	 */
	public void setEnumValue(String name);

	/**
	 * @param user
	 *            the user to set
	 */
	public void setClazz(Users user);

	/**
	 * @return the UserEnum
	 */
	public UsersEnum getEnumValue(String name);

	public String toStringBuffer();
}