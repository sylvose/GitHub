package com.gabon.info.dao.spring.hibernate;

import static com.gabon.info.util.Constants.BEAN_ABSTRACT_HIBERNATE_DAO_SUPPORT;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.dao.DAOSupportFacade;

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
 * This class has the DAO class extend HibernateDaoSupport and uses the HibernateTemplate, but Spring recommends the native Hibernate style of coding.
 * 
 */

@Transactional
@Repository(BEAN_ABSTRACT_HIBERNATE_DAO_SUPPORT)
public abstract class AbstractHibernateDaoSupport<E, PK extends Serializable> extends HibernateDaoSupport implements DAOSupportFacade<E, PK> {	
	
	private static final long serialVersionUID = -5733408712041255431L;

	protected static final Logger logger = Logger.getLogger(AbstractHibernateDaoSupport.class.getName());
	
	protected static final Object lock = new Object();
	
	protected static final ApplicationContext applicationContext = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_ROOT);
	
	
	protected PK id;
	protected Class<E> clazzE;
	protected Class<PK> clazzPK;
	
	
	
	@SuppressWarnings("unchecked")
	public AbstractHibernateDaoSupport() { 
		super();
		
		final Type type = getClass().getGenericSuperclass();
		if (type instanceof Type) {
			final Class<ParameterizedType> clazzEParameterizedType = ParameterizedType.class;
			final ParameterizedType parameterizedType = clazzEParameterizedType.cast(type);
		    final Object object = parameterizedType.getActualTypeArguments()[0];
		    this.clazzE = Class.class.cast(object); 
		}
		
		if (applicationContext instanceof ApplicationContext) {
			final HibernateTemplate hibernateTemplate = (HibernateTemplate) applicationContext.getBean(BEAN_HIBERNATE_TEMPLATE);
			setHibernateTemplate(hibernateTemplate);
		}
	}
	
	
	
	public PK getId(E entity) {
		this.id = getProperty(entity, Id);
		return this.id;
	}

	public void setId(PK id) {
		this.id = id;
	}
	
	public Class<E> getClazzE() {
		return this.clazzE;
	}

	public void setClazzE(Class<E> clazzE) {
		this.clazzE = clazzE;
	}
	
	public Class<PK> getClazzPK() {
		return this.clazzPK;
	}

	public void setClazzPK(Class<PK> clazzPK) {
		this.clazzPK = clazzPK;
	}
	

	
	public void showMethods(E entity) {
		
		final Method[] method = entity.getClass().getMethods();
		
		for (int i = 0; i < method.length; i++) {
			final String methodString = method[i].getName();
			logger.log(Level.INFO, "Name: " + methodString);
			
			final String returnString = method[i].getReturnType().getName();
			logger.log(Level.INFO, "   Return Type: " + returnString);
			
			final Class<?>[] parameterTypes = method[i].getParameterTypes();
			logger.log(Level.INFO, "   Parameter Types:");
			
			for (int k = 0; k < parameterTypes.length; k++) {
				final String parameterString = parameterTypes[k].getName();
				logger.log(Level.INFO, " " + parameterString);
			}
			
			System.out.println();
		}
	}

	
	public PK getProperty(E entity, String property) {
        
    	showMethods(entity);
    	
    	PK returnValue = null;

			try {
				final String methodName = GET + property.substring(0, 1).toUpperCase() + property.substring(1, property.length());
				final Class<?>[] parameterTypes = new Class<?>[] {};
				final Object[] args = new Object[] {};

				final Method method = entity.getClass().getMethod(methodName, parameterTypes);
				final Object object = method.invoke(entity, args);
	            
	            if (object instanceof Object)
	            	returnValue = this.clazzPK.cast(object);
	            
			} catch (SecurityException e) {
				logger.log(Level.SEVERE, e.getClass().getName() + MESSAGE + e.getMessage(), e);
			} catch (IllegalArgumentException e) {
				logger.log(Level.SEVERE, e.getClass().getName() + MESSAGE + e.getMessage(), e);
			} catch (IllegalAccessException e) {
				logger.log(Level.SEVERE, e.getClass().getName() + MESSAGE + e.getMessage(), e);
			} catch (NoSuchMethodException e) {
				logger.log(Level.SEVERE, e.getClass().getName() + MESSAGE + e.getMessage(), e);
			} catch (InvocationTargetException e) {
				logger.log(Level.SEVERE, e.getClass().getName() + MESSAGE + e.getMessage(), e);
			}
            
        return returnValue;
    }
    
    public boolean isEntityExists(E entity) {
		boolean exists = false;
		
		final String queryString = CLAUSE_STRING_FROM + this.clazzE.getSimpleName();
		final SessionFactory sessionFactory = this.getHibernateTemplate().getSessionFactory();
		final Session session = sessionFactory.openSession();
		
		try {
			final Query query = session.createQuery(queryString);
			final List<?> resultList = query.list();
			
			if (resultList == null)
				throw new ObjectRetrievalFailureException(this.clazzE, resultList);
			
			for (Object object : resultList) {		
				final PK entityId = getId(entity);
				final PK modelId = getId(this.clazzE.cast(object));
			
				if (entityId instanceof Serializable && modelId instanceof Serializable) {
					exists = entityId.equals(modelId);
					
					if (exists)
					break;
				}
			}
			
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, e.getClass().getName() + MESSAGE + e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getClass().getName() + MESSAGE + e.getMessage(), e);
		}
		
		return exists;
	}
    
    public boolean isEntityExists(PK id) {
		boolean exists = false;
		
		try {
			final E entity = findById(id);
			
			if (entity == null)
				exists = false;
			else
				exists = true;
			
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, e.getClass().getName() + MESSAGE + e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getClass().getName() + MESSAGE + e.getMessage(), e);
		}
		
		return exists;
	}
    
    
	
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void save(E entity) {
		if (entity == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_SAVE + entity);
		
		final SessionFactory sessionFactory = this.getHibernateTemplate().getSessionFactory();
		Session session = sessionFactory.openSession();
		if (session == null || !session.isOpen())
			session = getSession(false);
		
		if (isEntityExists(entity))
			throw new ObjectNotFoundException(getId(entity), this.clazzE.getSimpleName());
		
		final Transaction transaction = session.beginTransaction();
		if (transaction == null)
			throw new NullPointerException(ILLEGAL_ARGUMENT_SAVE + entity);
				
		logger.log(Level.INFO, SAVING_E +entity+ INSTANCE);
		
		try {
			if (!transaction.isActive())
				transaction.begin();
			
			this.getHibernateTemplate().save(entity);
			
			transaction.commit();
			
			logger.log(Level.INFO, SAVE_SUCCESSFUL);
				
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, SAVE_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
			if (transaction.isActive()) 
				transaction.rollback();
			throw e;
		} catch (Exception e) {
			logger.log(Level.SEVERE, SAVE_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
		} 
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(E entity) {
		delete(getId(entity));
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(PK id) {
		if (id == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_DELETE + id);
		
		final SessionFactory sessionFactory = this.getHibernateTemplate().getSessionFactory();
		Session session = sessionFactory.openSession();
		if (session == null || !session.isOpen())
			session = getSession(false);
		
		final E entity = findById(id);
		if (entity == null)
			throw new ObjectNotFoundException(id, this.clazzE.getSimpleName());
		
		final Transaction transaction = session.getTransaction();
		if (transaction == null)
			throw new NullPointerException();
		
		logger.log(Level.INFO, DELETING_E + id);
		
		try {
			if (!transaction.isActive())
				transaction.begin();
			
				this.getHibernateTemplate().delete(entity);
				
				transaction.commit();
				
				logger.log(Level.INFO, DELETE_SUCCESSFUL);

		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, DELETE_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
			if (session.getTransaction().isActive()) 
			transaction.rollback();
			throw e;
		} catch (Exception e) {
			logger.log(Level.SEVERE, DELETE_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
		} 
	}

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public E update(E entity) {
		if (entity == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_UPDATE + entity);
		
		final SessionFactory sessionFactory = this.getHibernateTemplate().getSessionFactory();
		Session session = sessionFactory.openSession();
		if (session == null || !session.isOpen())
			session = getSession(false);
		
		if (!isEntityExists(entity))
			throw new ObjectNotFoundException(getId(entity), this.clazzE.getSimpleName());
		
		final Transaction transaction = session.getTransaction();
		if (transaction == null)
			throw new NullPointerException();
		
		logger.log(Level.INFO, UPDATING_E +entity+ INSTANCE);
		
		E model = null;
				
		try {
			if (!transaction.isActive())
				transaction.begin();
			
			model = this.clazzE.cast(this.getHibernateTemplate().merge(entity));
			
			if (model != null) {
				transaction.commit();
				
				logger.log(Level.INFO, UPDATE_SUCCESFUL);
				
			} else
				logger.log(Level.INFO, UPDATE_FAILED);
				
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, UPDATE_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
			if (session.getTransaction().isActive()) 
			transaction.rollback();
			throw e;
		} catch (Exception e) {
			logger.log(Level.SEVERE, UPDATE_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
		}
		
		return model;
	}

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public E findById(PK id) {
		if (id == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_FIND + id);
		
		final SessionFactory sessionFactory = this.getHibernateTemplate().getSessionFactory();
		Session session = sessionFactory.openSession();
		if (session == null || !session.isOpen())
			session = getSession(false);
		
		E entity = null;
		
		logger.log(Level.INFO, FINDING_E_BY_ID + id);
		
		try {
			entity = this.clazzE.cast(this.getHibernateTemplate().get(this.clazzE, id));
			
			if (entity != null) {
				
				logger.log(Level.INFO, FIND_SUCCESSFUL);
				
			} else  {
				logger.log(Level.INFO, FIND_FAILED);
				throw new ObjectNotFoundException(id, this.clazzE.getSimpleName());
			}
			
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, FIND_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.log(Level.SEVERE, FIND_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
		} 
		return entity;
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<E> findByProperty(String propertyName, Object propertyValue, int... rowStartIdxAndCount) {
		
		if (StringUtils.isBlank(StringUtils.trim(propertyName)) || propertyValue == null || rowStartIdxAndCount.length < 0)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_FIND + propertyName + ", propertyValue: " + propertyValue + ", rowStartIdxAndCount: " + rowStartIdxAndCount);
		
		final SessionFactory sessionFactory = this.getHibernateTemplate().getSessionFactory();
		Session session = sessionFactory.openSession();
		if (session == null || !session.isOpen())
			session = getSession(false);
		
		logger.log(Level.INFO, FINDING_ALL_E_BY_PROPERTY_NAME + propertyName + ", propertyValue: " + propertyValue);
		
		final List<E> entities = new LinkedList<E>();
		
		try {
			final String queryString = CLAUSE_STRING_FROM + this.clazzE.getSimpleName() + CLAUSE_STRING_MODEL_WHERE_MODEL_DOT + propertyName  + " = :propertyValue";
			final Query query = session.createQuery(queryString);
			query.setParameter("propertyValue", propertyValue);
			
			if (rowStartIdxAndCount != null && rowStartIdxAndCount.length > 0) {
				int rowStartIdx = Math.max(0, rowStartIdxAndCount[0]);
				
				if (rowStartIdx > 0) {
					query.setFirstResult(rowStartIdx);
				}

				if (rowStartIdxAndCount.length > 1) {
					int rowCount = Math.max(0, rowStartIdxAndCount[1]);
					if (rowCount > 0) {
						query.setMaxResults(rowCount);
					}
				}
			}
			
			final List<?> resultList = query.list();
			if (resultList instanceof List) {
				for (Object object : resultList) {
					if (object instanceof Object) {
						final E model = clazzE.cast(object);
						entities.add(model);
					}
				}
			}
			
			if (entities != null && !entities.isEmpty())
				logger.log(Level.INFO, FIND_SUCCESSFUL);
			else 
				logger.log(Level.INFO, FIND_FAILED);
			
		 } catch (RuntimeException e) {
			 logger.log(Level.SEVERE, FIND_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
			throw e;
		 } catch (Exception e) {
			 logger.log(Level.SEVERE, FIND_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
		 } 
		
		return entities;
	}

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<E> findAll(int... rowStartIdxAndCount) {
		
		if (rowStartIdxAndCount.length < 0)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_FIND + ", rowStartIdxAndCount: " + rowStartIdxAndCount);

		final SessionFactory sessionFactory = this.getHibernateTemplate().getSessionFactory();
		Session session = sessionFactory.openSession();
		if (session == null || !session.isOpen())
			session = getSession(false);
		
		logger.log(Level.INFO, FINDING_ALL_E_BY_ROWSSTARTIDXANDCOUNT + rowStartIdxAndCount);
		
		final List<E> entities = new LinkedList<E>();
		
		try {
			final String queryString = CLAUSE_STRING_FROM + this.clazzE.getSimpleName();
			final Query query = session.createQuery(queryString);
			
			if (rowStartIdxAndCount != null && rowStartIdxAndCount.length > 0) {
				int rowStartIdx = Math.max(0, rowStartIdxAndCount[0]);
				if (rowStartIdx > 0) {
					query.setFirstResult(rowStartIdx);
				}

				if (rowStartIdxAndCount.length > 1) {
					int rowCount = Math.max(0, rowStartIdxAndCount[1]);
					if (rowCount > 0) {
						query.setMaxResults(rowCount);
					}
				}
			}
			
			final List<?> resultList = query.list();
			if (resultList instanceof List) {
				for (Object object : resultList) {
					if (object instanceof Object) {
						final E model = clazzE.cast(object);
						entities.add(model);
					}
				}
			}
			
			if (entities != null && !entities.isEmpty())
				logger.log(Level.INFO, FIND_SUCCESSFUL);
			else 
				logger.log(Level.INFO, FIND_FAILED);
				
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, FIND_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
			throw e;
		 } catch (Exception e) {
			 logger.log(Level.SEVERE, FIND_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
		 }
		
		return entities;
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<E> getAll() {
		logger.log(Level.INFO, GETTING_ALL_E);

		final SessionFactory sessionFactory = this.getHibernateTemplate().getSessionFactory();
		Session session = sessionFactory.openSession();
		if (session == null || !session.isOpen())
			session = getSession(false);
		
		final List<E> entities = new LinkedList<E>();
		
		try {
			final String queryString = CLAUSE_STRING_FROM + this.clazzE.getSimpleName();
			final Query query = session.createQuery(queryString);
			final List<?> resultList = query.list();
			if (resultList instanceof List) {
				for (Object object : resultList) {
					if (object instanceof Object) {
						final E model = clazzE.cast(object);
						entities.add(model);
					}
				}
			}
			
			if (entities != null && !entities.isEmpty())
				logger.log(Level.INFO, GET_SUCCESSFUL);
			else 
				logger.log(Level.INFO, GET_FAILED);
			
		 } catch (RuntimeException e) {
			 logger.log(Level.SEVERE, GET_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
			throw e;
		 } catch (Exception e) {
			 logger.log(Level.SEVERE, GET_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
		 }
	
		return entities;
	}
}