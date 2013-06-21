package com.gabon.info.model.department;

import static com.gabon.info.util.RestFulConstants.AbstractDepartment;
import static com.gabon.info.util.RestFulConstants.DEPARTMENT;
import static com.gabon.info.util.RestFulConstants.ID;
import static com.gabon.info.util.RestFulConstants.USERS;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/*
import javax.persistence.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
*/

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
 * AbstractDepartment entity provides the base persistence definition of the Department entity.
 */

@MappedSuperclass

@XmlRootElement(name = AbstractDepartment)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = AbstractDepartment, propOrder = {ID, DEPARTMENT, USERS})
public abstract class AbstractDepartment extends AbstractModel {

	private static final long serialVersionUID = -6658807982078337613L;

	
	// Fields
	
	@XmlElement(required = true)
	protected Long id;
	
	@XmlElement(required = true)
	protected String department;
	
	@XmlElement(required = true)
	protected Set<Users> users;

	
	// Constructors

	/** empty constructor */
	public AbstractDepartment() {
		super();
		
		this.users = new HashSet<Users>(0);
		this.version = 0;
	}

	/** full constructor */
	public AbstractDepartment(Long id, String department, Set<Users> users) {
		this.id = id;
		this.department = department;
		this.users = users;
	}
	
	/** default constructor */
	public AbstractDepartment(Long id, String department) {
		this.id = id;
		this.department = department;
	}
	

	// Property accessors
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = DEPARTMENT_SEQ_GEN)
	@Column(name = ID, unique = true, nullable = false, insertable = false, updatable = false, precision = 10, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
		this.version++;
	}

	@Column(name = DEPARTMENT, nullable = false, insertable = true, updatable = true)
	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@OneToMany(targetEntity = Users.class, fetch = FetchType.LAZY, mappedBy = "department")
	// @OneToMany(targetEntity = Users.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "department")
	// @OnDelete(action = OnDeleteAction.CASCADE)
	public Set<Users> getUsers() {
		return this.users;
	}

	public void setUsers(Set<Users> users) {
		this.users = users;
	}
	
	
	public void addUsers(final Users users) {
		if (users != null) {
			if (!getUsers().contains(users)) {
				getUsers().add(users);
			}
		}
	}
	
	public void addUsers(final Set<Users> usersList) {
        if (usersList != null) {
            for (Users project : usersList) {
            	users.add(project);
            }
        }
    }
}