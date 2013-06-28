package com.gabon.info.server.model.office;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.gabon.info.server.model.users.Users;
import com.gabon.info.server.util.Constants;

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

public interface OfficeFacade extends Constants {


	// Property accessors
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = OFFICE_SEQ_GEN)
	@Column(name = ID, unique = true, nullable = false, insertable = false, updatable = false, precision = 10, scale = 0)
	public Long getId();

	public void setId(Long id);

	@OneToOne(targetEntity = Users.class, fetch = FetchType.LAZY, mappedBy = "office")
	// @OneToOne(targetEntity = Users.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "office")
	// @OnDelete(action = OnDeleteAction.CASCADE)
	public Users getUsers();

	public void setUsers(Users users);

	@Column(name = OFFICE, nullable = false, insertable = true, updatable = true)
	public String getOffice();

	public void setOffice(String office);
}