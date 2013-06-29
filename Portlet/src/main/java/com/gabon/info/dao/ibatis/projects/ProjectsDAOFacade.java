package com.gabon.info.dao.ibatis.projects;

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
 * Interface for ProjectsDAO.
 *
 */

public interface ProjectsDAOFacade<Projects> extends DAOFacade<Projects> {
	
	List<Projects> findByProject(final Object project, final int... rowStartIdxAndCount);
}