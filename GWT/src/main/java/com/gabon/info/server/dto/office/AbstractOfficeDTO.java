package com.gabon.info.server.dto.office;

import com.gabon.info.server.dto.AbstractDTO;
import org.dynadto.DTO;
import com.gabon.info.server.model.users.Users;

/*
 * This class is a abstract dto class.
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

public abstract class AbstractOfficeDTO<T extends DTO> extends AbstractDTO<T> {

	private static final long serialVersionUID = 9056779960898937059L;

	
	// Fields
	
	protected Users users;
	
	protected String office;

	
	// Constructors

	/** empty constructor */
	public AbstractOfficeDTO() {
		super();
	}

	/** full constructor */
	public AbstractOfficeDTO(Long id, String office, Users users) {
		this.id = id;
		this.office = office;
		this.users = users;
	}
	
	/** default constructor */
	public AbstractOfficeDTO(Long id, String office) {
		this.id = id;
		this.office = office;
	}
	
	
	// Property accessors
	
    public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public String getOffice() {
		return this.office;
	}

	public void setOffice(String office) {
		this.office = office;
	}
}