package com.gabon.info.server.service.dto.users;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.server.service.dto.ServiceFacade;


/*
 * This class is a interface service class.
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

public interface UsersServiceFacade<UsersDTOFacade, Users> extends ServiceFacade<UsersDTOFacade, Users, Long> {

	@Modifying
	@Transactional(readOnly = true)
	List<UsersDTOFacade> findByName(final Object name, int... rowStartIdxAndCount);

	@Modifying
	@Transactional(readOnly = true)
	List<UsersDTOFacade> findByEmail(final Object email, int... rowStartIdxAndCount);
}