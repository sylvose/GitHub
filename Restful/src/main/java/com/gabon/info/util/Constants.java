package com.gabon.info.util;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.gabon.info.model.department.Department;
import com.gabon.info.model.office.Office;
import com.gabon.info.model.projects.Projects;
import com.gabon.info.model.roles.Roles;
import com.gabon.info.model.users.Users;


/*
 * This class RestFulConstants provides many constants.
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

public interface Constants extends Serializable {
	
	int CONCRETE_DAO_JDBC = 1;
	int CONCRETE_DAO_JDBC_SPRING = 2;
	
	int CONCRETE_DAO_JPA = 3;
	int CONCRETE_DAO_JPA_SPRING = 4;
	
	int CONCRETE_DAO_HIBERNATE = 5;
	int CONCRETE_DAO_HIBERNATE_SPRING = 6;
	
	String NULL = "null";
	
	String UQAM = "UQAM";
	
	String ID = "id";
	String GET = "get";
	String Id  = "Id";
	
	String USERSID = "usersId";
	String OFFICEID = "officeId";
	String DEPARTMENTID = "departmentId";
	String PROJECTSID = "projectsId";
	String T_USERS_PROJECTS = "JOIN_TABLE_USERS_PROJECTS";
	
	String DEPARTMENT_SEQ_GEN = "DEPARTMENT_SEQ_GEN";
	String DEPARTMENT_SEQ = "DEPARTMENT_SEQ";
			
	String AbstractDepartment = "AbstractDepartment";
	String Department = "Department";
	String T_DEPARTMENT = "DEPARTMENT";
	String DEPARTMENT = "department";
	String USERS = "users";
	
	
	String USERS_SEQ_GEN = "USERS_SEQ_GEN";
	String USERS_SEQ = "USERS_SEQ";
			
	String AbstractUsers = "AbstractUsers";
	String Users = "Users";
	String T_USERS = "USERS";
	String NAME = "name";
	String EMAIL = "email";
	String ROLES = "roles";
	String PARTICIPATIONS = "participations";
	String PROJECTS = "projects";
	
	String ORACLE_SEQ_GENERATOR = "ORACLE_SEQ_GENERATOR";
	
	String OFFICE_SEQ_GEN = "OFFICE_SEQ_GEN";
	String OFFICE_SEQ = "OFFICE_SEQ";
	
	String AbstractOffice = "AbstractOffice";
	String Office = "Office";
	String T_OFFICE = "OFFICE";
	String OFFICE = "office";
	
	
	String ROLES_SEQ_GEN = "ROLES_SEQ_GEN";
	String ROLES_SEQ = "ROLES_SEQ";
	
	String AbstractRoles = "AbstractRoles";
	String Roles = "Roles";
	String T_ROLES = "ROLES";
	String ROLE = "role";
	

	String AbstractProjects = "AbstractProjects";
	String PROJECTS_SEQ_GEN = "PROJECTS_SEQ_GEN";
	String PROJECTS_SEQ = "PROJECTS_SEQ";
			
	String Projects = "Projects";
	String T_PROJECTS = "PROJECTS";
	String PROJECT = "project";
	
	String NULL_ID = null;
	
	
	String URL = "url";
    String DRIVER = "driver";
    String USERNAME = "username";
    String PASSWORD = "password";
    
    String DRIVERTYPE = "driverType";
    String SERVERNAME = "serverName";
    String NETWORKPROTOCOL = "networkProtocol";
    String DATABASENAME = "databaseName";
    String PORTNUMBER = "portNumber";
    
    String JNDIDS = "jndiDs";
    String INITIAL_CONTEXT_FACTORY = "initialContextFactory";
    String PROVIDER_URL = "providerUrl";
    String BIND = "bind";
    String LOOKUP = "lookup";
    
    
    
    String QUERY_STRING_SELECT_DISTINCT_MODEL_FROM_UQAM_TABLE = "SELECT DISTINCT model FROM ";
    String PREDICAT_STRING_MODEL = " model";
    String CLAUSE_STRING_MODEL_WHERE_MODEL_DOT = " model WHERE model.";
    
    String CLAUSE_STRING_FROM = "FROM ";
    
    String QUERY_STRING_SELECT_STAR_FROM_UQAM_TABLE = "SELECT * FROM " + "UQAM.";
    String QUERY_STRING_DELETE_FROM_UQAM_TABLE = "DELETE FROM " + "UQAM.";
    String CLAUSE_STRING_WHERE_ID = " WHERE id = ";
    String CLAUSE_STRING_WHERE = " WHERE id = ";
    
    String INSTANCE = " instance";
    String INSTANCE_FAILED_NO_ROWS_AFFECTED = " instance failed, no rows affected";
    
    
    String QUERY_STRING_SAVE_USERS_BY_ENTITY = "INSERT INTO UQAM.USERS(ID, OFFICEID, DEPARTMENTID, NAME, EMAIL) VALUES(?, ?, ?, ?, ?)";
    String QUERY_STRING_UPDATE_USERS_BY_ENTITY = "UPDATE UQAM.USERS SET OFFICEID = ?, DEPARTMENTID = ?, NAME = ?, EMAIL = ? WHERE ID = ?";
    
    
    String QUERY_STRING_SAVE_OFFICE_BY_ENTITY = "INSERT INTO UQAM.OFFICE(ID, OFFICE) VALUES(?, ?)";
    String QUERY_STRING_UPDATE_OFFICE_BY_ENTITY = "UPDATE UQAM.OFFICE SET OFFICE = ? WHERE ID = ?";
    
    
    String QUERY_STRING_SAVE_DEPARTMENT_BY_ENTITY = "INSERT INTO UQAM.DEPARTMENT(ID, DEPARTMENT) VALUES(?, ?)";
    String QUERY_STRING_UPDATE_DEPARTMENT_BY_ENTITY = "UPDATE UQAM.DEPARTMENT SET DEPARTMENT = ? WHERE ID = ?";
    
    
    String QUERY_STRING_SAVE_ROLES_BY_ENTITY = "INSERT INTO UQAM.ROLES(ID, USERSID, ROLE) VALUES(?, ?, ?)";
    String QUERY_STRING_UPDATE_ROLES_BY_ENTITY = "UPDATE UQAM.ROLES SET USERSID = ?, ROLE = ? WHERE ID = ?";
    
    
    String QUERY_STRING_SAVE_PROJECTS_BY_ENTITY = "INSERT INTO UQAM.PROJECTS(ID, PROJECT) VALUES(?, ?)";
    String QUERY_STRING_UPDATE_PROJECTS_BY_ENTITY = "UPDATE UQAM.PROJECTS SET PROJECT = ? WHERE ID = ?";
    
    
    
    Long ONE_ID = new Long(1);
    Long TWO_ID = new Long(2);
    Long FIVE_ID = new Long(5);
    Long SIX_ID = new Long(6);
    
    String ONE_NAME_ID = "Allogo";
    String TWO_NAME_ID = "Allogo";
	String FIVE_NAME_ID = "Marc";
	String SIX_NAME_ID = "Simon";

	String ONE_EMAIL_ID = "asylvose@yahoo.fr";
	String TWO_EMAIL_ID = "americains@hitmail.com";
	String FIVE_EMAIL_ID = "jhon@gmail.com";
	String SIX_EMAIL_ID = "pierre@hotmail.com";
	
	String ONE_OFFICE_ID = "Green";
	String TWO_OFFICE_ID = "Yellow";
	String FIVE_OFFICE_ID = "Montreal";
	String SIX_OFFICE_ID = "Quebec";
	
	String ONE_ROLE_ID = "admin";
	String TWO_ROLE_ID = "master";
	String FIVE_ROLE_ID = "president";
	String SIX_ROLE_ID = "controller";
	
	String ONE_PROJECTS_ID = "FREEDOMONE";
	String TWO_PROJECTS_ID = "RFTB";
	String FIVE_PROJECTS_ID = "SOFTWARE";
	String SIX_PROJECTS_ID = "HARDWARE";
	
	String ONE_DEPARTMENT_ID = "IT";
	String TWO_DEPARTMENT_ID = "FINANCIAL";
	String FIVE_DEPARTMENT_ID = "Pink";
	String SIX_DEPARTMENT_ID = "Magenta";
	
	Department FIVE_DEPARTMENT = new Department(FIVE_ID, FIVE_DEPARTMENT_ID);
	Department SIX_DEPARTMENT = new Department(FIVE_ID, SIX_DEPARTMENT_ID);

	Office FIVE_OFFICE = new Office(FIVE_ID, FIVE_OFFICE_ID);
	Office SIX_OFFICE = new Office(FIVE_ID, SIX_OFFICE_ID);
	
	Projects FIVE_PROJECTS = new Projects(FIVE_ID, FIVE_PROJECTS_ID);
	Projects SIX_PROJECTS = new Projects(FIVE_ID, SIX_PROJECTS_ID);

	Roles FIVE_ROLES = new Roles(FIVE_ID, FIVE_ROLE_ID, new Users(FIVE_ID, FIVE_NAME_ID, FIVE_EMAIL_ID, FIVE_OFFICE, FIVE_DEPARTMENT));
	Roles SIX_ROLES = new Roles(FIVE_ID, SIX_ROLE_ID, new Users(FIVE_ID, SIX_NAME_ID, SIX_EMAIL_ID, SIX_OFFICE, SIX_DEPARTMENT));
	
	Users FIVE_USERS = new Users(FIVE_ID, FIVE_NAME_ID, FIVE_EMAIL_ID, FIVE_OFFICE, FIVE_DEPARTMENT);  
	Users SIX_USERS = new Users(FIVE_ID, SIX_NAME_ID, SIX_EMAIL_ID, SIX_OFFICE, SIX_DEPARTMENT); 

	Set<Projects> SET_FIVE_PROJECTS = Collections.unmodifiableSet(new HashSet<Projects>(Arrays.asList(new Projects [] { FIVE_PROJECTS, SIX_PROJECTS })));
	Set<Projects> SET_SIX_PROJECTS = Collections.unmodifiableSet(new HashSet<Projects>(Arrays.asList(new Projects [] { SIX_PROJECTS, FIVE_PROJECTS })));
	
	Set<Users> SET_FIVE_USERS = Collections.unmodifiableSet(new HashSet<Users>(Arrays.asList(new Users [] { FIVE_USERS, SIX_USERS })));
	Set<Users> SET_SIX_USERS = Collections.unmodifiableSet(new HashSet<Users>(Arrays.asList(new Users [] { SIX_USERS, FIVE_USERS })));

	Set<Roles> SET_FIVE_ROLES = Collections.unmodifiableSet(new HashSet<Roles>(Arrays.asList(new Roles [] { FIVE_ROLES, SIX_ROLES })));
	Set<Roles> SET_SIX_ROLES = Collections.unmodifiableSet(new HashSet<Roles>(Arrays.asList(new Roles [] { SIX_ROLES, FIVE_ROLES })));
	
	
	
	Map<String, Users> mapUsers = Collections.synchronizedMap(new TreeMap<String, Users>() {
		private static final long serialVersionUID = 403529168623707033L;
		{ put(FIVE_NAME_ID, FIVE_USERS); put(SIX_NAME_ID, SIX_USERS); }
	});

	// Map<String, User> mapUsers = ImmutableMap.<String, User> builder().put(FIRST_NAME_ID, FIRST_USER).put(SECOND_NAME_ID, SECOND_USER).build();
	
	String ILLEGAL_ARGUMENT_SAVE = "Save : E instance is not saved yet, the E Entity Or Id is null : ";
	String ILLEGAL_ARGUMENT_UPDATE = "Update : E instance is not updated yet, the E Entity Or Id is null : ";
	String ILLEGAL_ARGUMENT_DELETE = "Delete : E instance is not deleted yet, the E Entity Or Id is null : ";
	String ILLEGAL_ARGUMENT_FIND = "Find : E instance is not deleted yet, the E Entity Or Id is null : ";
	String ILLEGAL_ARGUMENT_GET = "Get : E instance is not getted yet, the E Entity Or Id is null : ";
	
	String SAVE_SUCCESSFUL = "save successful";
	String UPDATE_SUCCESFUL = "update successful";
	String DELETE_SUCCESSFUL = "delete successful";
	String FIND_SUCCESSFUL = "find successful";
	String GET_SUCCESSFUL = "get successful";

	String MESSAGE = ", Message : ";
	
	String SAVING_E = "saving E ";
	String DELETING_E = "deleting E instance with id: ";
	String UPDATING_E = "updating E ";
	String FINDING_E_BY_ID = "finding E instance with id: ";
	String FINDING_ALL_E_BY_PROPERTY_NAME = "finding all E instances with propertyName: ";
	String FINDING_ALL_E_BY_ROWSSTARTIDXANDCOUNT = "finding all E instances with rowStartIdxAndCount: ";
	String GETTING_ALL_E = "getting all E instances";
	
	String SAVE_FAILED = "save failed : Exception Type : ";
	String UPDATE_FAILED = "update failed : Exception Type : ";
	String DELETE_FAILED = "delete failed : Exception Type : ";
	String FIND_FAILED = "find failed : Exception Type : ";
	String GET_FAILED = "get failed : Exception Type : ";
	
	
	String SYSTEM = "SYSTEM";	
	
	
	String GET_ALL_DEPARTMENT_NAME = "GET_ALL_DEPARTMENT_NAME";
	String GET_DEPARTMENT_QUERY = "SELECT DISTINCT d FROM Department d";
			
	String GET_ALL_USERS_NAME = "GET_ALL_USERS_NAME";
	String GET_ALL_USERS_QUERY = "SELECT DISTINCT u FROM Users u";
	
	String GET_ALL_OFFICE_NAME = "GET_ALL_OFFICE_NAME";
	String GET_ALL_OFFICE_QUERY = "SELECT DISTINCT o FROM Office o";
	
	String GET_ALL_ROLES_NAME = "GET_ALL_ROLES_NAME";
	String GET_ALL_ROLES_QUERY = "SELECT DISTINCT r FROM Roles r";
	
	String GET_ALL_PROJECTS_NAME = "GET_ALL_PROJECTS_NAME";
	String GET_ALL_PROJECTS_QUERY = "SELECT DISTINCT p FROM Projects p";
	
	
	String MISSING_QP = "Missing %s query string parameter";
	String ERROR_DELETING = "Error while deleting %s";
	String ERROR_SAVING = "Error while saving %s";
	String ERROR_DESACTIVATING = "Error while desactivating %s";
	String ID_NOT_FOUND = "Could not find id: %s";
	String RESOURCE_NOT_FOUND = "Could not find %s with id: %s";
	String INVALID_DATA = "Missing properties in post data. Mandatory fields are: %s";
	String INVALID_VALUE = "Invalid value for property: %s. Use one of the following: %s";
	
	String HIBERNATE_CONFIG_FILE = "com/gabon/info/hibernate.cfg.xml";
	String ORACLE_CONFIG_FILE = "com/gabon/info/oracle.properties";
	
	String DTO_CONTEXT_ROOT = "com/gabon/info/DTO.dto.xml";
	String APPLICATION_CONTEXT_ROOT = "com/gabon/info/applicationContext-root.xml";
	
	String BEAN_DEPARTMENT = "department";
	String BEAN_USERS = "users";
	String BEAN_OFFICE = "office";
	String BEAN_ROLES = "roles";
	String BEAN_PROJECTS = "projects";
	
	

	String BEAN_ABSTRACT_JPA_DAO_SUPPORT = "abstractJpaDaoSupport";
	String BEAN_CONCRETE_DAO_JPA_SPRING = "concreteDAOJpaSpring";
	String BEAN_ENTITY_MANAGER_FACTORY = "entityManagerFactory";
	String BEAN_JPA_TEMPLATE = "jpaTemplate";
	String BEAN_JPA_TRANSACTION_MANAGER = "jpaTransactionManager";
	
	String BEAN_DEPARTMENT_DAO_JPA_SPRING = "departmentDAOJpaSpring";
	String BEAN_USERS_DAO_JPA_SPRING = "usersDAOJpaSpring";
	String BEAN_OFFICE_DAO_JPA_SPRING = "officeDAOJpaSpring";
	String BEAN_ROLES_DAO_JPA_SPRING = "rolesDAOJpaSpring";
	String BEAN_PROJECTS_DAO_JPA_SPRING = "projectsDAOJpaSpring";
	
	
	String BEAN_ABSTRACT_HIBERNATE_DAO_SUPPORT = "abstractHibernateDaoSupport";
	String BEAN_CONCRETE_DAO_HIBERNATE_SPRING = "concreteDAOHibernateSpring";
	String BEAN_SESSION_FACTORY = "sessionFactory";
	String BEAN_HIBERNATE_TEMPLATE = "hibernateTemplate";
	String BEAN_HIBERNATE_TRANSACTION_MANAGER = "hibernateTransactionManager";
	
	String BEAN_DEPARTMENT_DAO_HIBERNATE_SPRING = "departmentDAOHibernateSpring";
	String BEAN_USERS_DAO_HIBERNATE_SPRING = "usersDAOHibernateSpring";
	String BEAN_OFFICE_DAO_HIBERNATE_SPRING = "officeDAOHibernateSpring";
	String BEAN_ROLES_DAO_HIBERNATE_SPRING = "rolesDAOHibernateSpring";
	String BEAN_PROJECTS_DAO_HIBERNATE_SPRING = "projectsDAOHibernateSpring";
	
	
	String BEAN_ABSTRACT_JDBC_DAO_SUPPORT = "abstractJdbcDaoSupport";
	String BEAN_CONCRETE_DAO_JDBC_SPRING = "concreteDAOJdbcSpring";
	String BEAN_DATA_SOURCE = "dataSource";
	String BEAN_JBDC_TEMPLATE = "jdbcTemplate";
	String BEAN_JDBC_TRANSACTION_MANAGER = "jdbcTransactionManager";
	
	String BEAN_DEPARTMENT_DAO_JDBC_SPRING = "departmentDAOJdbcSpring";
	String BEAN_USERS_DAO_JDBC_SPRING = "usersDAOJdbcSpring";
	String BEAN_OFFICE_DAO_JDBC_SPRING = "officeDAOJdbcSpring";
	String BEAN_ROLES_DAO_JDBC_SPRING = "rolesDAOJdbcSpring";
	String BEAN_PROJECTS_DAO_JDBC_SPRING = "projectsDAOJdbcSpring";
	
	
	String BEAN_DEPARTMENT_RESOURCE = "departmentResource";
	String BEAN_USERS_RESOURCE = "usersResource";
	String BEAN_OFFICE_RESOURCE = "officeResource";
	String BEAN_ROLES_RESOURCE = "rolesResource";
	String BEAN_PROJECTS_RESOURCE = "projectsResource";
	
	
	String BEAN_BUILDER_FACTORY = "builderFactory";
	
	
	
	
	
	
	
	
	
	String PERSISISTENCE                   = "entityManagerFactory";
	String URI_PACKAGE                     = "com.gabon.info.resource";
	
	String RESTFUL                         = "restful";              // file  pom.xml           : <artifactId>restful</artifactId> 
	String CONTEXT                         = "rest";                 // file  web.xml           : <url-pattern>/rest/*</url-pattern>
	
	String LOCALHOST                       = "http://localhost/";
	int PORT                               = 8080;
	String URL_SERVER                      = LOCALHOST + ":" + PORT;
	
	String URL_WEB_SERVICE_PROJECT_ON_THE_SERVER = URL_SERVER + "/" + RESTFUL + "/" + CONTEXT; 
	
	String ALLOW = "Allow";
	String NOT_ALLOW = "Not Allowed";
	
	String E = "E";
	String PROPERTYNAME                    = "propertyName";
	String OBJECT = "Object";
	String ROWSSTARTIDXANDCOUNT            = "rowStartIdxAndCount";
	
	
	
	String URI_PATH_ABSTRACT_RESOURCES     = "abstractResources";
	String URI_PATH_FIND_ALL_BY_PROPERTY   = "find/all/property";
	
	String URI_PATH_DEPARTMENTS            = "departments";
	String URI_PATH_FIND_ALL_BY_DEPARTMENT = "find/all/department";
	
	String URI_PATH_USERS    		       = "users";
	String URI_PATH_FIND_ALL_BY_NAME       = "find/all/name";
	String URI_PATH_FIND_ALL_BY_EMAIL      = "find/all/email";
	
	String URI_PATH_OFFICES                = "offices";
	String URI_PATH_FIND_ALL_BY_OFFICE     = "find/all/office";
	
	String URI_PATH_ROLES                  = "roles";
	String URI_PATH_FIND_ALL_BY_ROLE       = "find/all/role";
	
	String URI_PATH_PROJECTS               = "projects";
	String URI_PATH_FIND_ALL_BY_PROJECT    = "find/all/project";
	
	String URI_PATH_SAVE_BY_ENTITY         = "save/entity";
	String URI_PATH_SAVE_BY_PARAMETERS     = "save/parameters";
	String URI_PATH_DELETE_BY_ENTITY       = "delete/entity";
	String URI_PATH_DELETE_BY_ID           = "delete/id";
	String URI_PATH_UPDATE_BY_ENTITY       = "update/entity";
	String URI_PATH_UPDATE_BY_PARAMETERS   = "update/parameters";
	String URI_PATH_FIND_BY_ID             = "find/id";
	String URI_PATH_FIND_ALL               = "find/all";
	String URI_PATH_GET_ALL                = "get/all";
}