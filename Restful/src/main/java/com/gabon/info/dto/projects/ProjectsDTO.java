package com.gabon.info.dto.projects;

import java.util.Set;

import javax.annotation.concurrent.Immutable;

import org.dynadto.DTO;
import com.gabon.info.model.users.Users;

/*
 * This class is a dto class.
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

@Immutable
public final class ProjectsDTO<T extends DTO> extends AbstractProjectsDTO<T> implements ProjectsDTOFacade {
	
	private static final long serialVersionUID = 3117449074301641945L;

	
	// Constructors
	
	/** default constructor */
	public ProjectsDTO() {
		super();
	}

	/** full constructor */
	public ProjectsDTO(Long id, String project, Set<Users> users) {
		super(id, project, users);
	}
	
	/** default constructor */
	public ProjectsDTO(Long id, String projec) {
		super(id, projec);
	}
		
}