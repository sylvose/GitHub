package com.gabon.info.server.dao.spring.jdbc.roles;


import static com.gabon.info.server.util.Constants.BEAN_ROLES_DAO_JDBC_SPRING;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.server.dao.spring.jdbc.ConcreteDAOJdbcSpring;
import com.gabon.info.server.model.roles.Roles;

/**
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

@Transactional
@Repository(BEAN_ROLES_DAO_JDBC_SPRING)
public class RolesDAO extends ConcreteDAOJdbcSpring<Roles, Long> implements RolesDAOFacade<Roles> {
	
	private static final long serialVersionUID = -1683943798868390748L;
	
	
	private static RolesDAOFacade<Roles> rolesDAOFacade;
	
	
	public RolesDAO() { 
		super();
		this.clazzE = Roles.class;
		this.clazzPK = Long.class;
	}
	
	
	public static RolesDAOFacade<Roles> getInstance() {
		synchronized(lock) {
			if (rolesDAOFacade == null)
				rolesDAOFacade = new RolesDAO();
		}
	
		return rolesDAOFacade;
	}
	
	
	
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void save(final Roles entity) {
		
		if (entity.getId() != null && entity.getUsers().getId() != null && entity.getRole() != null) {
			
			this.queryString = QUERY_STRING_SAVE_ROLES_BY_ENTITY;
			this.values = new Object[] { entity.getId(), entity.getUsers().getId(), entity.getRole() };
			
			super.save(entity);
		}
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
		
		Roles roles = null;
		
		if (entity.getId() != null && entity.getUsers().getId() != null && entity.getRole() != null) {
			
			this.queryString = QUERY_STRING_UPDATE_ROLES_BY_ENTITY;
			this.values = new Object[] { entity.getUsers().getId(), entity.getRole(), entity.getId() };
			
			roles = super.update(entity);
		}
		
		return roles;
	}

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Roles findById(final Long id) {
		return super.findById(id);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<Roles> findByRole(final Object role, final int... rowStartIdxAndCount) {
		return super.findByProperty("role", role, rowStartIdxAndCount);
	}

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<Roles> findAll(final int... rowStartIdxAndCount) {
		return super.findAll(rowStartIdxAndCount);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<Roles> getAll() {
		return super.getAll();
	}
}