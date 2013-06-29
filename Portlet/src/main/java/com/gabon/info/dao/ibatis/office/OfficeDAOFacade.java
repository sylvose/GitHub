package com.gabon.info.dao.ibatis.office;

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
 * Interface for OfficeDAO.
 *
 */

public interface OfficeDAOFacade<Office> extends DAOFacade<Office> {
	
	List<Office> findByOffice(final Object office, final int... rowStartIdxAndCount);
}