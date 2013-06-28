package com.gabon.info.server.service.rpc.sync.roles;

import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.server.dao.spring.jdbc.roles.RolesDAO;
import com.gabon.info.server.model.roles.Roles;
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
public final class RolesServiceRPCSync extends AbstractServiceRPCSync<Roles, Long> implements RolesServiceFacadeRPCSync<Roles> {

	private static final long serialVersionUID = -4347503786928817126L;

	
	public RolesServiceRPCSync() {
		super();
		
		if (this.daoSupportFacade == null)
			this.daoSupportFacade = RolesDAO.getInstance();
	}
	
	
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void save(final Roles entity) {
		super.save(entity);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(final Roles entity) {
		super.delete(entity);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(final Long id) {
		super.delete(id);
	}

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Roles update(final Roles entity) {
		return super.update(entity);
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public Roles findById(final Long id) {
		return super.findById(id);
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<Roles> findByRole(final Object role, int... rowStartIdxAndCount) {
		return super.findByProperty("role", role, rowStartIdxAndCount);
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<Roles> findAll(final int... rowStartIdxAndCount) {
		return super.findAll(rowStartIdxAndCount);
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public List<Roles> getAll() {
		return super.getAll();
	}
}