package com.gabon.info.dao.ibatis.department;

import java.util.List;

import com.gabon.info.dao.ibatis.ConcreteDAOIBatis;
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
public class DepartmentDAO extends ConcreteDAOIBatis<Department> implements DepartmentDAOFacade<Department> {

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
		this.statement = SAVE_DEPARTMENT;
		super.save(entity);
	}
	
	public void delete(final Department entity) {
		this.statement = DELETE_DEPARTMENT;
		super.delete(entity);
	}
	
	public void delete(final Long id) {
		this.statement = DELETE_DEPARTMENT;
		super.delete(id);
	}

	public Department update(final Department entity) {
		this.statement = UPDATE_DEPARTMENT;
		return super.update(entity);
	}

	public Department findById(final Long id) {
		this.statement = FIND_BY_ID_DEPARTMENT;
		return super.findById(id);
	}
	
	public List<Department> findByDepartment(final Object department, final int... rowStartIdxAndCount) {
		this.statement = FIND_BY_PROPERTY_DEPARTMENT;
		return super.findByProperty("department", department, rowStartIdxAndCount);
	}

	public List<Department> findAll(final int... rowStartIdxAndCount) {
		this.statement = FIND_ALL_DEPARTMENT;
		return super.findAll(rowStartIdxAndCount);
	}
	
	public List<Department> getAll() {
		this.statement = GET_ALL_DEPARTMENT;
		return super.getAll();
	}
}