package com.gabon.info.server.service.dto.users;

import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.server.dao.spring.jdbc.users.UsersDAO;
import com.gabon.info.server.dto.users.UsersDTOFacade;
import com.gabon.info.server.model.users.Users;
import com.gabon.info.server.service.dto.AbstractService;


/*
 * This class is a service class.
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

@NotThreadSafe
public final class UsersService extends AbstractService<UsersDTOFacade, Users, Long> implements UsersServiceFacade<UsersDTOFacade, Users> {

	private static final long serialVersionUID = -4347503786928817126L;

	
	public UsersService() {
		super();
		
		if (this.daoSupportFacade == null)
			this.daoSupportFacade = UsersDAO.getInstance();
	}
	
	
	public void initialization() {
		if (this.builder == null)
			this.builder = this.builderFactory.getBuilder(UsersDTOFacade.class);
	}
	
	
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void save(final Users entity) {
		super.save(entity);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(final Users entity) {
		super.delete(entity);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(final Long id) {
		super.delete(id);
	}

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public UsersDTOFacade update(final Users entity) {
		return super.update(entity);
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public UsersDTOFacade findById(final Long id) {
		return super.findById(id);
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<UsersDTOFacade> findByName(final Object name, int... rowStartIdxAndCount) {
		return super.findByProperty("name", name, rowStartIdxAndCount);
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<UsersDTOFacade> findByEmail(final Object email, int... rowStartIdxAndCount) {
		return super.findByProperty("email", email, rowStartIdxAndCount);
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<UsersDTOFacade> findAll(final int... rowStartIdxAndCount) {
		return super.findAll(rowStartIdxAndCount);
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public List<UsersDTOFacade> getAll() {
		return super.getAll();
	}
}