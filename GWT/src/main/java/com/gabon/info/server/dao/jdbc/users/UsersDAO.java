package com.gabon.info.server.dao.jdbc.users;

import java.util.List;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

import com.gabon.info.server.dao.jdbc.ConcreteDAOJdbc;
import com.gabon.info.server.model.users.Users;

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

@ThreadSafe
public class UsersDAO extends ConcreteDAOJdbc<Users> implements UsersDAOFacade<Users> {	
	
	private static final long serialVersionUID = 6227060756707902184L;
	

	@GuardedBy("this")
	private static UsersDAOFacade<Users> usersDAOFacade;
	

	public UsersDAO() { 
		super();
		this.clazz = Users.class;
	}
	
	public UsersDAO(Class<Users> clazz) { 
		super(clazz);
		this.clazz = clazz; 
	}
	
	
	public static synchronized UsersDAOFacade<Users> getInstance() {
		if (usersDAOFacade == null)
			usersDAOFacade = new UsersDAO();
	
		return usersDAOFacade;
	}

	
	
	
	public void save(final Users entity) {
		
		if (entity.getId() != null && entity.getOffice().getId() != null && entity.getDepartment().getId() != null && entity.getName() != null && entity.getEmail() != null) {			
			
			this.queryString = QUERY_STRING_SAVE_USERS_BY_ENTITY;
			this.values = new Object[] { entity.getId(), entity.getOffice().getId(), entity.getDepartment().getId(), entity.getName(), entity.getEmail() };
			
			super.save(entity);
		}
	}
	
	public void delete(final Users entity) {
		super.delete(entity);
	}
	
	public void delete(final Long id) {
		super.delete(id);
	}

	public Users update(final Users entity) {
		
		Users users = null;
		
		if (entity.getId() != null && entity.getOffice().getId() != null && entity.getDepartment().getId() != null && entity.getName() != null && entity.getEmail() != null) {
			
			this.queryString = QUERY_STRING_UPDATE_USERS_BY_ENTITY;
			this.values = new Object[] { entity.getOffice().getId(), entity.getDepartment().getId(), entity.getName(), entity.getEmail(), entity.getId() };
			
			users = super.update(entity);
		}
		
		return users;
	}
	
	public Users findById(final Long id) {
		return super.findById(id);
	}
	
	public List<Users> findByName(final Object name, final int... rowStartIdxAndCount) {
		return super.findByProperty("name", name, rowStartIdxAndCount);
	}

	public List<Users> findByEmail(final Object email, final int... rowStartIdxAndCount) {
		return super.findByProperty("email", email, rowStartIdxAndCount);
	}

	public List<Users> findAll(final int... rowStartIdxAndCount) {
		return super.findAll(rowStartIdxAndCount);
	}
	
	public List<Users> getAll() {
		return super.getAll();
	}
}