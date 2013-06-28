package com.gabon.info.server.dao.jdbc.office;

import java.util.List;

import com.gabon.info.server.dao.jdbc.ConcreteDAOJdbc;
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
public class OfficeDAO extends ConcreteDAOJdbc<Office> implements OfficeDAOFacade<Office> {
	
	private static final long serialVersionUID = -6701582754878945062L;
	
	
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
		
		if (entity.getId() != null && entity.getOffice() != null) {
			
			this.queryString = QUERY_STRING_SAVE_OFFICE_BY_ENTITY;
			this.values = new Object[] { entity.getId(), entity.getOffice() };

			super.save(entity);
		}
	}
	
	public void delete(final Office entity) {
		super.delete(entity);
	}
	
	public void delete(final Long id) {
		super.delete(id);
	}

	public Office update(final Office entity) {
		
		Office office = null;
		
		if (entity.getId() != null && entity.getOffice() != null) {
			
			this.queryString = QUERY_STRING_UPDATE_OFFICE_BY_ENTITY;
			this.values = new Object[] { entity.getOffice(), entity.getId() };
			
			office = super.update(entity);
		}
		
		return office;
	}

	public Office findById(final Long id) {
		return super.findById(id);
	}
	
	public List<Office> findByOffice(final Object office, final int... rowStartIdxAndCount) {
		return super.findByProperty("office", office, rowStartIdxAndCount);
	}

	public List<Office> findAll(final int... rowStartIdxAndCount) {
		return super.findAll(rowStartIdxAndCount);
	}
	
	public List<Office> getAll() {
		return super.getAll();
	}
}