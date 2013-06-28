package com.gabon.info.server.dto.department;

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

public abstract class AbstractDepartmentDTO<T extends DTO> extends AbstractDTO<T> {

	private static final long serialVersionUID = 3363194928066647223L;

	
	// Fields

	protected String department;
	
	protected Set<Users> users;

	
	
	// Constructors

	/** empty constructor */
	public AbstractDepartmentDTO() {
		super();
		
		this.users = new HashSet<Users>(0);
	}

	/** full constructor */
	public AbstractDepartmentDTO(Long id, String department, Set<Users> users) {
		this.id = id;
		this.department = department;
		this.users = users;
	}
	
	/** default constructor */
	public AbstractDepartmentDTO(Long id, String department) {
		this.id = id;
		this.department = department;
	}
	
	

	// Property accessors
	
	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Set<Users> getUsers() {
		return this.users;
	}

	public void setUsers(Set<Users> users) {
		this.users = users;
	}
}