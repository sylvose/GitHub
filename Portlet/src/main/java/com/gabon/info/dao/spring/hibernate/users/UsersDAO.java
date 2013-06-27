package com.gabon.info.dao.spring.hibernate.users;

import static com.gabon.info.util.Constants.BEAN_USERS_DAO_HIBERNATE_SPRING;

import java.util.List;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.dao.spring.hibernate.ConcreteDAOHibernateSpring;
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
@Transactional
@Repository(BEAN_USERS_DAO_HIBERNATE_SPRING)
public class UsersDAO extends ConcreteDAOHibernateSpring<Users, Long> implements UsersDAOFacade<Users> {	
	
	private static final long serialVersionUID = 7641031870572687778L;

	
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