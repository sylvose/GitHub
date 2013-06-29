package com.gabon.info.dao.ibatis.office;

import java.util.List;

import com.gabon.info.dao.ibatis.ConcreteDAOIBatis;
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
public class OfficeDAO extends ConcreteDAOIBatis<Office> implements OfficeDAOFacade<Office> {
	
	private static final long serialVersionUID = -4861547024237667182L;
	
	
	private static OfficeDAOFacade<Office> officeDAOFacade;
	
	
	public OfficeDAO() { 
		super();
		this.clazz = Office.class;
	}
	
	public OfficeDAO(Class<Office> clazz) { 
		super(clazz);
		this.clazz = clazz; 
	}
	

	public static OfficeDAOFacade<Office> getInstance() {
		synchronized(lock) {
			if (officeDAOFacade == null)
				officeDAOFacade = new OfficeDAO();
		}
	
		return officeDAOFacade;
	}
	
	
	public void save(final Office entity) {
		this.statement = SAVE_OFFICE;
		super.save(entity);
	}
	
	public void delete(final Office entity) {
		this.statement = DELETE_OFFICE;
		super.delete(entity);
	}
	
	public void delete(final Long id) {
		this.statement = DELETE_OFFICE;
		super.delete(id);
	}

	public Office update(final Office entity) {
		this.statement = UPDATE_OFFICE;
		return super.update(entity);
	}

	public Office findById(final Long id) {
		this.statement = FIND_BY_ID_OFFICE;
		return super.findById(id);
	}
	
	public List<Office> findByOffice(final Object office, final int... rowStartIdxAndCount) {
		this.statement = FIND_BY_PROPERTY_OFFICE;
		return super.findByProperty("office", office, rowStartIdxAndCount);
	}

	public List<Office> findAll(final int... rowStartIdxAndCount) {
		this.statement = FIND_ALL_OFFICE;
		return super.findAll(rowStartIdxAndCount);
	}
	
	public List<Office> getAll() {
		this.statement = GET_ALL_OFFICE;
		return super.getAll();
	}
}