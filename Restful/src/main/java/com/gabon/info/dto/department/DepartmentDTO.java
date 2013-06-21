package com.gabon.info.dto.department;

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
public final class DepartmentDTO<T extends DTO> extends AbstractDepartmentDTO<T> implements DepartmentDTOFacade {
	
	private static final long serialVersionUID = -6581333425510061092L;

	
	// Constructors
	
	/** empty constructor */
	public DepartmentDTO() {
		super();
	}

	/** full constructor */
	public DepartmentDTO(Long departmentid, String department, Set<Users> users) {
		super(departmentid, department, users);
	}
	
	/** default constructor */
	public DepartmentDTO(Long departmentid, String department) {
		super(departmentid, department);
	}
}