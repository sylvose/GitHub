package com.gabon.info.service.projects;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.service.ServiceFacade;



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

public interface ProjectsServiceFacade<ProjectsDTOFacade, Projects> extends ServiceFacade<ProjectsDTOFacade, Projects, Long> {

	@Modifying
	@Transactional(readOnly = true)
	List<ProjectsDTOFacade> findByProject(final Object project, int... rowStartIdxAndCount);
}