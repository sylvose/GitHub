package com.gabon.info.dto.office;

import org.dynadto.DTO;
import com.gabon.info.model.users.Users;

/*
 * This class is a abstract dto class.
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

public interface OfficeDTOFacade extends DTO {

	// Property accessors
	
	Long getId();

	void setId(Long id);

	Users getUsers();

	void setUsers(Users users);

	String getOffice();

	void setOffice(String office);
}