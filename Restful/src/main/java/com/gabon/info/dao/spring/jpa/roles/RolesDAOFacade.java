package com.gabon.info.dao.spring.jpa.roles;

import java.util.List;

import com.gabon.info.dao.DAOSupportFacade;

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
 * Interface for RolesDAO.
 *
 */

public interface RolesDAOFacade<Roles> extends DAOSupportFacade<Roles, Long> {
	
	List<Roles> findByRole(final Object role, final int... rowStartIdxAndCount);
}