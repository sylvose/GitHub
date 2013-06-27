package com.gabon.info.run.resource;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.Response;

import com.gabon.info.model.department.Department;
import com.gabon.info.model.office.Office;
import com.gabon.info.model.projects.Projects;
import com.gabon.info.model.roles.Roles;
import com.gabon.info.model.users.Users;
import com.gabon.info.resource.department.DepartmentResource;
import com.gabon.info.resource.department.DepartmentResourceFacade;
import com.gabon.info.resource.office.OfficeResource;
import com.gabon.info.resource.office.OfficeResourceFacade;
import com.gabon.info.resource.projects.ProjectsResource;
import com.gabon.info.resource.projects.ProjectsResourceFacade;
import com.gabon.info.resource.roles.RolesResource;
import com.gabon.info.resource.roles.RolesResourceFacade;
import com.gabon.info.resource.users.UsersResource;
import com.gabon.info.resource.users.UsersResourceFacade;
import com.gabon.info.util.Constants;

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
 */

public class Resource implements Constants {

	private static final long serialVersionUID = 7152176377366845848L;
	
	
	private static final  Logger logger = Logger.getLogger(Resource.class.getName());
	
	
	private static final UsersResourceFacade<Response, Users> usersResourceFacade = new UsersResource();

	private static final RolesResourceFacade<Response, Roles> rolesResourceFacade = new RolesResource();
	
	private static final ProjectsResourceFacade<Response, Projects> projectsResourceFacade = new ProjectsResource();
	
	private static final DepartmentResourceFacade<Response, Department> departmentResourceFacade = new DepartmentResource();
	
	private static final OfficeResourceFacade<Response, Office> officeResourceFacade = new OfficeResource();

	
	public Resource() {}

	
	public static void main(String[] args) {
		
		logger.log(Level.INFO, "Start Test");
		
		
		
		logger.log(Level.INFO, "---------------------------------------------------------------------- save ----------------------------------------------------------------------");
		
		departmentResourceFacade.save(FIVE_DEPARTMENT);
		System.out.println("Test departmentResourceFacade.save OK");
		
		officeResourceFacade.save(FIVE_OFFICE);
		System.out.println("Test officeResourceFacade.save OK");
		
		projectsResourceFacade.save(FIVE_PROJECTS);
		System.out.println("Test projectsResourceFacade.save OK");
		
		usersResourceFacade.save(FIVE_USERS);
		System.out.println("Test usersResourceFacade.save OK");
		
		rolesResourceFacade.save(FIVE_ROLES);
		System.out.println("Test rolesResourceFacade.save OK");
		
		
		logger.log(Level.INFO, "---------------------------------------------------------------------- findById ----------------------------------------------------------------------");
		
		final Response departmentFindById = departmentResourceFacade.findById(FIVE_ID);
		System.out.println("Test departmentFindById : " +departmentFindById);
		
		final Response officeFindById = officeResourceFacade.findById(FIVE_ID);
		System.out.println("Test officeFindById : " +officeFindById);
		
		final Response projectsFindById = projectsResourceFacade.findById(FIVE_ID);
		System.out.println("Test projectsFindById : " +projectsFindById);
		
		final Response usersFindById = usersResourceFacade.findById(FIVE_ID);
		System.out.println("Test usersFindById : " +usersFindById);
		
		final Response rolesFindById = rolesResourceFacade.findById(FIVE_ID);
		System.out.println("Test rolesFindById : " +rolesFindById);
		
		
		logger.log(Level.INFO, "---------------------------------------------------------------------- update ----------------------------------------------------------------------");
		
		final Response departmentUpdateEntity = departmentResourceFacade.update(SIX_DEPARTMENT);
		System.out.println("Test departmentUpdateEntity : " +departmentUpdateEntity);
		
		final Response officeUpdateEntity = officeResourceFacade.update(SIX_OFFICE);
		System.out.println("Test officeUpdateEntity : " +officeUpdateEntity);
		
		final Response projectsUpdateEntity = projectsResourceFacade.update(SIX_PROJECTS);
		System.out.println("Test projectsUpdateEntity : " +projectsUpdateEntity);
		
		final Response usersUpdateEntity = usersResourceFacade.update(SIX_USERS);
		System.out.println("Test usersUpdateEntity : " +usersUpdateEntity);
		
		final Response rolesUpdateEntity = rolesResourceFacade.update(SIX_ROLES);
		System.out.println("Test rolesUpdateEntity : " +rolesUpdateEntity);
		
		
		logger.log(Level.INFO, "---------------------------------------------------------------------- delete by entity ----------------------------------------------------------------------");
		
		rolesResourceFacade.delete(FIVE_ROLES);
		System.out.println("Test rolesResourceFacade.delete OK");
		
		usersResourceFacade.delete(FIVE_USERS);
		System.out.println("Test usersResourceFacade.delete OK");
		
		departmentResourceFacade.delete(FIVE_DEPARTMENT);
		System.out.println("Test departmentResourceFacade.delete OK");
	
		officeResourceFacade.delete(FIVE_OFFICE);
		System.out.println("Test officeResourceFacade.delete OK");
		
		projectsResourceFacade.delete(FIVE_PROJECTS);
		System.out.println("Test projectsResourceFacade.delete OK");
		
		
		logger.log(Level.INFO, "---------------------------------------------------------------------- delete by id ----------------------------------------------------------------------");
	
		rolesResourceFacade.delete(ONE_ID);
		System.out.println("Test rolesResourceFacade.delete OK");
		
		usersResourceFacade.delete(ONE_ID);
		System.out.println("Test usersResourceFacade.delete OK");
		
		departmentResourceFacade.delete(ONE_ID);
		System.out.println("Test departmentResourceFacade.delete OK");
			
		officeResourceFacade.delete(ONE_ID);
		System.out.println("Test officeResourceFacade.delete OK");
		
		projectsResourceFacade.delete(ONE_ID);
		System.out.println("Test projectsResourceFacade.delete OK");
	
		
		logger.log(Level.INFO, "---------------------------------------------------------------------- findByProperty ----------------------------------------------------------------------");
		
		final Response departmentFindByDepartment = departmentResourceFacade.findByDepartment(TWO_DEPARTMENT_ID, 0);
		System.out.println("Test departmentFindByDepartment : " +departmentFindByDepartment);
		
		final Response officeFindByOffice = officeResourceFacade.findByOffice(TWO_OFFICE_ID, 0);
		System.out.println("Test officeFindByOffice : " +officeFindByOffice);
		
		final Response projectsFindByProject = projectsResourceFacade.findByProject(TWO_PROJECTS_ID, 0);
		System.out.println("Test projectsFindByProject : " +projectsFindByProject);

		final Response usersFindByName = usersResourceFacade.findByName(TWO_NAME_ID, 0);
		System.out.println("Test usersFindByName : " +usersFindByName);
		
		final Response usersFindByEmail = usersResourceFacade.findByEmail(TWO_EMAIL_ID, 0);
		System.out.println("Test usersFindByEmail : " +usersFindByEmail);
		
		final Response rolesFindByRole = rolesResourceFacade.findByRole(TWO_ROLE_ID, 0);
		System.out.println("Test rolesFindByRole : " +rolesFindByRole);
		
		
		logger.log(Level.INFO, "---------------------------------------------------------------------- findAll ----------------------------------------------------------------------");
		
		final Response departmentFindAll = departmentResourceFacade.findAll(1);
		System.out.println("Test departmentFindAll : " +departmentFindAll);
		
		final Response officeFindAll = officeResourceFacade.findAll(2);
		System.out.println("Test officeFindAll : " +officeFindAll);
		
		final Response projectsFindAll = projectsResourceFacade.findAll(3);
		System.out.println("Test projectsFindAll : " +projectsFindAll);
		
		final Response usersFindAll = usersResourceFacade.findAll(4);
		System.out.println("Test usersFindAll : " +usersFindAll);
		
		final Response rolesFindAll = rolesResourceFacade.findAll(5);
		System.out.println("Test rolesFindAll : " +rolesFindAll);
		
		
		logger.log(Level.INFO, "---------------------------------------------------------------------- getAll ----------------------------------------------------------------------");
		
		final Response departmentGetAll = departmentResourceFacade.getAll();
		System.out.println("Test departmentGetAll : " +departmentGetAll);
	
		final Response officeGetAll = officeResourceFacade.getAll();
		System.out.println("Test officeGetAll : " +officeGetAll);
		
		final Response projectsGetAll = projectsResourceFacade.getAll();
		System.out.println("Test projectsGetAll : " +projectsGetAll);
		
		final Response usersGetAll = usersResourceFacade.getAll();
		System.out.println("Test usersGetAll : " +usersGetAll);
		
		final Response rolesGetAll = rolesResourceFacade.getAll();
		System.out.println("Test rolesGetAll : " +rolesGetAll);
		
		
		
		logger.log(Level.INFO, "End Test");
	}
}