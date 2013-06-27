package com.gabon.info.model.roles;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
 * AbstractRole entity provides the base persistence definition of the Roles entity.
 */

public interface RolesFacade extends Constants {

	
	// Property accessors
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ROLES_SEQ_GEN)
	@Column(name = ID, unique = true, nullable = false, insertable = false, updatable = false, precision = 10, scale = 0)
	public Long getId();

	public void setId(Long id);

	@ManyToOne(targetEntity = Users.class, fetch = FetchType.LAZY)
	// @ManyToOne(targetEntity = Users.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = USERSID, referencedColumnName = ID)
	// @OnDelete(action = OnDeleteAction.CASCADE)
	public Users getUsers();

	public void setUsers(Users users);

	@Column(name = ROLE, nullable = false, insertable = true, updatable = true)
	public String getRole();

	public void setRole(String role);
}