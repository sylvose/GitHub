package com.gabon.info.dao.jpa.users;

import java.util.List;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

import com.gabon.info.dao.jpa.ConcreteDAOJpa;
import com.gabon.info.model.users.Users;

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
public class UsersDAO extends ConcreteDAOJpa<Users> implements UsersDAOFacade<Users> {	
	
	private static final long serialVersionUID = 7332635337769301698L;
	
	
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
		super.save(entity);
	}
	
	public void delete(final Users entity) {
		super.delete(entity);
	}
	
	public void delete(final Long id) {
		super.delete(id);
	}

	public Users update(final Users entity) {
		return super.update(entity);
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