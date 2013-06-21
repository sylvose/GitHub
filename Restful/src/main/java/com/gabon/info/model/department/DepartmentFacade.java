package com.gabon.info.model.department;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.gabon.info.model.users.Users;
import com.gabon.info.util.RestFulConstants;


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
 * AbstractDepartment entity provides the base persistence definition of the Department entity.
 */

public interface DepartmentFacade extends RestFulConstants {

	
	// Property accessors
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = DEPARTMENT_SEQ_GEN)
	@Column(name = ID, unique = true, nullable = false, insertable = false, updatable = false, precision = 10, scale = 0)
	public Long getId();

	public void setId(Long id);

	@Column(name = DEPARTMENT, nullable = false, insertable = true, updatable = true)
	public String getDepartment();

	public void setDepartment(String department);

	@OneToMany(targetEntity = Users.class, fetch = FetchType.LAZY, mappedBy = "department")
	// @OneToMany(targetEntity = Users.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "department")
	// @OnDelete(action = OnDeleteAction.CASCADE)
	public Set<Users> getUsers();

	public void setUsers(Set<Users> users);
	
	
	public void addUsers(final Users users);
	
	public void addUsers(final Set<Users> usersList);
}