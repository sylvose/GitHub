package com.gabon.info.service.office;

import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.dao.spring.ibatis.office.OfficeDAO;
import com.gabon.info.dto.office.OfficeDTOFacade;
import com.gabon.info.model.office.Office;
import com.gabon.info.service.AbstractService;


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
public final class OfficeService extends AbstractService<OfficeDTOFacade, Office, Long> implements OfficeServiceFacade<OfficeDTOFacade, Office> {

	private static final long serialVersionUID = -4347503786928817126L;

	
	public OfficeService() {
		super();
		
		if (this.daoSupportFacade == null)
			this.daoSupportFacade = OfficeDAO.getInstance();
	}

	
	public void initialization() {
		if (this.builder == null)
			this.builder = this.builderFactory.getBuilder(OfficeDTOFacade.class);
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
	public OfficeDTOFacade update(final Office entity) {
		return super.update(entity);
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public OfficeDTOFacade findById(final Long id) {
		return super.findById(id);
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<OfficeDTOFacade> findByOffice(final Object office, int... rowStartIdxAndCount) {
		return super.findByProperty("office", office, rowStartIdxAndCount);
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<OfficeDTOFacade> findAll(final int... rowStartIdxAndCount) {
		return super.findAll(rowStartIdxAndCount);
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public List<OfficeDTOFacade> getAll() {
		return super.getAll();
	}
}