package com.gabon.info.dto.users;

import java.util.Set;

import org.dynadto.DTO;

import com.gabon.info.model.department.Department;
import com.gabon.info.model.office.Office;
import com.gabon.info.model.projects.Projects;
import com.gabon.info.model.roles.Roles;

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

public interface UsersDTOFacade extends DTO {

	// Property accessors
	
	Long getId();

	void setId(Long id);

	String getName();

	void setName(String name);
	
	String getEmail();

	void setEmail(String email);

	Office getOffice();

	void setOffice(Office office);
	
	Set<Roles> getRoles();

	void setRoles(Set<Roles> roles);
	
	Department getDepartment();

	void setDepartment(Department department);
	
	Set<Projects> getProjects();
	
	void setProjects(Set<Projects> projects);
}