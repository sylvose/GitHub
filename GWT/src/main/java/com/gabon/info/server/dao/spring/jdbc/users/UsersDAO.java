package com.gabon.info.server.dao.spring.jdbc.users;

import static com.gabon.info.server.util.Constants.BEAN_USERS_DAO_JDBC_SPRING;

import java.util.List;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.server.dao.spring.jdbc.ConcreteDAOJdbcSpring;
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
@Transactional
@Repository(BEAN_USERS_DAO_JDBC_SPRING)
public class UsersDAO extends ConcreteDAOJdbcSpring<Users, Long> implements UsersDAOFacade<Users> {	
	
	private static final long serialVersionUID = 6227060756707902184L;
	
	
	@GuardedBy("this")
	private static UsersDAOFacade<Users> usersDAOFacade;
	

	public UsersDAO() { 
		super();
		this.clazzE = Users.class;
		this.clazzPK = Long.class;
	}
	
	
	public static synchronized UsersDAOFacade<Users> getInstance() {
		if (usersDAOFacade == null)
			usersDAOFacade = new UsersDAO();
	
		return usersDAOFacade;
	}
	
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	
	public void save(final Users entity) {
		
		if (entity.getId() != null && entity.getOffice().getId() != null && entity.getDepartment().getId() != null && entity.getName() != null && entity.getEmail() != null) {			
			
			this.queryString = QUERY_STRING_SAVE_USERS_BY_ENTITY;
			this.values = new Object[] { entity.getId(), entity.getOffice().getId(), entity.getDepartment().getId(), entity.getName(), entity.getEmail() };
			
			super.save(entity);
		}
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
		
		Users users = null;
		
		if (entity.getId() != null && entity.getOffice().getId() != null && entity.getDepartment().getId() != null && entity.getName() != null && entity.getEmail() != null) {
			
			this.queryString = QUERY_STRING_UPDATE_USERS_BY_ENTITY;
			this.values = new Object[] { entity.getOffice().getId(), entity.getDepartment().getId(), entity.getName(), entity.getEmail(), entity.getId() };
			
			users = super.update(entity);
		}
		
		return users;
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Users findById(final Long id) {
		return super.findById(id);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<Users> findByName(final Object name, final int... rowStartIdxAndCount) {
		return super.findByProperty("name", name, rowStartIdxAndCount);
	}

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<Users> findByEmail(final Object email, final int... rowStartIdxAndCount) {
		return super.findByProperty("email", email, rowStartIdxAndCount);
	}

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<Users> findAll(final int... rowStartIdxAndCount) {
		return super.findAll(rowStartIdxAndCount);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<Users> getAll() {
		return super.getAll();
	}
}