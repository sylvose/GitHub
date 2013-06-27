package com.gabon.info.dao.hibernate;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Environment;

import com.gabon.info.model.department.Department;
import com.gabon.info.model.office.Office;
import com.gabon.info.model.projects.Projects;
import com.gabon.info.model.roles.Roles;
import com.gabon.info.model.users.Users;
import com.gabon.info.util.Constants;

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

public class HibernateHelper implements Constants {
	
	private static final long serialVersionUID = 4443621644629038592L;
	
	
	private static final Logger logger = Logger.getLogger(HibernateHelper.class.getName());
    private static final Object lock = new Object();
	
    private AnnotationConfiguration annotationConfiguration;
    
    private ClassLoader classLoader;
    
    private URL url;
    
	private SessionFactory sessionFactory; 
	
	private Session session;
	
	private ThreadLocal<Session> threadLocal;
	
	
	
	public HibernateHelper() {
		logger.setLevel(Level.ALL);
		
		synchronized (lock) {  
			
			if (annotationConfiguration == null) {
				annotationConfiguration = new AnnotationConfiguration();
				logger.log(Level.INFO, "annotationConfiguration : " +annotationConfiguration);
			}
			
	        if (classLoader == null) {
	        	classLoader = Thread.currentThread().getContextClassLoader();
	        	
	        	if (classLoader == null)
	        		classLoader = ClassLoader.class.getClassLoader();
	        	
	        	logger.log(Level.INFO, "classLoader : " +classLoader);
	        }
	        
	        if (url == null) {
	        	url = classLoader.getResource(HIBERNATE_CONFIG_FILE);
	        	
	        	if (url == null)
	        		url = ClassLoader.getSystemResource(HIBERNATE_CONFIG_FILE);
	        	
	        	if (url == null)
	        		url = Environment.class.getResource(HIBERNATE_CONFIG_FILE);
	  
	    		if (url == null)
	    			url = Environment.class.getClassLoader().getResource(HIBERNATE_CONFIG_FILE);
	    		
				logger.log(Level.INFO, "url : " +url);
			}
	        
			if (sessionFactory == null) {
				
				annotationConfiguration.addAnnotatedClass(Users.class);
				annotationConfiguration.addAnnotatedClass(Department.class);
				annotationConfiguration.addAnnotatedClass(Office.class);
				annotationConfiguration.addAnnotatedClass(Projects.class);
				annotationConfiguration.addAnnotatedClass(Roles.class);
				
				sessionFactory = annotationConfiguration.configure(url).buildSessionFactory();
				logger.log(Level.INFO, "sessionFactory : " +sessionFactory);
			}
			
			if (threadLocal == null) {
				threadLocal = new ThreadLocal<Session>();
				logger.log(Level.INFO, "threadLocal : " +threadLocal);
			}
		
			if (session == null) {
				session = threadLocal.get();	
				
				if (!(session instanceof Session) || !session.isOpen())	
					session = sessionFactory.openSession();
				
				threadLocal.set(session);
				logger.log(Level.ALL, "threadLocal : " +threadLocal);
				
				logger.log(Level.INFO, "session : " +session);
			}
		  }
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public Session getSession() {
		return session;
	}
	
	public void close() {
		if (threadLocal != null) { 
			threadLocal.set(null); 
			threadLocal = null;
		}
		
		if (session != null) {
			 session.clear(); 
			 session.close(); 
			 session = null;
		}
		
		if (sessionFactory != null) {
			sessionFactory.close(); 
			sessionFactory = null;
		}
  }
}