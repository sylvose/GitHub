package com.gabon.info.server.dao.spring.jdbc.users;

import java.util.List;

import com.gabon.info.server.dao.DAOSupportFacade;

/**
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
 * Interface for UsersDAO.
 *
 */

public interface UsersDAOFacade<Users> extends DAOSupportFacade<Users, Long> {
	
	List<Users> findByName(final Object name, final int... rowStartIdxAndCount);

	List<Users> findByEmail(final Object email, final int... rowStartIdxAndCount);
}