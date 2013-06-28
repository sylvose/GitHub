package com.gabon.info.server.dto.users;

import java.util.Set;

import javax.annotation.concurrent.Immutable;

import org.dynadto.DTO;

import com.gabon.info.server.model.department.Department;
import com.gabon.info.server.model.office.Office;
import com.gabon.info.server.model.projects.Projects;
import com.gabon.info.server.model.roles.Roles;

/*
 * This class is a dto class.
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

@Immutable
public final class UsersDTO<T extends DTO> extends AbstractUsersDTO<T> implements UsersDTOFacade {
	
	private static final long serialVersionUID = 4430518091234490337L;
	
	
	// Constructors

	/** empty constructor */
	public UsersDTO() {
		super();
	}
	
	/** minimal constructor */
	public UsersDTO(Long id, String name, Office office, Department department) {
		super(id, name, office, department);
	}

	/** light constructor */
	public UsersDTO(Long id, String name, Office office, Department department, Set<Roles> roles) {
		super(id, name, office, department, roles);
	}
	
	/** light constructor */
	public UsersDTO(Long id, String name, Department department, Office office, Set<Projects> projects) {
		super(id, name, department, office, projects);
	}
	
	/** full constructor */
	public UsersDTO(Long id, String name, String email, Office office, Department department, Set<Roles> roles, Set<Projects> projects) {
		super(id, name, email, office, department, roles, projects);
	}

	/** default constructor */
	public UsersDTO(Long id, String name, String email, Office office, Department department) {
		super(id, name, email, office, department);
	}
}