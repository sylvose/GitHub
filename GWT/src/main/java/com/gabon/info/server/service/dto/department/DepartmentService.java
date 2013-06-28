package com.gabon.info.server.service.dto.department;

import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.server.dao.spring.jdbc.department.DepartmentDAO;
import com.gabon.info.server.dto.department.DepartmentDTOFacade;
import com.gabon.info.server.model.department.Department;
import com.gabon.info.server.service.dto.AbstractService;


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
public final class DepartmentService extends AbstractService<DepartmentDTOFacade, Department, Long> implements DepartmentServiceFacade<DepartmentDTOFacade, Department> {

	private static final long serialVersionUID = -4347503786928817126L;

	
	public DepartmentService() {
		super();
		
		if (this.daoSupportFacade == null)
			this.daoSupportFacade = DepartmentDAO.getInstance();
	}
	

	
	public void initialization() {
		if (this.builder == null)
			this.builder = this.builderFactory.getBuilder(DepartmentDTOFacade.class);
	}
	
	
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void save(final Department entity) {
		super.save(entity);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(final Department entity) {
		super.delete(entity);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(final Long id) {
		super.delete(id);
	}

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public DepartmentDTOFacade update(final Department entity) {
		return super.update(entity);
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public DepartmentDTOFacade findById(final Long id) {
		return super.findById(id);
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<DepartmentDTOFacade> findByDepartment(final Object department, int... rowStartIdxAndCount) {
		return super.findByProperty("department", department, rowStartIdxAndCount);
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<DepartmentDTOFacade> findAll(final int... rowStartIdxAndCount) {
		return super.findAll(rowStartIdxAndCount);
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public List<DepartmentDTOFacade> getAll() {
		return super.getAll();
	}
}