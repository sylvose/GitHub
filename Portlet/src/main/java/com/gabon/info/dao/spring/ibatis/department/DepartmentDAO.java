package com.gabon.info.dao.spring.ibatis.department;

import static com.gabon.info.util.Constants.BEAN_DEPARTMENT_DAO_IBATIS_SPRING;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.dao.spring.ibatis.ConcreteDAOIBatisSpring;
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

@Transactional
@Repository(BEAN_DEPARTMENT_DAO_IBATIS_SPRING)
public class DepartmentDAO extends ConcreteDAOIBatisSpring<Department, Long> implements DepartmentDAOFacade<Department> {

	private static final long serialVersionUID = -3937788254521108651L;
	
	
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
		this.statement = SAVE_DEPARTMENT;
		super.save(entity);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(final Department entity) {
		this.statement = DELETE_DEPARTMENT;
		super.delete(entity);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(final Long id) {
		this.statement = DELETE_DEPARTMENT;
		super.delete(id);
	}

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Department update(final Department entity) {
		this.statement = UPDATE_DEPARTMENT;
		return super.update(entity);
	}

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Department findById(final Long id) {
		this.statement = FIND_BY_ID_DEPARTMENT;
		return super.findById(id);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<Department> findByDepartment(final Object department, final int... rowStartIdxAndCount) {
		this.statement = FIND_BY_PROPERTY_DEPARTMENT;
		return super.findByProperty("department", department, rowStartIdxAndCount);
	}

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<Department> findAll(final int... rowStartIdxAndCount) {
		this.statement = FIND_ALL_DEPARTMENT;
		return super.findAll(rowStartIdxAndCount);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<Department> getAll() {
		this.statement = GET_ALL_DEPARTMENT;
		return super.getAll();
	}
}