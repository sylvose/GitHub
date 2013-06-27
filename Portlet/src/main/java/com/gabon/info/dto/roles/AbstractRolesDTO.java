package com.gabon.info.dto.roles;

import com.gabon.info.dto.AbstractDTO;
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
 * AbstractRole entity provides the base persistence definition of the Roles entity.
 */

public abstract class AbstractRolesDTO<T extends DTO> extends AbstractDTO<T> {

	private static final long serialVersionUID = 1075136943814312878L;

	
	// Fields
	
	protected Users users;
	
	protected String role;
	
	
	
	// Constructors

	/** empty constructor */
	public AbstractRolesDTO() {
		super();
	}

	/** light constructor */
	public AbstractRolesDTO(String role) {
		this.role = role;
	}
	
	/** full constructor */
	public AbstractRolesDTO(Long id, String role, Users users) {
		this.id = id;
		this.role = role;
		this.users = users;
	}

	/** full constructor */
	public AbstractRolesDTO(Long id, String role) {
		this.id = id;
		this.role = role;
	}

	
	
	// Property accessors

	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}