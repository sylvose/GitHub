package com.gabon.info.server.service.rpc.sync.department;

import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.server.dao.spring.jdbc.department.DepartmentDAO;
import com.gabon.info.server.model.department.Department;
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
public final class DepartmentServiceRPCSync extends AbstractServiceRPCSync<Department, Long> implements DepartmentServiceFacadeRPCSync<Department> {

	private static final long serialVersionUID = -2801498600451073251L;

	
	public DepartmentServiceRPCSync() {
		super();
		
		if (this.daoSupportFacade == null)
			this.daoSupportFacade = DepartmentDAO.getInstance();
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
	public Department update(final Department entity) {
		return super.update(entity);
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public Department findById(final Long id) {
		return super.findById(id);
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<Department> findByDepartment(final Object department, int... rowStartIdxAndCount) {
		return super.findByProperty("department", department, rowStartIdxAndCount);
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<Department> findAll(final int... rowStartIdxAndCount) {
		return super.findAll(rowStartIdxAndCount);
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public List<Department> getAll() {
		return super.getAll();
	}
}