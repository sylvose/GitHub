package com.gabon.info.server.dto.roles;

import javax.annotation.concurrent.Immutable;

import org.dynadto.DTO;

import com.gabon.info.server.model.users.Users;

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
public final class RolesDTO<T extends DTO> extends AbstractRolesDTO<T> implements RolesDTOFacade { 

	private static final long serialVersionUID = 6803306354042128428L;

	
	
	// Constructors
	
	/** default constructor */
	public RolesDTO() {
		super();
	}
	
	/** light constructor */
	public RolesDTO(String role) {
		super(role);
	}
	
	/** full constructor */
	public RolesDTO(Long id, String role, Users users) {
		super(id, role, users);
	}
	
	/** default constructor */
	public RolesDTO(Long id, String role) {
		super(id, role);
	}
}