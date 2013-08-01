package com.gabon.info.run.dao.hibernate;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gabon.info.dao.hibernate.department.DepartmentDAO;
import com.gabon.info.dao.hibernate.department.DepartmentDAOFacade;
import com.gabon.info.dao.hibernate.office.OfficeDAO;
import com.gabon.info.dao.hibernate.office.OfficeDAOFacade;
import com.gabon.info.dao.hibernate.projects.ProjectsDAO;
import com.gabon.info.dao.hibernate.projects.ProjectsDAOFacade;
import com.gabon.info.dao.hibernate.roles.RolesDAO;
import com.gabon.info.dao.hibernate.roles.RolesDAOFacade;
import com.gabon.info.dao.hibernate.users.UsersDAO;
import com.gabon.info.dao.hibernate.users.UsersDAOFacade;
import com.gabon.info.model.department.Department;
import com.gabon.info.model.office.Office;
import com.gabon.info.model.projects.Projects;
import com.gabon.info.model.roles.Roles;
import com.gabon.info.model.users.Users;
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

public class HibernateDAO implements Constants {

	private static final long serialVersionUID = 7152176377366845848L;
	
	
	private static final  Logger logger = Logger.getLogger(HibernateDAO.class.getName());
	
	
	private static final UsersDAOFacade<Users> usersDAOFacade = UsersDAO.getInstance();

	private static final RolesDAOFacade<Roles> rolesDAOFacade = RolesDAO.getInstance();
	
	private static final ProjectsDAOFacade<Projects> projectsDAOFacade = ProjectsDAO.getInstance();
	
	private static final DepartmentDAOFacade<Department> departmentDAOFacade = DepartmentDAO.getInstance();
	
	private static final OfficeDAOFacade<Office> officeDAOFacade = OfficeDAO.getInstance();

	
	public HibernateDAO() {}

	
	public static void main(String[] args) {
		
		logger.log(Level.INFO, "Start Test");
		
		
		
		logger.log(Level.INFO, "---------------------------------------------------------------------- save ----------------------------------------------------------------------");
		
		departmentDAOFacade.save(FIVE_DEPARTMENT);
		System.out.println("Test departmentDAOFacade.save OK");
		
		officeDAOFacade.save(FIVE_OFFICE);
		System.out.println("Test officeDAOFacade.save OK");
		
		projectsDAOFacade.save(FIVE_PROJECTS);
		System.out.println("Test projectsDAOFacade.save OK");
		
		usersDAOFacade.save(FIVE_USERS);
		System.out.println("Test usersDAOFacade.save OK");
		
		rolesDAOFacade.save(FIVE_ROLES);
		System.out.println("Test rolesDAOFacade.save OK");
		
		
		logger.log(Level.INFO, "---------------------------------------------------------------------- findById ----------------------------------------------------------------------");
		
		final Department departmentFindById = departmentDAOFacade.findById(FIVE_ID);
		System.out.println("Test departmentFindById : " +departmentFindById);
		
		final Office officeFindById = officeDAOFacade.findById(FIVE_ID);
		System.out.println("Test officeFindById : " +officeFindById);
		
		final Projects projectsFindById = projectsDAOFacade.findById(FIVE_ID);
		System.out.println("Test projectsFindById : " +projectsFindById);
		
		final Users usersFindById = usersDAOFacade.findById(FIVE_ID);
		System.out.println("Test usersFindById : " +usersFindById);
		
		final Roles rolesFindById = rolesDAOFacade.findById(FIVE_ID);
		System.out.println("Test rolesFindById : " +rolesFindById);
		
		
		logger.log(Level.INFO, "---------------------------------------------------------------------- update ----------------------------------------------------------------------");
		
		final Department departmentUpdateEntity = departmentDAOFacade.update(SIX_DEPARTMENT);
		System.out.println("Test departmentUpdateEntity : " +departmentUpdateEntity);
		
		final Office officeUpdateEntity = officeDAOFacade.update(SIX_OFFICE);
		System.out.println("Test officeUpdateEntity : " +officeUpdateEntity);
		
		final Projects projectsUpdateEntity = projectsDAOFacade.update(SIX_PROJECTS);
		System.out.println("Test projectsUpdateEntity : " +projectsUpdateEntity);
		
		final Users usersUpdateEntity = usersDAOFacade.update(SIX_USERS);
		System.out.println("Test usersUpdateEntity : " +usersUpdateEntity);
		
		final Roles rolesUpdateEntity = rolesDAOFacade.update(SIX_ROLES);
		System.out.println("Test rolesUpdateEntity : " +rolesUpdateEntity);
		
		
		logger.log(Level.INFO, "---------------------------------------------------------------------- delete by entity ----------------------------------------------------------------------");
		
		rolesDAOFacade.delete(FIVE_ROLES);
		System.out.println("Test rolesDAOFacade.delete OK");
		
		usersDAOFacade.delete(FIVE_USERS);
		System.out.println("Test usersDAOFacade.delete OK");
		
		departmentDAOFacade.delete(FIVE_DEPARTMENT);
		System.out.println("Test departmentDAOFacade.delete OK");
	
		officeDAOFacade.delete(FIVE_OFFICE);
		System.out.println("Test officeDAOFacade.delete OK");
		
		projectsDAOFacade.delete(FIVE_PROJECTS);
		System.out.println("Test projectsDAOFacade.delete OK");
		
		
		logger.log(Level.INFO, "---------------------------------------------------------------------- delete by id ----------------------------------------------------------------------");
	
		rolesDAOFacade.delete(ONE_ID);
		System.out.println("Test rolesDAOFacade.delete OK");
		
		usersDAOFacade.delete(ONE_ID);
		System.out.println("Test usersDAOFacade.delete OK");
		
		departmentDAOFacade.delete(ONE_ID);
		System.out.println("Test departmentDAOFacade.delete OK");
			
		officeDAOFacade.delete(ONE_ID);
		System.out.println("Test officeDAOFacade.delete OK");
		
		projectsDAOFacade.delete(ONE_ID);
		System.out.println("Test projectsDAOFacade.delete OK");
	
		
		logger.log(Level.INFO, "---------------------------------------------------------------------- findByProperty ----------------------------------------------------------------------");
		
		final List<Department> departmentFindByDepartment = departmentDAOFacade.findByDepartment(TWO_DEPARTMENT_ID, 0);
		System.out.println("Test departmentFindByDepartment : " +departmentFindByDepartment);
		
		final List<Office> officeFindByOffice = officeDAOFacade.findByOffice(TWO_OFFICE_ID, 0);
		System.out.println("Test officeFindByOffice : " +officeFindByOffice);
		
		final List<Projects> projectsFindByProject = projectsDAOFacade.findByProject(TWO_PROJECTS_ID, 0);
		System.out.println("Test projectsFindByProject : " +projectsFindByProject);

		final List<Users> usersFindByName = usersDAOFacade.findByName(TWO_NAME_ID, 0);
		System.out.println("Test usersFindByName : " +usersFindByName);
		
		final List<Users> usersFindByEmail = usersDAOFacade.findByEmail(TWO_EMAIL_ID, 0);
		System.out.println("Test usersFindByEmail : " +usersFindByEmail);
		
		final List<Roles> rolesFindByRole = rolesDAOFacade.findByRole(TWO_ROLE_ID, 0);
		System.out.println("Test rolesFindByRole : " +rolesFindByRole);
		
		
		logger.log(Level.INFO, "---------------------------------------------------------------------- findAll ----------------------------------------------------------------------");
		
		final List<Department> departmentFindAll = departmentDAOFacade.findAll(1);
		System.out.println("Test departmentFindAll : " +departmentFindAll);
		
		final List<Office> officeFindAll = officeDAOFacade.findAll(2);
		System.out.println("Test officeFindAll : " +officeFindAll);
		
		final List<Projects> projectsFindAll = projectsDAOFacade.findAll(3);
		System.out.println("Test projectsFindAll : " +projectsFindAll);
		
		final List<Users> usersFindAll = usersDAOFacade.findAll(4);
		System.out.println("Test usersFindAll : " +usersFindAll);
		
		final List<Roles> rolesFindAll = rolesDAOFacade.findAll(5);
		System.out.println("Test rolesFindAll : " +rolesFindAll);
		
		
		logger.log(Level.INFO, "---------------------------------------------------------------------- getAll ----------------------------------------------------------------------");
		
		final List<Department> departmentGetAll = departmentDAOFacade.getAll();
		System.out.println("Test departmentGetAll : " +departmentGetAll);
	
		final List<Office> officeGetAll = officeDAOFacade.getAll();
		System.out.println("Test officeGetAll : " +officeGetAll);
		
		final List<Projects> projectsGetAll = projectsDAOFacade.getAll();
		System.out.println("Test projectsGetAll : " +projectsGetAll);
		
		final List<Users> usersGetAll = usersDAOFacade.getAll();
		System.out.println("Test usersGetAll : " +usersGetAll);
		
		final List<Roles> rolesGetAll = rolesDAOFacade.getAll();
		System.out.println("Test rolesGetAll : " +rolesGetAll);
		
		
		
		logger.log(Level.INFO, "End Test");
	}
}