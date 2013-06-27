package com.gabon.info.model.roles;

import static com.gabon.info.util.Constants.BEAN_ROLES;
import static com.gabon.info.util.Constants.GET_ALL_ROLES_NAME;
import static com.gabon.info.util.Constants.GET_ALL_ROLES_QUERY;
import static com.gabon.info.util.Constants.ROLES_SEQ;
import static com.gabon.info.util.Constants.ROLES_SEQ_GEN;
import static com.gabon.info.util.Constants.Roles;
import static com.gabon.info.util.Constants.T_ROLES;
import static com.gabon.info.util.Constants.UQAM;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.gabon.info.model.users.Users;

/*
 * This class is a model class.
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

@Component(BEAN_ROLES)

@Entity(name = Roles)
@Table(name = T_ROLES, schema = UQAM)
@SequenceGenerator(name = ROLES_SEQ_GEN, sequenceName = ROLES_SEQ, initialValue = 1, allocationSize = 1)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

@NamedQueries({ 
	@NamedQuery(name = GET_ALL_ROLES_NAME, query = GET_ALL_ROLES_QUERY)
})

@Cacheable(true)

public final class Roles extends AbstractRoles implements RolesFacade { 

	private static final long serialVersionUID = -6091836281264152922L;

	
	// Constructors
	
	/** default constructor */
	public Roles() {
		super();
	}
	
	/** light constructor */
	public Roles(String role) {
		super(role);
	}
	
	/** full constructor */
	public Roles(Long id, String role, Users users) {
		super(id, role, users);
	}
	
	/** default constructor */
	public Roles(Long id, String role) {
		super(id, role);
	}
}