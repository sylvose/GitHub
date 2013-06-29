package com.gabon.info.dao.ibatis.roles;

import java.util.List;

import com.gabon.info.dao.ibatis.ConcreteDAOIBatis;
import com.gabon.info.model.roles.Roles;

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
public class RolesDAO extends ConcreteDAOIBatis<Roles> implements RolesDAOFacade<Roles> {

	private static final long serialVersionUID = 8956121744437126767L;
	
	
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
		this.statement = SAVE_ROLES;
		super.save(entity);
	}
	
	public void delete(final Roles entity) {
		this.statement = DELETE_ROLES;
		super.delete(entity);
	}
	
	public void delete(final Long id) {
		this.statement = DELETE_ROLES;
		super.delete(id);
	}

	public Roles update(final Roles entity) {
		this.statement = UPDATE_ROLES;
		return super.update(entity);
	}

	public Roles findById(final Long id) {
		this.statement = FIND_BY_ID_ROLES;
		return super.findById(id);
	}
	
	public List<Roles> findByRole(final Object role, final int... rowStartIdxAndCount) {
		this.statement = FIND_BY_PROPERTY_ROLES;
		return super.findByProperty("role", role, rowStartIdxAndCount);
	}

	public List<Roles> findAll(final int... rowStartIdxAndCount) {
		this.statement = FIND_ALL_ROLES;
		return super.findAll(rowStartIdxAndCount);
	}
	
	public List<Roles> getAll() {
		this.statement = GET_ALL_ROLES;
		return super.getAll();
	}
}