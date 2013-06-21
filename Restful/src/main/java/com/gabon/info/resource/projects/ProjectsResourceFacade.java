package com.gabon.info.resource.projects;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.resource.ResourceFacade;



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

public interface ProjectsResourceFacade<Response, Projects> extends ResourceFacade<Response, Projects, Long> {

	@GET // Annotation for methods that retrieve a resource representation
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path(value = URI_PATH_FIND_ALL_BY_PROJECT)
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Response findByProject(final @QueryParam(PROJECT) Object project, final @QueryParam(ROWSSTARTIDXANDCOUNT) int... rowStartIdxAndCount);
}