package com.gabon.info.server.model.roles;

import static com.gabon.info.server.util.Constants.AbstractRoles;
import static com.gabon.info.server.util.Constants.ID;
import static com.gabon.info.server.util.Constants.ROLE;
import static com.gabon.info.server.util.Constants.USERS;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.gabon.info.server.model.AbstractModel;
import com.gabon.info.server.model.users.Users;

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

@MappedSuperclass

@XmlRootElement(name = AbstractRoles)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = AbstractRoles, propOrder = {ID, USERS, ROLE})
public abstract class AbstractRoles extends AbstractModel {

	private static final long serialVersionUID = 2419898179162117026L;

	
	// Fields
	
	@XmlElement(required = true)
	protected Long id;
	
	@XmlElement(required = true)
	protected Users users;
	
	@XmlElement(required = true)
	protected String role;
	
	
	// Constructors

	/** empty constructor */
	public AbstractRoles() {
		super();
		
		this.version = 0;
	}

	/** light constructor */
	public AbstractRoles(String role) {
		this.role = role;
	}
	
	/** full constructor */
	public AbstractRoles(Long id, String role, Users users) {
		this.id = id;
		this.role = role;
		this.users = users;
	}

	/** full constructor */
	public AbstractRoles(Long id, String role) {
		this.id = id;
		this.role = role;
	}
	
	
	// Property accessors
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ROLES_SEQ_GEN)
	@Column(name = ID, unique = true, nullable = false, insertable = false, updatable = false, precision = 10, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
		this.version++;
	}

	@ManyToOne(targetEntity = Users.class, fetch = FetchType.LAZY)
	// @ManyToOne(targetEntity = Users.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = USERSID, referencedColumnName = ID)
	// @OnDelete(action = OnDeleteAction.CASCADE)
	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@Column(name = ROLE, nullable = false, insertable = true, updatable = true)
	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}