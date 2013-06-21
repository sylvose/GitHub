package com.gabon.info.service.roles;

import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.dao.spring.jpa.roles.RolesDAO;
import com.gabon.info.dto.roles.RolesDTOFacade;
import com.gabon.info.model.roles.Roles;
import com.gabon.info.service.AbstractService;


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
public final class RolesService extends AbstractService<RolesDTOFacade, Roles, Long> implements RolesServiceFacade<RolesDTOFacade, Roles> {

	private static final long serialVersionUID = -4347503786928817126L;

	
	public RolesService() {
		super();
		
		if (this.daoSupportFacade == null)
			this.daoSupportFacade = RolesDAO.getInstance();
	}

	
	public void initialization() {
		if (this.builder == null)
			this.builder = this.builderFactory.getBuilder(RolesDTOFacade.class);
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
	public RolesDTOFacade update(final Roles entity) {
		return super.update(entity);
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public RolesDTOFacade findById(final Long id) {
		return super.findById(id);
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<RolesDTOFacade> findByRole(final Object role, int... rowStartIdxAndCount) {
		return super.findByProperty("role", role, rowStartIdxAndCount);
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<RolesDTOFacade> findAll(final int... rowStartIdxAndCount) {
		return super.findAll(rowStartIdxAndCount);
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public List<RolesDTOFacade> getAll() {
		return super.getAll();
	}
}