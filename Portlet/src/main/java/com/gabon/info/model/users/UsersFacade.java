package com.gabon.info.model.users;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.gabon.info.model.department.Department;
import com.gabon.info.model.office.Office;
import com.gabon.info.model.projects.Projects;
import com.gabon.info.model.roles.Roles;
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
 * AbstractUsers entity provides the base persistence definition of the Users
 */

public interface UsersFacade extends Constants {

	// Property accessors
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = USERS_SEQ_GEN)
    @Column(name = ID, unique = true, nullable = false, insertable = false, updatable = false, precision = 10, scale = 0)
	public Long getId();

	public void setId(Long id);

	@Column(name = NAME, nullable = false, insertable = true, updatable = true)
	public String getName();

	public void setName(String name);

	@Column(name = EMAIL, insertable = true, updatable = true)
	public String getEmail();

	public void setEmail(String email);
	
	
    @OneToOne(targetEntity = Office.class, fetch = FetchType.LAZY)
    // @OneToOne(targetEntity = Office.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = OFFICEID, referencedColumnName = ID)
    // @OnDelete(action = OnDeleteAction.CASCADE)
	public Office getOffice();

	public void setOffice(Office office);
	
	@ManyToOne(targetEntity = Department.class, fetch = FetchType.LAZY)
	// @ManyToOne(targetEntity = Department.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = DEPARTMENTID, referencedColumnName = ID)
	// @OnDelete(action = OnDeleteAction.CASCADE)
	public Department getDepartment();

	public void setDepartment(Department department);
	
	@OneToMany(targetEntity = Roles.class, fetch = FetchType.LAZY, mappedBy = "users")
	// @OneToMany(targetEntity = Roles.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "users")
	// @OnDelete(action = OnDeleteAction.CASCADE)
	public Set<Roles> getRoles();

	public void setRoles(Set<Roles> roles);
	
	@ManyToMany(targetEntity = Projects.class, fetch = FetchType.LAZY)
	// @ManyToMany(targetEntity = Projects.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = T_USERS_PROJECTS, schema = UQAM, 
	    joinColumns = @JoinColumn(name = USERSID, referencedColumnName = ID),
    	inverseJoinColumns = @JoinColumn(name = PROJECTSID, referencedColumnName = ID) 
	)
	public Set<Projects> getProjects();
	
	public void setProjects(Set<Projects> projects);
	
	public void addRoles(final Roles roles);
	
	public void addRoles(final Set<Roles> rolesList);
	
	public void addProjects(final Projects projects);
	
	public void addProjects(final Set<Projects> projectsList);
	
	public void assignUsersToProjects(final Long usersId, final Long projectsId);
}