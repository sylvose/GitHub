package com.gabon.info.server.service.rpc.sync.users;

import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.server.dao.spring.jdbc.users.UsersDAO;
import com.gabon.info.server.model.users.Users;
import com.gabon.info.server.service.rpc.sync.AbstractServiceRPCSync;


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
public final class UsersServiceRPCSync extends AbstractServiceRPCSync<Users, Long> implements UsersServiceFacadeRPCSync<Users> {

	private static final long serialVersionUID = -6196811116652808290L;

	
	
	public UsersServiceRPCSync() {
		super();
		
		if (this.daoSupportFacade == null)
			this.daoSupportFacade = UsersDAO.getInstance();
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
	public Users update(final Users entity) {
		return super.update(entity);
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public Users findById(final Long id) {
		return super.findById(id);
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<Users> findByName(final Object name, int... rowStartIdxAndCount) {
		return super.findByProperty("name", name, rowStartIdxAndCount);
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<Users> findByEmail(final Object email, int... rowStartIdxAndCount) {
		return super.findByProperty("email", email, rowStartIdxAndCount);
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<Users> findAll(final int... rowStartIdxAndCount) {
		return super.findAll(rowStartIdxAndCount);
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public List<Users> getAll() {
		return super.getAll();
	}
}