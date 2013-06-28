package com.gabon.info.server.dao.spring.jdbc;

import static com.gabon.info.server.util.Constants.BEAN_CONCRETE_DAO_JDBC_SPRING;

import java.io.Serializable;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.server.dao.AbstractDAOFactory;
import com.gabon.info.server.dao.DAOFacade;
import com.gabon.info.server.model.department.Department;
import com.gabon.info.server.model.office.Office;
import com.gabon.info.server.model.projects.Projects;
import com.gabon.info.server.model.roles.Roles;
import com.gabon.info.server.model.users.Users;

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
 * added to each of these methods for data to be persisted to the JDBC datastore.
 * 
 */

@Transactional
@Repository(BEAN_CONCRETE_DAO_JDBC_SPRING)
public class ConcreteDAOJdbcSpring<E, PK extends Serializable> extends AbstractJdbcDaoSupport<E, PK> implements AbstractDAOFactory<E> {	
	
	private static final long serialVersionUID = -662565075298812864L;

	
	private DAOFacade<Department> departmentDAOFacade;
	
	private DAOFacade<Office> officeDAOFacade;
	
	private DAOFacade<Projects> projectsDAOFacade;
	
	private DAOFacade<Roles> rolesDAOFacade;
	
	private DAOFacade<Users> usersDAOFacade;
	
	
	
	public ConcreteDAOJdbcSpring() {
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