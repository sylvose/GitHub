package com.gabon.info.dao.spring.hibernate.department;

import static com.gabon.info.util.Constants.BEAN_DEPARTMENT_DAO_JDBC_SPRING;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.dao.spring.hibernate.ConcreteDAOHibernateSpring;
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
@Repository(BEAN_DEPARTMENT_DAO_JDBC_SPRING)
public class DepartmentDAO extends ConcreteDAOHibernateSpring<Department, Long> implements DepartmentDAOFacade<Department> {

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