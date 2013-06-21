package com.gabon.info.dao.jpa;

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
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import com.gabon.info.dao.DAOFacade;

/*
 * This class is a abstract JPA class.
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
 * AbstractJpaDAO entity provides the methods for the base persistence definition of the Ts
 */

public abstract class AbstractJpaDAO<E> implements DAOFacade<E> {

	private static final long serialVersionUID = 7248308021760170573L; 
	
	protected static final Logger logger = Logger.getLogger(AbstractJpaDAO.class.getName());
	
	protected static final Object lock = new Object();
	
	
	protected Long id;
	protected Class<E> clazz;

	
	@SuppressWarnings("unchecked")
	public AbstractJpaDAO() { 
		 
		final Type type = getClass().getGenericSuperclass();
		if (type instanceof Type) {
			final Class<ParameterizedType> clazzEParameterizedType = ParameterizedType.class;
			final ParameterizedType parameterizedType = clazzEParameterizedType.cast(type);
		    final Object object = parameterizedType.getActualTypeArguments()[0];
		    this.clazz = Class.class.cast(object); 
		}
	}
	
	public AbstractJpaDAO(Class<E> clazz) { 
		setClazz(clazz);
	}
	
	
	public Long getId(E entity) {
		this.id = getProperty(entity, Id);
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Class<E> getClazz() {
		return this.clazz;
	}

	public void setClazz(Class<E> clazz) {
		this.clazz = clazz;
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

    public Long getProperty(E entity, String property) {
        
    	showMethods(entity);
    	
    	Long returnValue = null;

			try {
				final String methodName = GET + property.substring(0, 1).toUpperCase() + property.substring(1, property.length());
				final Class<?>[] parameterTypes = new Class<?>[] {};
				final Object[] args = new Object[] {};

				final Method method = entity.getClass().getMethod(methodName, parameterTypes);
				final Object object = method.invoke(entity, args);
	            
	            if (object instanceof Long)
	            	returnValue = (Long) object;
	            
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
		
		final String queryString = QUERY_STRING_SELECT_DISTINCT_MODEL_FROM_UQAM_TABLE + this.clazz.getSimpleName() + PREDICAT_STRING_MODEL;
		final JpaHelper jpaHelper = new JpaHelper();
		final EntityManager entityManager = jpaHelper.getEntityManager();
		
		try {
			final Query query = entityManager.createQuery(queryString);
			final List<?> resultList = query.getResultList();
			
			if (resultList == null)
				throw new IllegalArgumentException();
			
			for (Object object : resultList) {		
				final Long entityId = getId(entity);
				final Long modelId = getId(this.clazz.cast(object));
			
				if (entityId instanceof Long && modelId instanceof Long) {
					exists = entityId.longValue() == modelId.longValue();
					
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
    
	
	
	
	public void save(E entity) {
		if (entity == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_SAVE + entity);
		
		final JpaHelper jpaHelper = new JpaHelper();
		
		final EntityManager entityManager = jpaHelper.getEntityManager();
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
			
				entityManager.merge(entity);
			
				entityTransaction.commit();
			
				logger.log(Level.INFO, SAVE_SUCCESSFUL);
				
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, SAVE_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
			if (entityManager.getTransaction().isActive()) 
			entityTransaction.rollback();
			throw e;
		} catch (Exception e) {
			logger.log(Level.SEVERE, SAVE_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
		} finally {
			jpaHelper.close();
		}
	}
	
	public void delete(E entity) {
		delete(getId(entity));
	}
	
	public void delete(Long id) {
		if (id == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_DELETE + id);
		
		final JpaHelper jpaHelper = new JpaHelper();
		
		final EntityManager entityManager = jpaHelper.getEntityManager();
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
			
				entityManager.remove(entity);
				
				entityTransaction.commit();
				
				logger.log(Level.INFO, DELETE_SUCCESSFUL);

		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, DELETE_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
			if (entityManager.getTransaction().isActive()) 
			entityTransaction.rollback();
			throw e;
		} catch (Exception e) {
			logger.log(Level.SEVERE, DELETE_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
		} finally {
			jpaHelper.close();
		}
	}

	public E update(E entity) {
		if (entity == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_UPDATE + entity);
		
		final JpaHelper jpaHelper = new JpaHelper();
		
		final EntityManager entityManager = jpaHelper.getEntityManager();
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
			
			model = this.clazz.cast(entityManager.merge(entity));
			
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
		} finally {
			jpaHelper.close();
		}
		
		return model;
	}

	public E findById(Long id) {
		if (id == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_FIND + id);
		
		final JpaHelper jpaHelper = new JpaHelper();
		
		final EntityManager entityManager = jpaHelper.getEntityManager();
		if (entityManager == null)
			throw new NullPointerException();
		
		if (!entityManager.isOpen())
			entityManager.joinTransaction();
		
		E entity = null;
		
		logger.log(Level.INFO, FINDING_E_BY_ID + id);
		
		try {
			entity = this.clazz.cast(entityManager.find(this.clazz, id));
			
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
		} finally {
			jpaHelper.close();
		}
		
		return entity;
	}
	
	public List<E> findByProperty(String propertyName, Object propertyValue, int... rowStartIdxAndCount) {
		
		if (StringUtils.isBlank(StringUtils.trim(propertyName)) || propertyValue == null || rowStartIdxAndCount.length < 0)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_FIND + propertyName + ", propertyValue: " + propertyValue + ", rowStartIdxAndCount: " + rowStartIdxAndCount);
		
		final JpaHelper jpaHelper = new JpaHelper();
		
		final EntityManager entityManager = jpaHelper.getEntityManager();
		if (entityManager == null)
			throw new NullPointerException();
		
		if (!entityManager.isOpen())
			entityManager.joinTransaction();
		
		logger.log(Level.INFO, FINDING_ALL_E_BY_PROPERTY_NAME + propertyName + ", propertyValue: " + propertyValue);
		
		final List<E> entities = new LinkedList<E>();
		
		try {
			final String queryString = QUERY_STRING_SELECT_DISTINCT_MODEL_FROM_UQAM_TABLE + this.clazz.getSimpleName() + CLAUSE_STRING_MODEL_WHERE_MODEL_DOT + propertyName  + " = :propertyValue";
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
						final E model = clazz.cast(object);
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
		 } finally {
			 jpaHelper.close();
		 }
		
		return entities;
	}

	public List<E> findAll(int... rowStartIdxAndCount) {
		
		if (rowStartIdxAndCount.length < 0)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_FIND + ", rowStartIdxAndCount: " + rowStartIdxAndCount);

		final JpaHelper jpaHelper = new JpaHelper();
		
		final EntityManager entityManager = jpaHelper.getEntityManager();
		if (entityManager == null)
			throw new NullPointerException();
		
		if (!entityManager.isOpen())
			entityManager.joinTransaction();
		
		logger.log(Level.INFO, FINDING_ALL_E_BY_ROWSSTARTIDXANDCOUNT + rowStartIdxAndCount);
		
		final List<E> entities = new LinkedList<E>();
		
		try {
			final String queryString = QUERY_STRING_SELECT_DISTINCT_MODEL_FROM_UQAM_TABLE + this.clazz.getSimpleName() + PREDICAT_STRING_MODEL;
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
						final E model = clazz.cast(object);
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
		 } finally {
			 jpaHelper.close();
		 }
		
		return entities;
	}
	
	public List<E> getAll() {
		logger.log(Level.INFO, GETTING_ALL_E);

		final JpaHelper jpaHelper = new JpaHelper();
		
		final EntityManager entityManager = jpaHelper.getEntityManager();
		if (entityManager == null)
			throw new NullPointerException();
		
		if (!entityManager.isOpen())
			entityManager.joinTransaction();
		
		final List<E> entities = new LinkedList<E>();
		
		try {
			final String queryString = QUERY_STRING_SELECT_DISTINCT_MODEL_FROM_UQAM_TABLE + this.clazz.getSimpleName() + PREDICAT_STRING_MODEL;
			final Query query = entityManager.createQuery(queryString);
			final List<?> resultList = query.getResultList();
			if (resultList instanceof List) {
				for (Object object : resultList) {
					if (object instanceof Object) {
						final E model = clazz.cast(object);
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
		 } finally {
			 jpaHelper.close();
		 }
	
		return entities;
	}
}