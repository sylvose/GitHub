package com.gabon.info.dao.hibernate;

import com.gabon.info.dao.AbstractDAOFactory;
import com.gabon.info.dao.DAOFacade;
import com.gabon.info.dao.hibernate.department.DepartmentDAO;
import com.gabon.info.dao.hibernate.office.OfficeDAO;
import com.gabon.info.dao.hibernate.projects.ProjectsDAO;
import com.gabon.info.dao.hibernate.roles.RolesDAO;
import com.gabon.info.dao.hibernate.users.UsersDAO;
import com.gabon.info.dao.hibernate.users.UsersDAOFacade;
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
public class ConcreteDAOHibernate<E> extends AbstractHibernateDAO<E> implements AbstractDAOFactory<E> {		
	
	private static final long serialVersionUID = -7505690593811500695L;

	
	public ConcreteDAOHibernate() {
		super(); 
	}

	public ConcreteDAOHibernate(Class<E> clazz) { 
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