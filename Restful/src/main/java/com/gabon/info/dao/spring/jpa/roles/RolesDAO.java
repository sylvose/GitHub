package com.gabon.info.dao.spring.jpa.roles;

import static com.gabon.info.util.Constants.BEAN_ROLES_DAO_JPA_SPRING;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.dao.spring.jpa.ConcreteDAOJpaSpring;
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

@Transactional
@Repository(BEAN_ROLES_DAO_JPA_SPRING)
public class RolesDAO extends ConcreteDAOJpaSpring<Roles, Long> implements RolesDAOFacade<Roles> {

	private static final long serialVersionUID = 8956121744437126767L;
	
	
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
	public void save(Roles entity) {
		super.save(entity);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(Roles entity) {
		super.delete(entity);
	}

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(Long id) {
		super.delete(id);
	}

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Roles update(Roles entity) {
		return super.update(entity);
	}

	@Modifying
	@Transactional(readOnly = true)
	public Roles findById(Long id) {
		return super.findById(id);
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<Roles> findByRole(final Object role, final int... rowStartIdxAndCount) {
		return super.findByProperty("role", role, rowStartIdxAndCount);
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<Roles> findAll(int... rowStartIdxAndCount) {
		return super.findAll(rowStartIdxAndCount);
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<Roles> getAll() {
		return super.getAll();
	}
}