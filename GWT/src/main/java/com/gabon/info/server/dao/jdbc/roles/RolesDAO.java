package com.gabon.info.server.dao.jdbc.roles;

import java.util.List;

import com.gabon.info.server.dao.jdbc.ConcreteDAOJdbc;
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
public class RolesDAO extends ConcreteDAOJdbc<Roles> implements RolesDAOFacade<Roles> {
	
	private static final long serialVersionUID = -1683943798868390748L;
	
	
	private static RolesDAOFacade<Roles> rolesDAOFacade;
	
	
	public RolesDAO() { 
		super();
		this.clazz = Roles.class;
	}
	
	public RolesDAO(Class<Roles> clazz) { 
		super(clazz);
		this.clazz = clazz; 
	}
	
	
	public static RolesDAOFacade<Roles> getInstance() {
		synchronized(lock) {
			if (rolesDAOFacade == null)
				rolesDAOFacade = new RolesDAO();
		}
	
		return rolesDAOFacade;
	}
	

	
	
	public void save(final Roles entity) {
		
		if (entity.getId() != null && entity.getUsers().getId() != null && entity.getRole() != null) {
			
			this.queryString = QUERY_STRING_SAVE_ROLES_BY_ENTITY;
			this.values = new Object[] { entity.getId(), entity.getUsers().getId(), entity.getRole() };
			
			super.save(entity);
		}
	}
	
	public void delete(final Roles entity) {
		super.delete(entity);
	}
	
	public void delete(final Long id) {
		super.delete(id);
	}

	public Roles update(final Roles entity) {
		
		Roles roles = null;
		
		if (entity.getId() != null && entity.getUsers().getId() != null && entity.getRole() != null) {
			
			this.queryString = QUERY_STRING_UPDATE_ROLES_BY_ENTITY;
			this.values = new Object[] { entity.getUsers().getId(), entity.getRole(), entity.getId() };
			
			roles = super.update(entity);
		}
		
		return roles;
	}

	public Roles findById(final Long id) {
		return super.findById(id);
	}
	
	public List<Roles> findByRole(final Object role, final int... rowStartIdxAndCount) {
		return super.findByProperty("role", role, rowStartIdxAndCount);
	}

	public List<Roles> findAll(final int... rowStartIdxAndCount) {
		return super.findAll(rowStartIdxAndCount);
	}
	
	public List<Roles> getAll() {
		return super.getAll();
	}
}