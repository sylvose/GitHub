package com.gabon.info.dao.spring.jpa;

import static com.gabon.info.util.RestFulConstants.BEAN_ABSTRACT_JPA_DAO_SUPPORT;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.orm.jpa.support.JpaDaoSupport;
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
 * This class has the DAO class extend JpaDaoSupport and uses the JpaTemplate, but Spring recommends the native JPA style of coding.
 * 
 */

@SuppressWarnings({"deprecation", "unchecked"})
@Transactional
@Repository(BEAN_ABSTRACT_JPA_DAO_SUPPORT)
public abstract class AbstractJpaDaoSupport<E, PK extends Serializable> extends JpaDaoSupport implements DAOSupportFacade<E, PK> {
	
	private static final long serialVersionUID = -6529645663917449504L;	
	
	protected static final Logger logger = Logger.getLogger(AbstractJpaDaoSupport.class.getName());
	
	protected static final Object lock = new Object();
	
	protected static final ApplicationContext applicationContext = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_ROOT);
	
	
	protected PK id;
	protected Class<E> clazzE;
	protected Class<PK> clazzPK;
	
	
	
	public AbstractJpaDaoSupport() { 
		super();
		
		final Type type = getClass().getGenericSuperclass();
		if (type instanceof Type) {
			final Class<ParameterizedType> clazzParameterizedType = ParameterizedType.class;
			final ParameterizedType parameterizedType = clazzParameterizedType.cast(type);
		    final Object object = parameterizedType.getActualTypeArguments()[0];
		    this.clazzE = Class.class.cast(object); 
		}
		
		if (applicationContext instanceof ApplicationContext) {
			final JpaTemplate jpaTemplate = (JpaTemplate) applicationContext.getBean(BEAN_JPA_TEMPLATE);
			final EntityManagerFactory entityManagerFactory = (EntityManagerFactory) applicationContext.getBean(BEAN_ENTITY_MANAGER_FACTORY);
			final EntityManager entityManager = entityManagerFactory.createEntityManager();
			
			jpaTemplate.setEntityManagerFactory(entityManagerFactory);
			jpaTemplate.setEntityManager(entityManager);
			setJpaTemplate(jpaTemplate);
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
		
		final String queryString = QUERY_STRING_SELECT_DISTINCT_MODEL_FROM_UQAM_TABLE + this.clazzE.getSimpleName() + PREDICAT_STRING_MODEL;
		final EntityManager entityManager = this.getJpaTemplate().getEntityManager();
		
		try {
			final Query query = entityManager.createQuery(queryString);
			final List<?> resultList = query.getResultList();
			
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
		
		final EntityManager entityManager = this.getJpaTemplate().getEntityManager();
		
		try {
			final E entity = this.clazzE.cast(entityManager.find(this.clazzE, id));
			
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
		
		final EntityManager entityManager = this.getJpaTemplate().getEntityManager();
		if (entityManager == null)
			throw new NullPointerException();
		
		if (!entityManager.isOpen())
			entityManager.joinTransaction();
		
		if (isEntityExists(entity))
			throw new EntityExistsException(ILLEGAL_ARGUMENT_SAVE + entity);
		
		final EntityTransaction entityTransaction = entityManager.getTransaction();
		if (entityTransaction == null)
			throw new NullPointerException();
		
		logger.log(Level.INFO, SAVING_E +entity+ INSTANCE);
		
		try {
			if (!entityTransaction.isActive())
				entityTransaction.begin();
			
				this.getJpaTemplate().merge(entity);
			
				entityTransaction.commit();
			
				logger.log(Level.INFO, SAVE_SUCCESSFUL);
				
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, SAVE_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
			if (entityManager.getTransaction().isActive()) 
			entityTransaction.rollback();
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
		
		final EntityManager entityManager = this.getJpaTemplate().getEntityManager();
		if (entityManager == null)
			throw new NullPointerException();
		
		if (!entityManager.isOpen())
			entityManager.joinTransaction();
		
		final E entity = findById(id);
		if (entity == null)
			throw new EntityNotFoundException(ILLEGAL_ARGUMENT_DELETE + id);
		
		final EntityTransaction entityTransaction = entityManager.getTransaction();
		if (entityTransaction == null)
			throw new NullPointerException();
		
		logger.log(Level.INFO, DELETING_E + id);
		
		try {
			if (!entityTransaction.isActive())
				entityTransaction.begin();
			
				this.getJpaTemplate().remove(entity);
				
				entityTransaction.commit();
				
				logger.log(Level.INFO, DELETE_SUCCESSFUL);

		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, DELETE_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
			if (entityManager.getTransaction().isActive()) 
			entityTransaction.rollback();
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
		
		final EntityManager entityManager = this.getJpaTemplate().getEntityManager();
		if (entityManager == null)
			throw new NullPointerException();
		
		if (!entityManager.isOpen())
			entityManager.joinTransaction();
		
		if (!isEntityExists(entity))
			throw new EntityNotFoundException(ILLEGAL_ARGUMENT_UPDATE + entity);
		
		final EntityTransaction entityTransaction = entityManager.getTransaction();
		if (entityTransaction == null)
			throw new NullPointerException();
		
		logger.log(Level.INFO, UPDATING_E +entity+ INSTANCE);
		
		E model = null;
				
		try {
			if (!entityTransaction.isActive())
				entityTransaction.begin();
			
			model = this.clazzE.cast(this.getJpaTemplate().merge(entity));
			
			if (model != null) {
				entityTransaction.commit();
				
				logger.log(Level.INFO, UPDATE_SUCCESFUL);
				
			} else
				logger.log(Level.INFO, UPDATE_FAILED);
				
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, UPDATE_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
			if (entityManager.getTransaction().isActive()) 
			entityTransaction.rollback();
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
		
		final EntityManager entityManager = this.getJpaTemplate().getEntityManager();
		if (entityManager == null)
			throw new NullPointerException();
		
		if (!entityManager.isOpen())
			entityManager.joinTransaction();
		
		E entity = null;
		
		logger.log(Level.INFO, FINDING_E_BY_ID + id);
		
		try {
			entity = this.clazzE.cast(this.getJpaTemplate().find(this.clazzE, id));
			
			if (entity != null) {
				
				logger.log(Level.INFO, FIND_SUCCESSFUL);
				
			} else  {
				logger.log(Level.INFO, FIND_FAILED);
				throw new EntityNotFoundException(ILLEGAL_ARGUMENT_FIND + id);
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
		
		final EntityManager entityManager = this.getJpaTemplate().getEntityManager();
		if (entityManager == null)
			throw new NullPointerException();
		
		if (!entityManager.isOpen())
			entityManager.joinTransaction();
		
		logger.log(Level.INFO, FINDING_ALL_E_BY_PROPERTY_NAME + propertyName + ", propertyValue: " + propertyValue);
		
		final List<E> entities = new LinkedList<E>();
		
		try {
			final String queryString = QUERY_STRING_SELECT_DISTINCT_MODEL_FROM_UQAM_TABLE + this.clazzE.getSimpleName() + CLAUSE_STRING_MODEL_WHERE_MODEL_DOT + propertyName  + " = :propertyValue";
			final Query query = entityManager.createQuery(queryString);
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
			
			final List<?> resultList = query.getResultList();
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

		final EntityManager entityManager = this.getJpaTemplate().getEntityManager();
		if (entityManager == null)
			throw new NullPointerException();
		
		if (!entityManager.isOpen())
			entityManager.joinTransaction();
		
		logger.log(Level.INFO, FINDING_ALL_E_BY_ROWSSTARTIDXANDCOUNT + rowStartIdxAndCount);
		
		final List<E> entities = new LinkedList<E>();
		
		try {
			final String queryString = QUERY_STRING_SELECT_DISTINCT_MODEL_FROM_UQAM_TABLE + this.clazzE.getSimpleName() + PREDICAT_STRING_MODEL;
			final Query query = entityManager.createQuery(queryString);
			
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
			
			final List<?> resultList = query.getResultList();
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

		final EntityManager entityManager = this.getJpaTemplate().getEntityManager();
		if (entityManager == null)
			throw new NullPointerException();
		
		if (!entityManager.isOpen())
			entityManager.joinTransaction();
		
		final List<E> entities = new LinkedList<E>();
		
		try {
			final String queryString = QUERY_STRING_SELECT_DISTINCT_MODEL_FROM_UQAM_TABLE + this.clazzE.getSimpleName() + PREDICAT_STRING_MODEL;
			final Query query = entityManager.createQuery(queryString);
			final List<?> resultList = query.getResultList();
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