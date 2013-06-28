package com.gabon.info.server.dao.jdbc;

import com.gabon.info.server.dao.AbstractDAOFactory;
import com.gabon.info.server.dao.DAOFacade;
import com.gabon.info.server.dao.jdbc.department.DepartmentDAO;
import com.gabon.info.server.dao.jdbc.office.OfficeDAO;
import com.gabon.info.server.dao.jdbc.projects.ProjectsDAO;
import com.gabon.info.server.dao.jdbc.roles.RolesDAO;
import com.gabon.info.server.dao.jdbc.users.UsersDAO;
import com.gabon.info.server.dao.jdbc.users.UsersDAOFacade;
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
public class ConcreteDAOJdbc<E> extends AbstractJdbcDAO<E> implements AbstractDAOFactory<E> {
	
	private static final long serialVersionUID = -7505690593811500695L;

	 
	public ConcreteDAOJdbc() {
		super(); 
	}

	public ConcreteDAOJdbc(Class<E> clazz) { 
		super(clazz);
		this.clazz = clazz; 
	}
	
	
	public DAOFacade<Department> createDepartmentDAO() {
		return DepartmentDAO.getInstance();
	}

	public DAOFacade<Office> createOfficeDAO() {
		return OfficeDAO.getInstance();
	}
	
	public DAOFacade<Projects> createProjectsDAO() {
		return ProjectsDAO.getInstance();
	}

	public DAOFacade<Roles> createRolesDAO() {
		return RolesDAO.getInstance();
	}
	
	public UsersDAOFacade<Users> createUsersDAO() {
		return UsersDAO.getInstance();
	}
}