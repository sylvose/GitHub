package com.gabon.info.server.dao.spring.jdbc.department;

import static com.gabon.info.server.util.Constants.BEAN_DEPARTMENT_DAO_JDBC_SPRING;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.server.dao.spring.jdbc.ConcreteDAOJdbcSpring;
import com.gabon.info.server.model.department.Department;

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
@Repository(BEAN_DEPARTMENT_DAO_JDBC_SPRING)
public class DepartmentDAO extends ConcreteDAOJdbcSpring<Department, Long> implements DepartmentDAOFacade<Department> {

	private static final long serialVersionUID = -1071478704119802690L;
	
	
	private static DepartmentDAOFacade<Department> departmentDAOFacade;
	
	
	public DepartmentDAO() { 
		super();
		this.clazzE = Department.class;
		this.clazzPK = Long.class;
	}
	
	
	public static DepartmentDAOFacade<Department> getInstance() {
		synchronized(lock) {
			if (departmentDAOFacade == null)
				departmentDAOFacade = new DepartmentDAO();
		}
	
		return departmentDAOFacade;
	}
	
	
	
	
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void save(final Department entity) {
		
		if (entity.getId() != null && entity.getDepartment() != null) {
			
			this.queryString = QUERY_STRING_SAVE_DEPARTMENT_BY_ENTITY;
			this.values = new Object[] { entity.getId(), entity.getDepartment() };

			super.save(entity);
		}
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
		
		Department department = null;
		
		if (entity.getId() != null && entity.getDepartment() != null) {
			
			this.queryString = QUERY_STRING_UPDATE_DEPARTMENT_BY_ENTITY;
			this.values = new Object[] { entity.getDepartment(), entity.getId() };

			department = super.update(entity);
		}
		
		return department;
	}

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Department findById(final Long id) {
		return super.findById(id);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<Department> findByDepartment(final Object department, final int... rowStartIdxAndCount) {
		return super.findByProperty("department", department, rowStartIdxAndCount);
	}

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<Department> findAll(final int... rowStartIdxAndCount) {
		return super.findAll(rowStartIdxAndCount);
	}

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<Department> getAll() {
		return super.getAll();
	}
}