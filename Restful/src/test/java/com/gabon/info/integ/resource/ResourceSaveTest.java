package com.gabon.info.integ.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gabon.info.integ.AbstractIntegTest;
import com.gabon.info.model.department.Department;
import com.gabon.info.model.office.Office;
import com.gabon.info.model.projects.Projects;
import com.gabon.info.model.roles.Roles;
import com.gabon.info.model.users.Users;
import com.gabon.info.resource.department.DepartmentResourceFacade;
import com.gabon.info.resource.office.OfficeResourceFacade;
import com.gabon.info.resource.projects.ProjectsResourceFacade;
import com.gabon.info.resource.roles.RolesResourceFacade;
import com.gabon.info.resource.users.UsersResourceFacade;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

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

public class ResourceSaveTest extends AbstractIntegTest {

	private static final long serialVersionUID = 2548040851478523967L;

	
	
	@Autowired
	private DepartmentResourceFacade<Response, Department> departmentResourceFacade;
	
	@Autowired
	private OfficeResourceFacade<Response, Office> officeResourceFacade;
	
	@Autowired
	private ProjectsResourceFacade<Response, Projects> projectsResourceFacade;

	@Autowired
	private UsersResourceFacade<Response, Users> usersResourceFacade;
	
	@Autowired
	private RolesResourceFacade<Response, Roles> rolesResourceFacade;
	
	
	
	public ResourceSaveTest() {
		super();
	}
	
	
	@SuppressWarnings("unchecked")
	@Before
	/**
	 * Purpose:
	 * Prerequisites:
	 * Description: 
	 * Expected result: 
	 * @TEST_STATUS	        Ready
	 * @DESIGNER            Sylvose ALLOGO
	 * @TEST_TYPE	        Unit integration
	 * @TEST_COVERAGE       
	 * @TEST_DESTINATION    
	 * 
	 * @throws java.lang.Exception
	 */
	public void setUp() throws Exception {
		 super.setUp();
		 
		 this.departmentResourceFacade = (DepartmentResourceFacade<Response, Department>) applicationContext.getBean(BEAN_DEPARTMENT_RESOURCE);
		 assertNotNull(departmentResourceFacade);	
		 assertTrue(departmentResourceFacade instanceof DepartmentResourceFacade);
		 
		 this.officeResourceFacade = (OfficeResourceFacade<Response, Office>) applicationContext.getBean(BEAN_OFFICE_RESOURCE);
		 assertNotNull(officeResourceFacade);	
		 assertTrue(officeResourceFacade instanceof OfficeResourceFacade);
		 
		 this.projectsResourceFacade = (ProjectsResourceFacade<Response, Projects>) applicationContext.getBean(BEAN_PROJECTS_RESOURCE);
		 assertNotNull(projectsResourceFacade);	
		 assertTrue(projectsResourceFacade instanceof ProjectsResourceFacade);
		 
		 this.usersResourceFacade = (UsersResourceFacade<Response, Users>) applicationContext.getBean(BEAN_USERS_RESOURCE);
		 assertNotNull(usersResourceFacade);	
		 assertTrue(usersResourceFacade instanceof UsersResourceFacade);
		 
		 this.rolesResourceFacade = (RolesResourceFacade<Response, Roles>) applicationContext.getBean(BEAN_ROLES_RESOURCE);
		 assertNotNull(usersResourceFacade);	
		 assertTrue(rolesResourceFacade instanceof RolesResourceFacade);
	}

	/**
	 * Test method save
	 *
	 */
	// @Ignore("Not Ready, to be changed")
	@Test
	/**
	 * Purpose:				
	 * 						
	 * Prerequisites:           
	 * Description:         
	 * Expected result:     
	 * @TEST_STATUS	        Ready
	 * @DESIGNER            Sylvose ALLOGO
	 * @TEST_TYPE	        
	 * @TEST_COVERAGE       
	 * @TEST_DESTINATION    
	 */
	public void testDepartmentResourceSave() throws Exception {

		try {
			assertNotNull(FIVE_DEPARTMENT);	
			assertTrue(FIVE_DEPARTMENT instanceof Department);
			
			final Response response = this.departmentResourceFacade.save(FIVE_DEPARTMENT);
			
			assertNotNull(response);	
			assertTrue(response instanceof Response);
			
			
			final int status = response.getStatus();
			
			if ((status >= 200) && (status <= 299)) {
				assertEquals(200, status);
				logger.debug(SAVE_SUCCESSFUL);
			} else {
				assertEquals(500, status);
				logger.debug(SAVE_FAILED);
			}
			
			
			final String URI_PATH = URL_WEB_SERVICE_PROJECT_ON_THE_SERVER + "/" + URI_PATH_DEPARTMENTS + "/" + URI_PATH_FIND_BY_ID + "/" + FIVE_ID;
			
			assertNotNull(URI_PATH);	
			assertTrue(URI_PATH instanceof String);
			
			this.webResource = this.client.resource(URI_PATH);
			
			assertNotNull(this.webResource);	
			assertTrue(this.webResource instanceof WebResource);
			
			this.clientResponse = this.webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
			assertNotNull(this.clientResponse);	
			assertTrue(this.clientResponse instanceof ClientResponse);
			
			logger.debug("status : " +status);
				
		} catch (UniformInterfaceException e) { 
			logger.debug("status : " + e.getResponse().getClientResponseStatus().getStatusCode());
			
		} catch (ClientHandlerException e) { 
			logger.debug(MESSAGE + e.getMessage(), e);
		}
	}
	
	/**
	 * Test method save
	 * 
	 */
	// @Ignore("Not Ready, to be changed")
	@Test
	/**
	 * Purpose:				
	 * 						
	 * Prerequisites:           
	 * Description:         
	 * Expected result:     
	 * @TEST_STATUS	        Ready
	 * @DESIGNER            Sylvose ALLOGO
	 * @TEST_TYPE	        
	 * @TEST_COVERAGE       
	 * @TEST_DESTINATION    
	 */
	public void testOfficeResourceSave() throws Exception {

		try {
			assertNotNull(FIVE_OFFICE);	
			assertTrue(FIVE_OFFICE instanceof Office);
			
			final Response response = this.officeResourceFacade.save(FIVE_OFFICE);
			
			assertNotNull(response);	
			assertTrue(response instanceof Response);
			
			
			final int status = response.getStatus();
			
			if ((status >= 200) && (status <= 299)) {
				assertEquals(200, status);
				logger.debug(SAVE_SUCCESSFUL);
			} else {
				assertEquals(500, status);
				logger.debug(SAVE_FAILED);
			}
			
			
			final String URI_PATH = URL_WEB_SERVICE_PROJECT_ON_THE_SERVER + "/" + URI_PATH_OFFICES + "/" + URI_PATH_FIND_BY_ID + "/" + FIVE_ID;
			
			assertNotNull(URI_PATH);	
			assertTrue(URI_PATH instanceof String);
			
			this.webResource = this.client.resource(URI_PATH);
			
			assertNotNull(this.webResource);	
			assertTrue(this.webResource instanceof WebResource);
			
			this.clientResponse = this.webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
			assertNotNull(this.clientResponse);	
			assertTrue(this.clientResponse instanceof ClientResponse);
			
			logger.debug("status : " +status);
				
		} catch (UniformInterfaceException e) { 
			logger.debug("status : " + e.getResponse().getClientResponseStatus().getStatusCode());
			
		} catch (ClientHandlerException e) { 
			logger.debug(MESSAGE + e.getMessage(), e);
		}
	}
	
	/**
	 * Test method save
	 * 
	 */
	// @Ignore("Not Ready, to be changed")
	@Test
	/**
	 * Purpose:				
	 * 						
	 * Prerequisites:           
	 * Description:         
	 * Expected result:     
	 * @TEST_STATUS	        Ready
	 * @DESIGNER            Sylvose ALLOGO
	 * @TEST_TYPE	        
	 * @TEST_COVERAGE       
	 * @TEST_DESTINATION    
	 */
	public void testProjectsResourceSave() throws Exception {

		try {
			assertNotNull(FIVE_PROJECTS);	
			assertTrue(FIVE_PROJECTS instanceof Projects);
			
			final Response response = this.projectsResourceFacade.save(FIVE_PROJECTS);
			
			assertNotNull(response);	
			assertTrue(response instanceof Response);
			
			
			final int status = response.getStatus();
			
			if ((status >= 200) && (status <= 299)) {
				assertEquals(200, status);
				logger.debug(SAVE_SUCCESSFUL);
			} else {
				assertEquals(500, status);
				logger.debug(SAVE_FAILED);
			}
			
			
			final String URI_PATH = URL_WEB_SERVICE_PROJECT_ON_THE_SERVER + "/" + URI_PATH_PROJECTS + "/" + URI_PATH_FIND_BY_ID + "/" + FIVE_ID;
			
			assertNotNull(URI_PATH);	
			assertTrue(URI_PATH instanceof String);
			
			this.webResource = this.client.resource(URI_PATH);
			
			assertNotNull(this.webResource);	
			assertTrue(this.webResource instanceof WebResource);
			
			this.clientResponse = this.webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
			assertNotNull(this.clientResponse);	
			assertTrue(this.clientResponse instanceof ClientResponse);
			
			logger.debug("status : " +status);
				
		} catch (UniformInterfaceException e) { 
			logger.debug("status : " + e.getResponse().getClientResponseStatus().getStatusCode());
			
		} catch (ClientHandlerException e) { 
			logger.debug(MESSAGE + e.getMessage(), e);
		}
	}

	/**
	 * Test method save
	 * 
	 */
	// @Ignore("Not Ready, to be changed")
	@Test
	/**
	 * Purpose:				
	 * 						
	 * Prerequisites:           
	 * Description:         
	 * Expected result:     
	 * @TEST_STATUS	        Ready
	 * @DESIGNER            Sylvose ALLOGO
	 * @TEST_TYPE	        
	 * @TEST_COVERAGE       
	 * @TEST_DESTINATION    
	 */
	public void testUsersResourceSave() throws Exception {

		try {
			assertNotNull(FIVE_USERS);	
			assertTrue(FIVE_USERS instanceof Users);
			
			final Response response = this.usersResourceFacade.save(FIVE_USERS);
			
			assertNotNull(response);	
			assertTrue(response instanceof Response);
			
			
			final int status = response.getStatus();
			
			if ((status >= 200) && (status <= 299)) {
				assertEquals(200, status);
				logger.debug(SAVE_SUCCESSFUL);
			} else {
				assertEquals(500, status);
				logger.debug(SAVE_FAILED);
			}
			
			
			final String URI_PATH = URL_WEB_SERVICE_PROJECT_ON_THE_SERVER + "/" + URI_PATH_USERS + "/" + URI_PATH_FIND_BY_ID + "/" + FIVE_ID;
			
			assertNotNull(URI_PATH);	
			assertTrue(URI_PATH instanceof String);
			
			this.webResource = this.client.resource(URI_PATH);
			
			assertNotNull(this.webResource);	
			assertTrue(this.webResource instanceof WebResource);
			
			this.clientResponse = this.webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
			assertNotNull(this.clientResponse);	
			assertTrue(this.clientResponse instanceof ClientResponse);
			
			logger.debug("status : " +status);
				
		} catch (UniformInterfaceException e) { 
			logger.debug("status : " + e.getResponse().getClientResponseStatus().getStatusCode());
			
		} catch (ClientHandlerException e) { 
			logger.debug(MESSAGE + e.getMessage(), e);
		}
	}

	/**
	 * Test method save
	 * 
	 */
	// @Ignore("Not Ready, to be changed")
	@Test
	/**
	 * Purpose:				
	 * 						
	 * Prerequisites:           
	 * Description:         
	 * Expected result:     
	 * @TEST_STATUS	        Ready
	 * @DESIGNER            Sylvose ALLOGO
	 * @TEST_TYPE	        
	 * @TEST_COVERAGE       
	 * @TEST_DESTINATION    
	 */
	public void testRolesResourceSave() throws Exception {

		try {
			assertNotNull(FIVE_ROLES);	
			assertTrue(FIVE_ROLES instanceof Roles);
			
			final Response response = this.rolesResourceFacade.save(FIVE_ROLES);
			
			assertNotNull(response);	
			assertTrue(response instanceof Response);
			
			
			final int status = response.getStatus();
			
			if ((status >= 200) && (status <= 299)) {
				assertEquals(200, status);
				logger.debug(SAVE_SUCCESSFUL);
			} else {
				assertEquals(500, status);
				logger.debug(SAVE_FAILED);
			}
			
			
			final String URI_PATH = URL_WEB_SERVICE_PROJECT_ON_THE_SERVER + "/" + URI_PATH_ROLES + "/" + URI_PATH_FIND_BY_ID + "/" + FIVE_ID;
			
			assertNotNull(URI_PATH);	
			assertTrue(URI_PATH instanceof String);
			
			this.webResource = this.client.resource(URI_PATH);
			
			assertNotNull(this.webResource);	
			assertTrue(this.webResource instanceof WebResource);
			
			this.clientResponse = this.webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
			assertNotNull(this.clientResponse);	
			assertTrue(this.clientResponse instanceof ClientResponse);
			
			logger.debug("status : " +status);
				
		} catch (UniformInterfaceException e) { 
			logger.debug("status : " + e.getResponse().getClientResponseStatus().getStatusCode());
			
		} catch (ClientHandlerException e) { 
			logger.debug(MESSAGE + e.getMessage(), e);
		}
	}
		
	@After
	/**
	 * Purpose:
	 * Prerequisites:
	 * Description: 
	 * Expected result: 
	 * @TEST_STATUS	        Ready
	 * @DESIGNER            Sylvose ALLOGO
	 * @TEST_TYPE	        Integration testing
	 * @TEST_COVERAGE       
	 * @TEST_DESTINATION    
	 * 
	 * @throws java.lang.Exception
	 */
	public void tearDown() throws Exception {
		super.tearDown();
		
		this.rolesResourceFacade = null;
		this.usersResourceFacade = null;
		this.projectsResourceFacade = null;
		this.officeResourceFacade = null;
		this.departmentResourceFacade = null;
	}
}