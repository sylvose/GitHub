package com.gabon.info.server.model.office;

import static com.gabon.info.server.util.Constants.BEAN_OFFICE;
import static com.gabon.info.server.util.Constants.GET_ALL_OFFICE_NAME;
import static com.gabon.info.server.util.Constants.GET_ALL_OFFICE_QUERY;
import static com.gabon.info.server.util.Constants.OFFICE_SEQ;
import static com.gabon.info.server.util.Constants.OFFICE_SEQ_GEN;
import static com.gabon.info.server.util.Constants.Office;
import static com.gabon.info.server.util.Constants.T_OFFICE;
import static com.gabon.info.server.util.Constants.UQAM;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.gabon.info.server.model.users.Users;

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

@Component(BEAN_OFFICE)

@Entity(name = Office)
@Table(name = T_OFFICE, schema = UQAM)
@SequenceGenerator(name = OFFICE_SEQ_GEN, sequenceName = OFFICE_SEQ, initialValue = 1, allocationSize = 1)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

@NamedQueries({ 
	@NamedQuery(name = GET_ALL_OFFICE_NAME, query = GET_ALL_OFFICE_QUERY)
})

@Cacheable(true)

public final class Office extends AbstractOffice implements OfficeFacade {

	private static final long serialVersionUID = 560809623819662589L;
	
	
	// Constructors

	/** empty constructor */
	public Office() {
		super();
	}

	/** full constructor */
	public Office(Long userid, String office, Users user) {
		super(userid, office, user);
	}
	
	/** default constructor */
	public Office(Long id, String office) {
		super(id, office);
	}
}