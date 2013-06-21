package com.gabon.info.model.department;

import static com.gabon.info.util.RestFulConstants.BEAN_DEPARTMENT;
import static com.gabon.info.util.RestFulConstants.DEPARTMENT_SEQ;
import static com.gabon.info.util.RestFulConstants.DEPARTMENT_SEQ_GEN;
import static com.gabon.info.util.RestFulConstants.Department;
import static com.gabon.info.util.RestFulConstants.GET_ALL_DEPARTMENT_NAME;
import static com.gabon.info.util.RestFulConstants.GET_DEPARTMENT_QUERY;
import static com.gabon.info.util.RestFulConstants.T_DEPARTMENT;
import static com.gabon.info.util.RestFulConstants.UQAM;

import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.gabon.info.model.users.Users;

/*
 * This class is a model class.
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

@Component(BEAN_DEPARTMENT)

@Entity(name = Department)
@Table(name = T_DEPARTMENT, schema = UQAM)
@SequenceGenerator(name = DEPARTMENT_SEQ_GEN, sequenceName = DEPARTMENT_SEQ, initialValue = 1, allocationSize = 1)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

@NamedQueries({ 
	@NamedQuery(name = GET_ALL_DEPARTMENT_NAME, query = GET_DEPARTMENT_QUERY)
})

@Cacheable(true)

public final class Department extends AbstractDepartment implements DepartmentFacade {

	private static final long serialVersionUID = 6618471486695230383L;

	
	// Constructors
	
	/** empty constructor */
	public Department() {
		super();
	}

	/** full constructor */
	public Department(Long departmentid, String department, Set<Users> users) {
		super(departmentid, department, users);
	}
	
	/** default constructor */
	public Department(Long departmentid, String department) {
		super(departmentid, department);
	}
}