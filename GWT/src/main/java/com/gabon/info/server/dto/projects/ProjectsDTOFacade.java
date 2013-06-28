package com.gabon.info.server.dto.projects;

import java.util.Set;

import org.dynadto.DTO;
import com.gabon.info.server.model.users.Users;


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

public interface ProjectsDTOFacade extends DTO {

	// Property accessors
	
	Long getId();

	void setId(Long id);

	String getProject();

	void setProject(String project);
	
	Set<Users> getUsers();

	void setUsers(Set<Users> users);
}