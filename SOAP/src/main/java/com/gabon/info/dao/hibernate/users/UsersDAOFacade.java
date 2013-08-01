package com.gabon.info.dao.hibernate.users;

import java.util.List;

import com.gabon.info.dao.DAOFacade;

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

public interface UsersDAOFacade<Users> extends DAOFacade<Users> {
	
	List<Users> findByName(final Object name, final int... rowStartIdxAndCount);

	List<Users> findByEmail(final Object email, final int... rowStartIdxAndCount);
}