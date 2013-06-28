package com.gabon.info.server.dto.users;

import java.util.HashSet;
import java.util.Set;

import com.gabon.info.server.dto.AbstractDTO;
import org.dynadto.DTO;
import com.gabon.info.server.model.department.Department;
import com.gabon.info.server.model.office.Office;
import com.gabon.info.server.model.projects.Projects;
import com.gabon.info.server.model.roles.Roles;

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

public abstract class AbstractUsersDTO<T extends DTO> extends AbstractDTO<T> {

	private static final long serialVersionUID = 6176980076800848273L;

	
	// Fields
	
	protected Office office;
	
	protected Department department;
	
	protected String name;
	
	protected String email;
	
	protected Set<Roles> roles;
	
	protected Set<Projects> projects;


	
	// Constructors

	/** empty constructor */
	public AbstractUsersDTO() {
		super();
		
		this.roles = new HashSet<Roles>(0);
		this.projects = new HashSet<Projects>(0);
	}

	/** minimal constructor */
	public AbstractUsersDTO(Long id, String name, Office office, Department department) {
		this.id = id;
		this.name = name;
		this.office = office;
		this.department = department;
	}

	/** light constructor */
	public AbstractUsersDTO(Long id, String name, Office office, Department department, Set<Roles> roles) {
		this.id = id;
		this.name = name;
		this.office = office;
		this.department = department;
		this.roles = roles;
	}
	
	/** light constructor */
	public AbstractUsersDTO(Long id, String name, Department department, Office office, Set<Projects> projects) {
		this.id = id;
		this.name = name;
		this.office = office;
		this.department = department;
		this.office = office;
		this.projects = projects;
	}
	
	/** full constructor */
	public AbstractUsersDTO(Long id, String name, String email, Office office, Department department, Set<Roles> roles, Set<Projects> projects) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.office = office;
		this.department = department;
		this.roles = roles;
		this.projects = projects;
	}

	/** default constructor */
	public AbstractUsersDTO(Long id, String name, String email, Office office, Department department) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.office = office;
		this.department = department;
	}

	
	// Property accessors
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Office getOffice() {
		return this.office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	public Set<Roles> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Roles> roles) {
		this.roles = roles;
	}

	public Department getDepartment() {
		return this.department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
	public Set<Projects> getProjects() {
		return this.projects;
	}
	
	public void setProjects(Set<Projects> projects) {
		this.projects = projects;
	}
}