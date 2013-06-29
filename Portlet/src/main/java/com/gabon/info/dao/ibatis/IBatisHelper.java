package com.gabon.info.dao.ibatis;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gabon.info.util.Constants;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

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

public class IBatisHelper implements Constants {
	
	private static final long serialVersionUID = 4443621644629038592L;
	
	
	private static final Logger logger = Logger.getLogger(IBatisHelper.class.getName());
    private static final Object lock = new Object();
	 
    private Reader reader;
    
	private SqlMapClient sqlMapClient; 
	
	private Connection connection;
	
	private ThreadLocal<Connection> threadLocal;
	
	
	
	public IBatisHelper() {
		logger.setLevel(Level.ALL);
		
		synchronized (lock) {  
		
			try {
				if (reader == null) {
					reader = Resources.getResourceAsReader(IBATIS_CONFIG_FILE);

					logger.log(Level.INFO, "reader : " + reader);
				}

				if (sqlMapClient == null) {
					sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(reader);

					logger.log(Level.INFO, "sqlMapClient : " + sqlMapClient);
				}

				if (threadLocal == null) {
					threadLocal = new ThreadLocal<Connection>();
					
					logger.log(Level.INFO, "threadLocal : " + threadLocal);
				}

				if (connection == null) {
					connection = threadLocal.get();

					if (!(connection instanceof Connection) || connection.isClosed())
						connection = sqlMapClient.getCurrentConnection();

					threadLocal.set(connection);
					logger.log(Level.ALL, "threadLocal : " + threadLocal);

					logger.log(Level.INFO, "connection : " + connection);
				}

			} catch (IOException e) {
				logger.log(Level.SEVERE, e.getClass().getName() + MESSAGE + e.getMessage(), e);
			} catch (SQLException e) {
				logger.log(Level.SEVERE, e.getClass().getName() + MESSAGE + e.getMessage(), e);
			}
		  }
	}

	public SqlMapClient getSqlMapClient() {
		return sqlMapClient;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public void close() {
		if (threadLocal != null) { 
			threadLocal.set(null); 
			threadLocal = null;
		}
		
		if (connection != null) {
			
		   try {
			  connection.close();
				
			} catch (SQLException e) {
				logger.log(Level.SEVERE, e.getClass().getName() + MESSAGE + e.getMessage(), e);
				
			} finally {
			   connection = null;
			}
		}
		
		if (sqlMapClient != null) {
			sqlMapClient = null;
		}
   }
}