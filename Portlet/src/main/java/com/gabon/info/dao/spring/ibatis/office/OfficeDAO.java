package com.gabon.info.dao.spring.ibatis.office;

import static com.gabon.info.util.Constants.BEAN_OFFICE_DAO_IBATIS_SPRING;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.dao.spring.ibatis.ConcreteDAOIBatisSpring;
import com.gabon.info.model.office.Office;

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
@Repository(BEAN_OFFICE_DAO_IBATIS_SPRING)
public class OfficeDAO extends ConcreteDAOIBatisSpring<Office, Long> implements OfficeDAOFacade<Office> {
	
	private static final long serialVersionUID = -4861547024237667182L;
	
	
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
		this.statement = SAVE_OFFICE;
		super.save(entity);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(final Office entity) {
		this.statement = DELETE_OFFICE;
		super.delete(entity);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(final Long id) {
		this.statement = DELETE_OFFICE;
		super.delete(id);
	}

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Office update(final Office entity) {
		this.statement = UPDATE_OFFICE;
		return super.update(entity);
	}

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Office findById(final Long id) {
		this.statement = FIND_BY_ID_OFFICE;
		return super.findById(id);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<Office> findByOffice(final Object office, final int... rowStartIdxAndCount) {
		this.statement = FIND_BY_PROPERTY_OFFICE;
		return super.findByProperty("office", office, rowStartIdxAndCount);
	}

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<Office> findAll(final int... rowStartIdxAndCount) {
		this.statement = FIND_ALL_OFFICE;
		return super.findAll(rowStartIdxAndCount);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<Office> getAll() {
		this.statement = GET_ALL_OFFICE;
		return super.getAll();
	}
}