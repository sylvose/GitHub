package com.gabon.info.model.projects;

import static com.gabon.info.util.Constants.AbstractProjects;
import static com.gabon.info.util.Constants.ID;
import static com.gabon.info.util.Constants.PROJECT;
import static com.gabon.info.util.Constants.USERS;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


// import javax.persistence.CascadeType;

import com.gabon.info.model.AbstractModel;
import com.gabon.info.model.users.Users;


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

@MappedSuperclass

@XmlRootElement(name = AbstractProjects)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = AbstractProjects, propOrder = {ID, PROJECT, USERS})
public abstract class AbstractProjects extends AbstractModel {

	private static final long serialVersionUID = -5628267528912102244L;

	
	// Fields
	
	@XmlElement(required = true)
	protected Long id;
	
	@XmlElement(required = true)
	protected String project;
	
	@XmlElement(required = true)
	protected Set<Users> users;

	
	
	// Constructors

	/** empty constructor */
	public AbstractProjects() {
		super();
		
		this.users = new HashSet<Users>(0);
		this.version = 0;
	}

	/** full constructor */
	public AbstractProjects(Long id, String project, Set<Users> users) {
		this.id = id;
		this.project = project;
		this.users = users;
	}
	
	/** default constructor */
	public AbstractProjects(Long id, String project) {
		this.id = id;
		this.project = project;
	}
	
	
	// Property accessors
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = PROJECTS_SEQ_GEN)
	@Column(name = ID, unique = true, nullable = false, insertable = false, updatable = false, precision = 10, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
		this.version++;
	}

	@Column(name = PROJECT, nullable = false, insertable = true, updatable = true)
	public String getProject() {
		return this.project;
	}

	public void setProject(String project) {
		this.project = project;
	}
	
	@ManyToMany(targetEntity = Users.class, fetch = FetchType.LAZY, mappedBy = "projects")
	// @ManyToMany(targetEntity = Users.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "projects")
	public Set<Users> getUsers() {
		return this.users;
	}

	public void setUsers(Set<Users> users) {
		this.users = users;
	}
}