package com.gabon.info.server.dao;

import java.io.Serializable;

import com.gabon.info.server.model.department.Department;
import com.gabon.info.server.model.office.Office;
import com.gabon.info.server.model.projects.Projects;
import com.gabon.info.server.model.roles.Roles;
import com.gabon.info.server.model.users.Users;
import com.gabon.info.server.util.Constants;

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
 * The abstract factory pattern is a software creational design 
 * pattern that provides a way to encapsulate a group of individual 
 * factories that have a common theme without specifying their concrete classes.
 * In normal usage, the client software creates a concrete implementation of the 
 * abstract factory and then uses the generic interface of the factory 
 * to create the concrete objects that are part of the theme.
 * 
 */
public interface AbstractDAOFactory<E> extends Constants, Serializable {
	
	DAOFacade<Department> createDepartmentDAO();

	DAOFacade<Office> createOfficeDAO();
	
	DAOFacade<Projects> createProjectsDAO();

	DAOFacade<Roles> createRolesDAO();
	
	DAOFacade<Users> createUsersDAO();
}