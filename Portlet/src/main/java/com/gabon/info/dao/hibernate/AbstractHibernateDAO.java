package com.gabon.info.dao.hibernate;

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

import com.gabon.info.dao.DAOFacade;

/*
 * This class is a abstract Hibernate class.
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
 * AbstractHibernateDAO entity provides the methods for the base persistence definition of the Models
 */

public abstract class AbstractHibernateDAO<E> implements DAOFacade<E> {

	private static final long serialVersionUID = -820970953635282051L; 
	
	protected static final Logger logger = Logger.getLogger(AbstractHibernateDAO.class.getName());
	
	protected static final Object lock = new Object();
	
	
	protected Long id;
	protected Class<E> clazz;

	
	@SuppressWarnings("unchecked")
	public AbstractHibernateDAO() { 
		
		final Type type = getClass().getGenericSuperclass();
		if (type instanceof Type) {
			final Class<ParameterizedType> clazzEParameterizedType = ParameterizedType.class;
			final ParameterizedType parameterizedType = clazzEParameterizedType.cast(type);
		    final Object object = parameterizedType.getActualTypeArguments()[0];
		    this.clazz = Class.class.cast(object); 
		}
	}
	
	public AbstractHibernateDAO(Class<E> clazz) { 
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
		
		final String queryString = CLAUSE_STRING_FROM + this.clazz.getSimpleName();
		final HibernateHelper hibernateHelper = new HibernateHelper();
		final SessionFactory sessionFactory = hibernateHelper.getSessionFactory();
		final Session session = sessionFactory.openSession();
		
		try {
			final Query query = session.createQuery(queryString);
			final List<?> resultList = query.list();
			
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
		
		final HibernateHelper hibernateHelper = new HibernateHelper();
		
		Session session = hibernateHelper.getSession();
		if (session == null)
			throw new NullPointerException();
		
		if (!session.isOpen())  {
			final SessionFactory sessionFactory = hibernateHelper.getSessionFactory();
			session = sessionFactory.openSession();
		}
		
		if (isEntityExists(entity))
			throw new ObjectNotFoundException(getId(entity), this.clazz.getSimpleName());
		
		final Transaction transaction = session.beginTransaction();
		if (transaction == null)
			throw new NullPointerException(ILLEGAL_ARGUMENT_SAVE + entity);
				
		logger.log(Level.INFO, SAVING_E +entity+ INSTANCE);
		
		try {
			if (!transaction.isActive())
				transaction.begin();
			
			session.save(entity);
			
			transaction.commit();
			
			logger.log(Level.INFO, SAVE_SUCCESSFUL);
				
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, SAVE_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
			if (transaction.isActive()) 
				transaction.rollback();
			throw e;
		} catch (Exception e) {
			logger.log(Level.SEVERE, SAVE_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
		} finally {
			hibernateHelper.close();
		}
	}
	
	public void delete(E entity) {
		delete(getId(entity));
	}
	
	public void delete(Long id) {
		if (id == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_DELETE + id);
		
		final HibernateHelper hibernateHelper = new HibernateHelper();
		
		Session session = hibernateHelper.getSession();
		if (session == null)
			throw new NullPointerException();
		
		if (!session.isOpen())  {
			final SessionFactory sessionFactory = hibernateHelper.getSessionFactory();
			session = sessionFactory.openSession();
		}
		
		final E entity = findById(id);
		if (entity == null)
			throw new ObjectNotFoundException(id, this.clazz.getSimpleName());
		
		final Transaction transaction = session.getTransaction();
		if (transaction == null)
			throw new NullPointerException();
		
		logger.log(Level.INFO, DELETING_E + id);
		
		try {
			if (!transaction.isActive())
				transaction.begin();
			
				session.delete(entity);
				
				transaction.commit();
				
				logger.log(Level.INFO, DELETE_SUCCESSFUL);

		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, DELETE_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
			if (session.getTransaction().isActive()) 
			transaction.rollback();
			throw e;
		} catch (Exception e) {
			logger.log(Level.SEVERE, DELETE_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
		} finally {
			hibernateHelper.close();
		}
	}

	public E update(E entity) {
		if (entity == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_UPDATE + entity);
		
		final HibernateHelper hibernateHelper = new HibernateHelper();
		
		Session session = hibernateHelper.getSession();
		if (session == null)
			throw new NullPointerException();
		
		if (!session.isOpen())  {
			final SessionFactory sessionFactory = hibernateHelper.getSessionFactory();
			session = sessionFactory.openSession();
		}
		
		if (!isEntityExists(entity))
			throw new ObjectNotFoundException(getId(entity), this.clazz.getSimpleName());
		
		final Transaction transaction = session.getTransaction();
		if (transaction == null)
			throw new NullPointerException();
		
		logger.log(Level.INFO, UPDATING_E +entity+ INSTANCE);
		
		E model = null;
				
		try {
			if (!transaction.isActive())
				transaction.begin();
			
			model = this.clazz.cast(session.merge(entity));
			
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
		} finally {
			hibernateHelper.close();
		}
		
		return model;
	}

	public E findById(Long id) {
		if (id == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_FIND + id);
		
		final HibernateHelper hibernateHelper = new HibernateHelper();
		
		Session session = hibernateHelper.getSession();
		if (session == null)
			throw new NullPointerException();
		
		if (!session.isOpen())  {
			final SessionFactory sessionFactory = hibernateHelper.getSessionFactory();
			session = sessionFactory.openSession();
		}
		
		E entity = null;
		
		logger.log(Level.INFO, FINDING_E_BY_ID + id);
		
		try {
			entity = this.clazz.cast(session.get(this.clazz, id));
			
			if (entity != null) {
				
				logger.log(Level.INFO, FIND_SUCCESSFUL);
				
			} else  {
				logger.log(Level.INFO, FIND_FAILED);
				throw new ObjectNotFoundException(id, this.clazz.getSimpleName());
			}
			
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, FIND_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.log(Level.SEVERE, FIND_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
		} finally {
			hibernateHelper.close();
		}
		
		return entity;
	}
	
	public List<E> findByProperty(String propertyName, Object propertyValue, int... rowStartIdxAndCount) {
		
		if (StringUtils.isBlank(StringUtils.trim(propertyName)) || propertyValue == null || rowStartIdxAndCount.length < 0)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_FIND + propertyName + ", propertyValue: " + propertyValue + ", rowStartIdxAndCount: " + rowStartIdxAndCount);
		
		final HibernateHelper hibernateHelper = new HibernateHelper();
		
		Session session = hibernateHelper.getSession();
		if (session == null)
			throw new NullPointerException();
		
		if (!session.isOpen())  {
			final SessionFactory sessionFactory = hibernateHelper.getSessionFactory();
			session = sessionFactory.openSession();
		}
		
		logger.log(Level.INFO, FINDING_ALL_E_BY_PROPERTY_NAME + propertyName + ", propertyValue: " + propertyValue);
		
		final List<E> entities = new LinkedList<E>();
		
		try {
			final String queryString = CLAUSE_STRING_FROM + this.clazz.getSimpleName() + CLAUSE_STRING_MODEL_WHERE_MODEL_DOT + propertyName  + " = :propertyValue";
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
			 hibernateHelper.close();
		 }
		
		return entities;
	}

	public List<E> findAll(int... rowStartIdxAndCount) {
		
		if (rowStartIdxAndCount.length < 0)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_FIND + ", rowStartIdxAndCount: " + rowStartIdxAndCount);

		final HibernateHelper hibernateHelper = new HibernateHelper();
		
		Session session = hibernateHelper.getSession();
		if (session == null)
			throw new NullPointerException();
		
		if (!session.isOpen())  {
			final SessionFactory sessionFactory = hibernateHelper.getSessionFactory();
			session = sessionFactory.openSession();
		}
		
		logger.log(Level.INFO, FINDING_ALL_E_BY_ROWSSTARTIDXANDCOUNT + rowStartIdxAndCount);
		
		final List<E> entities = new LinkedList<E>();
		
		try {
			final String queryString = CLAUSE_STRING_FROM + this.clazz.getSimpleName();
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
			 hibernateHelper.close();
		 }
		
		return entities;
	}
	
	public List<E> getAll() {
		logger.log(Level.INFO, GETTING_ALL_E);

		final HibernateHelper hibernateHelper = new HibernateHelper();
		
		Session session = hibernateHelper.getSession();
		if (session == null)
			throw new NullPointerException();
		
		if (!session.isOpen())  {
			final SessionFactory sessionFactory = hibernateHelper.getSessionFactory();
			session = sessionFactory.openSession();
		}
		
		final List<E> entities = new LinkedList<E>();
		
		try {
			final String queryString = CLAUSE_STRING_FROM + this.clazz.getSimpleName();
			final Query query = session.createQuery(queryString);
			final List<?> resultList = query.list();
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
			 hibernateHelper.close();
		 }
	
		return entities;
	}
}