package com.gabon.info.server.dao.jdbc;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import oracle.jdbc.pool.OracleConnectionPoolDataSource;

import org.hibernate.cfg.Environment;

import com.gabon.info.server.util.Constants;

/*
 * This class is a domain class.
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

public class JdbcHelper implements Constants {
	
	private static final long serialVersionUID = -435371082586864275L;
	
	
	private static final Logger logger = Logger.getLogger(JdbcHelper.class.getName());
    private static final Object lock = new Object();
	
    private Class<?> clazz;
    
    private ClassLoader classLoader;
    
    private InputStream inputStream;
    
    private Properties properties;
    
    private String url;
    
    private String driver;
    
    private String password;
    
    private String username;

    private String driverType;
    
    private String serverName;
    
    private String networkProtocol;
    
    private String databaseName;
    
    private int portNumber;

    private String jndiDs;
    
    private String initialContextFactory;
    
    private String providerUrl;
    
    private String bind;
    
    private String lookup;
    
    private DataSource dataSource;
    
    private Connection connection; 
    
    private ThreadLocal<Connection> threadLocal;
    
    private PreparedStatement preparedStatement;
    
    private ResultSet resultSet;
    
	
    
    public JdbcHelper() {
		logger.setLevel(Level.ALL);
		
		synchronized (lock) {  
			
			if (classLoader == null) {
	        	classLoader = Thread.currentThread().getContextClassLoader();
	        	
	        	if (classLoader == null)
	        		classLoader = ClassLoader.class.getClassLoader();
	        	
	        	logger.log(Level.INFO, "classLoader : " +classLoader);
	        }
			
			if (inputStream == null) {
				inputStream = classLoader.getResourceAsStream(ORACLE_CONFIG_FILE);
	        	
	        	if (inputStream == null)
	        		inputStream = ClassLoader.getSystemResourceAsStream(ORACLE_CONFIG_FILE);
	        	
	        	if (inputStream == null)
	        		inputStream = Environment.class.getResourceAsStream(ORACLE_CONFIG_FILE);
	  
	    		if (inputStream == null)
	    			inputStream = Environment.class.getClassLoader().getResourceAsStream(ORACLE_CONFIG_FILE);
	    		
				logger.log(Level.INFO, "inputStream : " +inputStream);
			}
			
			if (properties == null) {
				
				properties = new Properties();
						
				
				try {
					 properties.load(inputStream);
					 logger.log(Level.INFO, "properties : " +properties);
				
					 url = properties.getProperty(URL);
					 logger.log(Level.INFO, "url : " +url);
					 
				     driver = properties.getProperty(DRIVER);
				     logger.log(Level.INFO, "driver : " +driver);
				     
				     password = properties.getProperty(PASSWORD);
				     logger.log(Level.INFO, "password : " +password);
				     
				     username = properties.getProperty(USERNAME);
				     logger.log(Level.INFO, "username : " +username);

				 } catch (IOException e) {
					logger.log(Level.SEVERE, "load failed : Exception Type : " + e.getClass().getName() + MESSAGE + e.getMessage(), e);
				 }
			}

			if (clazz == null) {
				
				try {
					clazz = Class.forName(driver);
					logger.log(Level.INFO, "clazz : " +clazz);
				
				 } catch (ClassNotFoundException e) {
					logger.log(Level.SEVERE, "forName failed : Exception Type : " + e.getClass().getName() + MESSAGE + e.getMessage(), e);
				 }
			}
		
			if (dataSource == null) {
		
				try {                
					driverType = properties.getProperty(DRIVERTYPE);
					logger.log(Level.INFO, "driverType : " +driverType);
					
				    serverName = properties.getProperty(SERVERNAME);
				    logger.log(Level.INFO, "serverName : " +serverName);
				    
				    networkProtocol = properties.getProperty(NETWORKPROTOCOL);
				    logger.log(Level.INFO, "networkProtocol : " +networkProtocol);
				    
				    databaseName = properties.getProperty(DATABASENAME);
				    logger.log(Level.INFO, "databaseName : " +databaseName);
				    
				    portNumber = Integer.valueOf(properties.getProperty(PORTNUMBER)).intValue();
				    logger.log(Level.INFO, "portNumber : " +portNumber);
				    
				    url = properties.getProperty(URL);
					logger.log(Level.INFO, "url : " +url);
					 
				    password = properties.getProperty(PASSWORD);
				    logger.log(Level.INFO, "password : " +password);
				     
				    username = properties.getProperty(USERNAME);
				    logger.log(Level.INFO, "username : " +username);
				    
				    networkProtocol = properties.getProperty(NETWORKPROTOCOL);
				    logger.log(Level.INFO, "networkProtocol : " +networkProtocol);
				    
				    username = properties.getProperty(USERNAME);
				    logger.log(Level.INFO, "username : " +username);
				    
				    username = properties.getProperty(USERNAME);
				    logger.log(Level.INFO, "username : " +username);
				    
				    username = properties.getProperty(USERNAME);
				    logger.log(Level.INFO, "username : " +username);
				    
				    jndiDs = properties.getProperty(JNDIDS);
				    logger.log(Level.INFO, "jndiDs : " +jndiDs);
				    
				    initialContextFactory = properties.getProperty(INITIAL_CONTEXT_FACTORY);
					properties.put(Context.INITIAL_CONTEXT_FACTORY, initialContextFactory);
					logger.log(Level.INFO, "initialContextFactory : " +initialContextFactory);
					
					providerUrl = properties.getProperty(PROVIDER_URL);
					properties.put(Context.PROVIDER_URL, providerUrl);
				    logger.log(Level.INFO, "providerUrl : " +providerUrl);
				    
				    
					final OracleConnectionPoolDataSource oracleConnectionPoolDataSource = new OracleConnectionPoolDataSource();
					oracleConnectionPoolDataSource.setDriverType(driverType);
					oracleConnectionPoolDataSource.setServerName(serverName);
					oracleConnectionPoolDataSource.setNetworkProtocol(networkProtocol);
					oracleConnectionPoolDataSource.setDatabaseName(databaseName);
					oracleConnectionPoolDataSource.setPortNumber(portNumber);
					oracleConnectionPoolDataSource.setURL(url);
					oracleConnectionPoolDataSource.setConnectionProperties(properties);
					oracleConnectionPoolDataSource.setUser(username);
					oracleConnectionPoolDataSource.setPassword(password);
					oracleConnectionPoolDataSource.setNetworkProtocol(networkProtocol);
					oracleConnectionPoolDataSource.setConnectionProperties(properties);
					
					bind = properties.getProperty(BIND);
				    logger.log(Level.INFO, "bind : " +bind);
				    
					final Context initContext = new InitialContext(properties);
					
					initContext.createSubcontext("java:");
					initContext.createSubcontext("java:/comp");
					initContext.createSubcontext("java:/comp/env");
					initContext.createSubcontext("java:/comp/env/jdbc");
					
					initContext.bind(bind, oracleConnectionPoolDataSource);
			        
					lookup = properties.getProperty(LOOKUP);
				    logger.log(Level.INFO, "lookup : " +lookup);
				    
			        final Context webContext = (Context) initContext.lookup(lookup);
			        
					dataSource = (DataSource) webContext.lookup(jndiDs);
					logger.log(Level.INFO, "dataSource : " +dataSource);
				
					initContext.destroySubcontext("java:");
						
				} catch (NamingException e) {
					logger.log(Level.SEVERE, "lookup failed : Exception Type : " + e.getClass().getName() + MESSAGE + e.getMessage(), e);
				} catch (SQLException e) {
					logger.log(Level.SEVERE, "InitialContext failed : Exception Type : " + e.getClass().getName() + MESSAGE + e.getMessage(), e);
				} 
			}
			
			if (threadLocal == null) {
				threadLocal = new ThreadLocal<Connection>();
				logger.log(Level.INFO, "threadLocal : " +threadLocal);
			}
			
			if (connection == null) {
				
				connection = threadLocal.get();	
				
				try {
					if (!(connection instanceof Connection) || connection.isClosed()) {
						
						if (clazz != null) {
							connection = DriverManager.getConnection(url, username, password);
							connection.setAutoCommit(false);
							
							threadLocal.set(connection);
							logger.log(Level.ALL, "threadLocal : " +threadLocal);
							
							logger.log(Level.INFO, "connection : " +connection);
							
						} else if (dataSource instanceof DataSource) {
							connection = dataSource.getConnection();
							connection.setAutoCommit(false);
							
							threadLocal.set(connection);
							logger.log(Level.ALL, "threadLocal : " +threadLocal);
							
							logger.log(Level.INFO, "connection : " +connection);
						} 
					}
				} catch (SQLException e) {
					 logger.log(Level.SEVERE, "getConnection failed : Exception Type : " + e.getClass().getName() + MESSAGE + e.getMessage(), e);
				} catch (Exception e) {
					logger.log(Level.SEVERE, "isClosed failed : Exception Type : " + e.getClass().getName() + MESSAGE + e.getMessage(), e);
				}
			}
		}
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public Connection getConnection() {
		return connection;
	}
	
	
	public Long getNextValOracleSequence(String sequenceName) {
		Long id = null;
		
		final String queryString = "SELECT " +sequenceName+ ".NEXTVAL FROM DUAL;";
		
		if (connection != null) {
			try {
				preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				
				if (preparedStatement != null)
					resultSet = preparedStatement.executeQuery();
				 else 
					throw new NullPointerException("Unexpected value preparedStatement : " + preparedStatement);
						
				if (resultSet != null)
					id = resultSet.getLong(1);
				 else 
					throw new NullPointerException("Unexpected value resultSet : " + resultSet);
				
			} catch (SQLException e) {
				logger.log(Level.SEVERE, "prepareStatement failed : Exception Type : " + e.getClass().getName() + MESSAGE + e.getMessage(), e);
				
			} finally {
				close();
				
			}
		} else {
			throw new NullPointerException("Unexpected value connection : " + connection);
		}
		
		return id;
	}
	
	
	public Long getUsersId() {
		return getNextValOracleSequence(USERS_SEQ);
	}
	
	public Long getOfficeId() {
		return getNextValOracleSequence(OFFICE_SEQ);
	}
	
	public Long getDepartmentId() {
		return getNextValOracleSequence(DEPARTMENT_SEQ);
	}
	
	public Long getRolesId() {
		return getNextValOracleSequence(ROLES_SEQ);
	}
	
	public Long getProjectsId() {
		return getNextValOracleSequence(PROJECTS_SEQ);
	}
	
	
	public void close() {
	
		if (resultSet != null) {

			try {
				resultSet.close();
				resultSet = null;
				
			} catch (SQLException e) {
				logger.log(Level.SEVERE, "close failed : Exception Type : " + e.getClass().getName() + MESSAGE + e.getMessage(), e);
			}
		}
		
		if (preparedStatement != null) {

			try {
				preparedStatement.close();
				preparedStatement = null;
				
			} catch (SQLException e) {
				logger.log(Level.SEVERE, "close failed : Exception Type : " + e.getClass().getName() + MESSAGE + e.getMessage(), e);
			}
		}
		
		if (threadLocal != null) { 
			threadLocal.set(null); 
			threadLocal = null;
		}
		
		if (connection != null) {

			try {
				connection.close();
				connection = null;
				
			} catch (SQLException e) {
				logger.log(Level.SEVERE, "close failed : Exception Type : " + e.getClass().getName() + MESSAGE + e.getMessage(), e);
			}
		}

		if (dataSource != null)
			dataSource = null;
		
		if (clazz != null)
			clazz = null;
	}
}