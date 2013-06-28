package com.gabon.info.server.dto.projects;

import java.util.HashSet;
import java.util.Set;

import com.gabon.info.server.dto.AbstractDTO;
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

public abstract class AbstractProjectsDTO<T extends DTO> extends AbstractDTO<T> {

	private static final long serialVersionUID = 6806905977208917889L;

	
	// Fields
	
	protected String project;
	
	protected Set<Users> users;

	

	// Constructors

	/** empty constructor */
	public AbstractProjectsDTO() {
		super();
		
		this.users = new HashSet<Users>(0);
	}

	/** full constructor */
	public AbstractProjectsDTO(Long id, String project, Set<Users> users) {
		this.id = id;
		this.project = project;
		this.users = users;
	}
	
	/** default constructor */
	public AbstractProjectsDTO(Long id, String project) {
		this.id = id;
		this.project = project;
	}
	

	
	// Property accessors
	
	public String getProject() {
		return this.project;
	}

	public void setProject(String project) {
		this.project = project;
	}
	
	public Set<Users> getUsers() {
		return this.users;
	}

	public void setUsers(Set<Users> users) {
		this.users = users;
	}
}