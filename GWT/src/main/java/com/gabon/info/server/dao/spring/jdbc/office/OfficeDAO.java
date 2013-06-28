package com.gabon.info.server.dao.spring.jdbc.office;

import static com.gabon.info.server.util.Constants.BEAN_OFFICE_DAO_JDBC_SPRING;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.server.dao.spring.jdbc.ConcreteDAOJdbcSpring;
import com.gabon.info.server.model.office.Office;

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
@Repository(BEAN_OFFICE_DAO_JDBC_SPRING)
public class OfficeDAO extends ConcreteDAOJdbcSpring<Office, Long> implements OfficeDAOFacade<Office> {
	
	private static final long serialVersionUID = -6701582754878945062L;
	
	
	private static OfficeDAOFacade<Office> officeDAOFacade;
	
	
	public OfficeDAO() { 
		super();
		this.clazzE = Office.class;
		this.clazzPK = Long.class;
	}
	

	public static OfficeDAOFacade<Office> getInstance() {
		synchronized(lock) {
			if (officeDAOFacade == null)
				officeDAOFacade = new OfficeDAO();
		}
	
		return officeDAOFacade;
	}
	
	
	
	
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void save(final Office entity) {
		
		if (entity.getId() != null && entity.getOffice() != null) {
			
			this.queryString = QUERY_STRING_SAVE_OFFICE_BY_ENTITY;
			this.values = new Object[] { entity.getId(), entity.getOffice() };

			super.save(entity);
		}
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(final Office entity) {
		super.delete(entity);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(final Long id) {
		super.delete(id);
	}

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Office update(final Office entity) {
		
		Office office = null;
		
		if (entity.getId() != null && entity.getOffice() != null) {
			
			this.queryString = QUERY_STRING_UPDATE_OFFICE_BY_ENTITY;
			this.values = new Object[] { entity.getOffice(), entity.getId() };
			
			office = super.update(entity);
		}
		
		return office;
	}

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Office findById(final Long id) {
		return super.findById(id);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<Office> findByOffice(final Object office, final int... rowStartIdxAndCount) {
		return super.findByProperty("office", office, rowStartIdxAndCount);
	}

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<Office> findAll(final int... rowStartIdxAndCount) {
		return super.findAll(rowStartIdxAndCount);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<Office> getAll() {
		return super.getAll();
	}
}