package com.gabon.info.server.service.rpc.sync.office;

import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.server.dao.spring.jdbc.office.OfficeDAO;
import com.gabon.info.server.model.office.Office;
import com.gabon.info.server.service.rpc.sync.AbstractServiceRPCSync;


/*
 * This class is a service class.
 * 
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

@NotThreadSafe
public final class OfficeServiceRPCSync extends AbstractServiceRPCSync<Office, Long> implements OfficeServiceFacadeRPCSync<Office> {
	
	private static final long serialVersionUID = -5587701551660311648L;

	
	
	public OfficeServiceRPCSync() {
		super();
		
		if (this.daoSupportFacade == null)
			this.daoSupportFacade = OfficeDAO.getInstance();
	}
	
	
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void save(final Office entity) {
		super.save(entity);
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
		return super.update(entity);
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public Office findById(final Long id) {
		return super.findById(id);
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<Office> findByOffice(final Object office, int... rowStartIdxAndCount) {
		return super.findByProperty("office", office, rowStartIdxAndCount);
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<Office> findAll(final int... rowStartIdxAndCount) {
		return super.findAll(rowStartIdxAndCount);
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public List<Office> getAll() {
		return super.getAll();
	}
}