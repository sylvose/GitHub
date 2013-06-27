package com.gabon.info.dao.jpa.department;

import java.util.List;

import com.gabon.info.dao.jpa.ConcreteDAOJpa;
import com.gabon.info.model.department.Department;

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

public class DepartmentDAO extends ConcreteDAOJpa<Department> implements DepartmentDAOFacade<Department> {

	private static final long serialVersionUID = -3937788254521108651L;
	
	
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
		super.save(entity);
	}
	
	public void delete(final Department entity) {
		super.delete(entity);
	}
	
	public void delete(final Long id) {
		super.delete(id);
	}

	public Department update(final Department entity) {
		return super.update(entity);
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