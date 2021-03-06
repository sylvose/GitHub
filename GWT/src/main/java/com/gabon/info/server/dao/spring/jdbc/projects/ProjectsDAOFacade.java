package com.gabon.info.server.dao.spring.jdbc.projects;

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
 * Interface for ProjectsDAO.
 *
 */

public interface ProjectsDAOFacade<Projects> extends DAOSupportFacade<Projects, Long> {
	
	List<Projects> findByProject(final Object project, final int... rowStartIdxAndCount);
}