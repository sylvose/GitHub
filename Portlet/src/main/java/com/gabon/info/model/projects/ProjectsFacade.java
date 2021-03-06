package com.gabon.info.model.projects;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.gabon.info.model.users.Users;
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
 * AbstractProject entity provides the base persistence definition of the Project entity.
 */

public interface ProjectsFacade extends Constants {

	
	// Property accessors
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = PROJECTS_SEQ_GEN)
	@Column(name = ID, unique = true, nullable = false, insertable = false, updatable = false, precision = 10, scale = 0)
	public Long getId();

	public void setId(Long id);

	@Column(name = PROJECT, nullable = false, insertable = true, updatable = true)
	public String getProject();

	public void setProject(String project);
	
	@ManyToMany(targetEntity = Users.class, fetch = FetchType.LAZY, mappedBy = "projects")
	// @ManyToMany(targetEntity = Users.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "projects")
	public Set<Users> getUsers();

	public void setUsers(Set<Users> users);
	
	public void addUsers(final Users users);
	
	public void addUsers(final Set<Users> usersList);
	
	public void assignProjectsToUsers(final Long projectsId, final Long usersId);
}