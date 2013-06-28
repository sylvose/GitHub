package com.gabon.info.server.dao.jdbc.department;

import java.util.List;

import com.gabon.info.server.dao.jdbc.ConcreteDAOJdbc;
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

public class DepartmentDAO extends ConcreteDAOJdbc<Department> implements DepartmentDAOFacade<Department> {

	private static final long serialVersionUID = -1071478704119802690L;
	
	
	private static DepartmentDAOFacade<Department> departmentDAOFacade;
	
	
	public DepartmentDAO() { 
		super();
		this.clazz = Department.class;
	}
	
	public DepartmentDAO(Class<Department> clazz) { 
		super(clazz);
		this.clazz = clazz; 
	}
	
	
	public static DepartmentDAOFacade<Department> getInstance() {
		synchronized(lock) {
			if (departmentDAOFacade == null)
				departmentDAOFacade = new DepartmentDAO();
		}
	
		return departmentDAOFacade;
	}
	
	
	
	
	public void save(final Department entity) {
		
		if (entity.getId() != null && entity.getDepartment() != null) {
			
			this.queryString = QUERY_STRING_SAVE_DEPARTMENT_BY_ENTITY;
			this.values = new Object[] { entity.getId(), entity.getDepartment() };

			super.save(entity);
		}
	}
	
	public void delete(final Department entity) {
		super.delete(entity);
	}
	
	public void delete(final Long id) {
		super.delete(id);
	}

	public Department update(final Department entity) {
		
		Department department = null;
		
		if (entity.getId() != null && entity.getDepartment() != null) {
			
			this.queryString = QUERY_STRING_UPDATE_DEPARTMENT_BY_ENTITY;
			this.values = new Object[] { entity.getDepartment(), entity.getId() };

			department = super.update(entity);
		}
		
		return department;
	}

	public Department findById(final Long id) {
		return super.findById(id);
	}
	
	public List<Department> findByDepartment(final Object department, final int... rowStartIdxAndCount) {
		return super.findByProperty("department", department, rowStartIdxAndCount);
	}

	public List<Department> findAll(final int... rowStartIdxAndCount) {
		return super.findAll(rowStartIdxAndCount);
	}
	
	public List<Department> getAll() {
		return super.getAll();
	}
}