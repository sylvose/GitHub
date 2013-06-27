package com.gabon.info.dao.hibernate.office;

import java.util.List;

import com.gabon.info.dao.hibernate.ConcreteDAOHibernate;
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
public class OfficeDAO extends ConcreteDAOHibernate<Office> implements OfficeDAOFacade<Office> {
	
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
		super.save(entity);
	}
	
	public void delete(final Office entity) {
		super.delete(entity);
	}
	
	public void delete(final Long id) {
		super.delete(id);
	}

	public Office update(final Office entity) {
		return super.update(entity);
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