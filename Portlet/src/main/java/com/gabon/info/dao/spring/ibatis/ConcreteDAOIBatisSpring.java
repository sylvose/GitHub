package com.gabon.info.dao.spring.ibatis;

import static com.gabon.info.util.Constants.BEAN_CONCRETE_DAO_IBATIS_SPRING;

import java.io.Serializable;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.dao.AbstractDAOFactory;
import com.gabon.info.dao.DAOFacade;
import com.gabon.info.model.department.Department;
import com.gabon.info.model.office.Office;
import com.gabon.info.model.projects.Projects;
import com.gabon.info.model.roles.Roles;
import com.gabon.info.model.users.Users;

/*
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
 * A data access object (DAO) providing persistence and search support for User
 * entities. Transaction control of the save(), update() and delete() operations
 * must be handled externally by senders of these methods or must be manually
 * added to each of these methods for data to be persisted to the Hibernate datastore.
 * 
 */

@Transactional
@Repository(BEAN_CONCRETE_DAO_IBATIS_SPRING)
public class ConcreteDAOIBatisSpring<E, PK extends Serializable> extends AbstractIBatisDaoSupport<E, PK> implements AbstractDAOFactory<E> {
	
	private static final long serialVersionUID = 2810647357661718963L;

	
	private DAOFacade<Department> departmentDAOFacade;
	
	private DAOFacade<Office> officeDAOFacade;
	
	private DAOFacade<Projects> projectsDAOFacade;
	
	private DAOFacade<Roles> rolesDAOFacade;
	
	private DAOFacade<Users> usersDAOFacade;
	
	
	
	public ConcreteDAOIBatisSpring() {
		super(); 
	}
	

	
	public DAOFacade<Department> createDepartmentDAO() {
		return departmentDAOFacade;
	}
	
	public DAOFacade<Office> createOfficeDAO() {
		return officeDAOFacade;
	}
	
	public DAOFacade<Projects> createProjectsDAO() {
		return projectsDAOFacade;
	}
	
	public DAOFacade<Roles> createRolesDAO() {
		return rolesDAOFacade;
	}
	
	public DAOFacade<Users> createUsersDAO() {
		return usersDAOFacade;
	}
}