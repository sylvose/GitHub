package com.gabon.info.model.office;

import static com.gabon.info.util.RestFulConstants.AbstractOffice;
import static com.gabon.info.util.RestFulConstants.ID;
import static com.gabon.info.util.RestFulConstants.OFFICE;
import static com.gabon.info.util.RestFulConstants.USERS;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
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
 * AbstractOffice entity provides the base persistence definition of the Office entity.
 */

@MappedSuperclass

@XmlRootElement(name = AbstractOffice)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = AbstractOffice, propOrder = {ID, USERS, OFFICE})
public abstract class AbstractOffice extends AbstractModel {

	private static final long serialVersionUID = 7594638713905599010L;

	
	// Fields
	
	@XmlElement(required = true)
	protected Long id;
	
	@XmlElement(required = true)
	protected Users users;
	
	@XmlElement(required = true)
	protected String office;

	
	// Constructors

	/** empty constructor */
	public AbstractOffice() {
		super();
		
		this.version = 0;
	}

	/** full constructor */
	public AbstractOffice(Long id, String office, Users users) {
		this.id = id;
		this.office = office;
		this.users = users;
	}
	
	/** default constructor */
	public AbstractOffice(Long id, String office) {
		this.id = id;
		this.office = office;
	}
	

	// Property accessors
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = OFFICE_SEQ_GEN)
	@Column(name = ID, unique = true, nullable = false, insertable = false, updatable = false, precision = 10, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
		this.version++;
	}

	@OneToOne(targetEntity = Users.class, fetch = FetchType.LAZY, mappedBy = "office")
	// @OneToOne(targetEntity = Users.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "office")
	// @OnDelete(action = OnDeleteAction.CASCADE)
	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@Column(name = OFFICE, nullable = false, insertable = true, updatable = true)
	public String getOffice() {
		return this.office;
	}

	public void setOffice(String office) {
		this.office = office;
	}
}