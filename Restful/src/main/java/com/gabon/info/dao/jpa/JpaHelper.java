package com.gabon.info.dao.jpa;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import com.gabon.info.util.RestFulConstants;

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

public class JpaHelper implements RestFulConstants {

	private static final long serialVersionUID = -2852956576423927755L;
	
	
	private static final Logger logger = Logger.getLogger(JpaHelper.class.getName());
    private static final Object lock = new Object();
	
    
	private EntityManagerFactory entityManagerFactory; 
	
	@PersistenceContext(name = PERSISISTENCE, unitName = RESTFUL, type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;
	
	private ThreadLocal<EntityManager> threadLocal;
	
	
	
	public JpaHelper() {
		logger.setLevel(Level.ALL);
		
		synchronized (lock) {  
			
			if (entityManagerFactory == null) {
				entityManagerFactory = Persistence.createEntityManagerFactory(RESTFUL); 
				
				if (!(entityManagerFactory instanceof EntityManagerFactory) || !entityManagerFactory.isOpen()) { 
					try {
						final Context context = new InitialContext();
                        entityManagerFactory = (EntityManagerFactory) (context.lookup("java:/entityManagerFactory") != null ? context.lookup("java:/entityManagerFactory") : context.lookup("java:comp/env/entityManagerFactory"));
					} catch (NamingException e) {
						logger.log(Level.SEVERE, "lookup failed : Exception Type : " + e.getClass().getName() + MESSAGE + e.getMessage(), e);
				    }
				} 
				logger.log(Level.INFO, "entityManagerFactory : " +entityManagerFactory);
			}
			
			if (threadLocal == null) {
				threadLocal = new ThreadLocal<EntityManager>();
				logger.log(Level.INFO, "threadLocal : " +threadLocal);
			 }
		
			if (entityManager == null) {
				entityManager = threadLocal.get();	
				
				if (!(entityManager instanceof EntityManager) || !entityManager.isOpen())	
					entityManager = entityManagerFactory.createEntityManager();
					logger.log(Level.INFO, "entityManager : " +entityManager);
				
				threadLocal.set(entityManager);
				logger.log(Level.ALL, "threadLocal : " +threadLocal);
				
				logger.log(Level.INFO, "entityManager : " +entityManager);
			}
		  }
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void close() {
		if (threadLocal != null) { 
			threadLocal.set(null); 
			threadLocal = null;
		}
		
		if (entityManager != null) {
			 entityManager.clear(); 
			 entityManager.close(); 
			 entityManager = null;
		}
		
		if (entityManagerFactory != null) {
			entityManagerFactory.close(); 
			entityManagerFactory = null;
		}
  }
}