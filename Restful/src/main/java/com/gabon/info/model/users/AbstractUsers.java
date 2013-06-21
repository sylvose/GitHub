package com.gabon.info.model.users;

import static com.gabon.info.util.RestFulConstants.AbstractUsers;
import static com.gabon.info.util.RestFulConstants.DEPARTMENT;
import static com.gabon.info.util.RestFulConstants.EMAIL;
import static com.gabon.info.util.RestFulConstants.ID;
import static com.gabon.info.util.RestFulConstants.NAME;
import static com.gabon.info.util.RestFulConstants.OFFICE;
import static com.gabon.info.util.RestFulConstants.PROJECTS;
import static com.gabon.info.util.RestFulConstants.ROLES;

import java.util.HashSet;
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
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.gabon.info.model.AbstractModel;
import com.gabon.info.model.department.Department;
import com.gabon.info.model.office.Office;
import com.gabon.info.model.projects.Projects;
import com.gabon.info.model.roles.Roles;

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

@MappedSuperclass

@XmlRootElement(name = AbstractUsers)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = AbstractUsers, propOrder = {ID, NAME, EMAIL, OFFICE, DEPARTMENT, ROLES, PROJECTS})
public abstract class AbstractUsers extends AbstractModel {

	private static final long serialVersionUID = 6850324126210931458L;

	
	// Fields
	
	@XmlElement(required = true)
	protected Long id;

	@XmlElement(required = true)
	protected String name;
	
	@XmlElement(required = true)
	protected String email;
	
	@XmlElement(required = true)
	protected Office office;
	
	@XmlElement(required = true)
	protected Department department;
	
	@XmlElement(required = true)
	protected Set<Roles> roles;
	
	@XmlElement(required = true)
	protected Set<Projects> projects;

	
	// Constructors

	/** empty constructor */
	public AbstractUsers() {
		super();
		
		this.roles = new HashSet<Roles>(0);
		this.projects = new HashSet<Projects>(0);
		this.version = 0;
	}

	/** minimal constructor */
	public AbstractUsers(Long id, String name, Office office, Department department) {
		this.id = id;
		this.name = name;
		this.office = office;
		this.department = department;
	}

	/** light constructor */
	public AbstractUsers(Long id, String name, Office office, Department department, Set<Roles> roles) {
		this.id = id;
		this.name = name;
		this.office = office;
		this.department = department;
		this.roles = roles;
	}
	
	/** light constructor */
	public AbstractUsers(Long id, String name, Department department, Office office, Set<Projects> projects) {
		this.id = id;
		this.name = name;
		this.office = office;
		this.department = department;
		this.office = office;
		this.projects = projects;
	}
	
	/** full constructor */
	public AbstractUsers(Long id, String name, String email, Office office, Department department, Set<Roles> roles, Set<Projects> projects) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.office = office;
		this.department = department;
		this.roles = roles;
		this.projects = projects;
	}

	/** default constructor */
	public AbstractUsers(Long id, String name, String email, Office office, Department department) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.office = office;
		this.department = department;
	}
	
	
	
	// Property accessors
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = USERS_SEQ_GEN)
    @Column(name = ID, unique = true, nullable = false, insertable = false, updatable = false, precision = 10, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
		this.version++;
	}

	@Column(name = NAME, nullable = false, insertable = true, updatable = true)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = EMAIL, insertable = true, updatable = true)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
    @OneToOne(targetEntity = Office.class, fetch = FetchType.LAZY)
    // @OneToOne(targetEntity = Office.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = OFFICEID, referencedColumnName = ID)
    // @OnDelete(action = OnDeleteAction.CASCADE)
	public Office getOffice() {
		return this.office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	@ManyToOne(targetEntity = Department.class, fetch = FetchType.LAZY)
	// @ManyToOne(targetEntity = Department.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = DEPARTMENTID, referencedColumnName = ID)
	// @OnDelete(action = OnDeleteAction.CASCADE)
	public Department getDepartment() {
		return this.department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
	@OneToMany(targetEntity = Roles.class, fetch = FetchType.LAZY, mappedBy = "users")
	// @OneToMany(targetEntity = Roles.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "users")
	// @OnDelete(action = OnDeleteAction.CASCADE)
	public Set<Roles> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Roles> roles) {
		this.roles = roles;
	}
	
	@ManyToMany(targetEntity = Projects.class, fetch = FetchType.LAZY)
	// @ManyToMany(targetEntity = Projects.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = T_USERS_PROJECTS, schema = UQAM, 
	    joinColumns = @JoinColumn(name = USERSID, referencedColumnName = ID),
    	inverseJoinColumns = @JoinColumn(name = PROJECTSID, referencedColumnName = ID) 
	)
	public Set<Projects> getProjects() {
		return this.projects;
	}
	
	public void setProjects(Set<Projects> projects) {
		this.projects = projects;
	}
}