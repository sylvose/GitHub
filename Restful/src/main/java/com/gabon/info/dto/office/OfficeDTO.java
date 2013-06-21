package com.gabon.info.dto.office;

import javax.annotation.concurrent.Immutable;

import org.dynadto.DTO;
import com.gabon.info.model.office.OfficeFacade;
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
public final class OfficeDTO<T extends DTO> extends AbstractOfficeDTO<T> implements OfficeFacade {

	private static final long serialVersionUID = 4924285674941295583L;

	

	// Constructors

	/** empty constructor */
	public OfficeDTO() {
		super();
	}

	/** full constructor */
	public OfficeDTO(Long userid, String office, Users user) {
		super(userid, office, user);
	}
	
	/** default constructor */
	public OfficeDTO(Long id, String office) {
		super(id, office);
	}
}