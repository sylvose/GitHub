package com.gabon.info.server.service.rpc.async.projects;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.server.service.rpc.async.ServiceFacadeRPCASync;



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

public interface ProjectsServiceFacadeRPCASync<Projects> extends ServiceFacadeRPCASync<Projects, Long> {

	@Modifying
	@Transactional(readOnly = true)
	List<Projects> findByProject(final Object project, int... rowStartIdxAndCount);
}