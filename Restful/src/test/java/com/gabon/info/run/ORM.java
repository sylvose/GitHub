package com.gabon.info.run;

import com.gabon.info.dao.AbstractDAOFactory;
import com.gabon.info.dao.DAOFacade;
import com.gabon.info.dao.jpa.ConcreteDAOJpa;
import com.gabon.info.dao.spring.jpa.ConcreteDAOJpaSpring;
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
 * added to each of these methods for data to be persisted to the JPA datastore.
 * 
 */

public class ORM<T> implements AbstractDAOFactory<T> {	
	
	private static final long serialVersionUID = 1346557290296788523L;
	
	
	private static AbstractDAOFactory<Object> daoFactory;
	
	
	private DAOFacade<Department> departmentDAOFacade;
	
	private DAOFacade<Office> officeDAOFacade;
	
	private DAOFacade<Projects> projectsDAOFacade;
	
	private DAOFacade<Roles> rolesDAOFacade;
	
	private DAOFacade<Users> usersDAOFacade;
	
	
	
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
	
	
	
	public static AbstractDAOFactory<Object> getAbstractDAOFactory(int frameWork) {
		
		switch (frameWork) {
		
				case CONCRETE_DAO_JPA:
					daoFactory = new ConcreteDAOJpa<Object>();
					break;
				case CONCRETE_DAO_JPA_SPRING:
					daoFactory = new ConcreteDAOJpaSpring<Object, Long>();
					break;
					
				default: 
					daoFactory = new ConcreteDAOJpaSpring<Object, Long>();
				break;
		}
		
		return daoFactory;
	}


	public static AbstractDAOFactory<Object> getDaoFactory() {
		return daoFactory;
	}

	public static void setDaoFactory(AbstractDAOFactory<Object> daoFactory) {
		ORM.daoFactory = daoFactory;
	}
}