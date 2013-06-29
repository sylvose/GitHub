package com.gabon.info.dao.ibatis;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;

import com.gabon.info.dao.DAOFacade;
import com.ibatis.sqlmap.client.SqlMapClient;

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

public abstract class AbstractIBatisDAO<E> implements DAOFacade<E> {

	private static final long serialVersionUID = -820970953635282051L; 
	
	protected static final Logger logger = Logger.getLogger(AbstractIBatisDAO.class.getName());
	
	protected static final Object lock = new Object();
	
	
	protected Long id;
	protected Class<E> clazz;
	protected String statement;

	
	
	@SuppressWarnings("unchecked")
	public AbstractIBatisDAO() { 
		
		final Type type = getClass().getGenericSuperclass();
		if (type instanceof Type) {
			final Class<ParameterizedType> clazzEParameterizedType = ParameterizedType.class;
			final ParameterizedType parameterizedType = clazzEParameterizedType.cast(type);
		    final Object object = parameterizedType.getActualTypeArguments()[0];
		    this.clazz = Class.class.cast(object); 
		}
	}
	
	public AbstractIBatisDAO(Class<E> clazz) { 
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
	
	
	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
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
		
		final String queryString = QUERY_STRING_SELECT_STAR_FROM_UQAM_TABLE + this.clazz.getSimpleName().toUpperCase();
		final IBatisHelper ibatisHelper = new IBatisHelper();
		Connection connection = ibatisHelper.getConnection();
		
		try {
			if (connection == null || connection.isClosed()) {
				final SqlMapClient sqlMapClient = ibatisHelper.getSqlMapClient();
				final DataSource dataSource = sqlMapClient.getDataSource();
				connection = dataSource.getConnection();
			}
			
			if (connection != null)
				connection.setAutoCommit(false);
			
			final PreparedStatement preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			final ResultSet resultSet = preparedStatement.executeQuery();
			
			if (resultSet == null)
				throw new IllegalArgumentException();
			
			while (resultSet.next()) {		
				final Long entityId = getId(entity);
				final Long modelId = resultSet.getLong(ID);
			
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
		} finally {
			ibatisHelper.close();
		}
		
		return exists;
	}
    

    
	
    
	public void save(E entity) {
		if (entity == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_SAVE + entity);
		
		if (StringUtils.isBlank(StringUtils.trim(this.statement)))
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_SAVE + this.statement);
		
		final IBatisHelper ibatisHelper = new IBatisHelper();
		final SqlMapClient sqlMapClient = ibatisHelper.getSqlMapClient();
		final Connection connection = ibatisHelper.getConnection();
		
		if (isEntityExists(entity))
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_SAVE + entity);
				
		logger.log(Level.INFO, SAVING_E +entity+ INSTANCE);
		
		try {
			sqlMapClient.startTransaction();
			
			final Object affectedRows = sqlMapClient.insert(this.statement, entity);
			if (affectedRows == null)
				throw new IllegalArgumentException(SAVING_E +entity+ INSTANCE_FAILED_NO_ROWS_AFFECTED);
			
			connection.commit();
			
			logger.log(Level.INFO, SAVE_SUCCESSFUL);
				
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, SAVE_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
			try {
				if (!connection.isClosed()) 
				connection.rollback();
			} catch (SQLException sql) {
				logger.log(Level.SEVERE, SAVE_FAILED + sql.getClass().getName() + MESSAGE + e.getMessage(), sql);
			}
			throw e;
		} catch (Exception e) {
			logger.log(Level.SEVERE, SAVE_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
		} finally {
			try {
				sqlMapClient.endTransaction();
			} catch (SQLException sql) {
				logger.log(Level.SEVERE, SAVE_FAILED + sql.getClass().getName() + MESSAGE + sql.getMessage(), sql);
			}
			ibatisHelper.close();
		}
	}
	
	public void delete(E entity) {
		delete(getId(entity));
	}
	
	public void delete(Long id) {
		if (id == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_DELETE + id);
		
		if (StringUtils.isBlank(StringUtils.trim(this.statement)))
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_DELETE + this.statement);
		
		final IBatisHelper ibatisHelper = new IBatisHelper();
		final SqlMapClient sqlMapClient = ibatisHelper.getSqlMapClient();
		final Connection connection = ibatisHelper.getConnection();
		
		final E entity = findById(id);
		if (entity == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_DELETE + id);
		
		logger.log(Level.INFO, DELETING_E + id);
		
		try {
			sqlMapClient.startTransaction();
			
			final int affectedRows = sqlMapClient.delete(this.statement, id);
			if (affectedRows == 0)
				throw new IllegalArgumentException(DELETING_E +entity+ INSTANCE_FAILED_NO_ROWS_AFFECTED);
			
			connection.commit();
			
			logger.log(Level.INFO, DELETE_SUCCESSFUL);
				
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, DELETE_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
			try {
				if (!connection.isClosed()) 
				connection.rollback();
			} catch (SQLException sql) {
				logger.log(Level.SEVERE, DELETE_FAILED + sql.getClass().getName() + MESSAGE + e.getMessage(), sql);
			}
			throw e;
		} catch (Exception e) {
			logger.log(Level.SEVERE, DELETE_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
		} finally {
			try {
				sqlMapClient.endTransaction();
			} catch (SQLException sql) {
				logger.log(Level.SEVERE, DELETE_FAILED + sql.getClass().getName() + MESSAGE + sql.getMessage(), sql);
			}
			ibatisHelper.close();
		}
	}

	public E update(E entity) {
		if (entity == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_UPDATE + entity);
		
		if (StringUtils.isBlank(StringUtils.trim(this.statement)))
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_UPDATE + this.statement);
		
		final IBatisHelper ibatisHelper = new IBatisHelper();
		final SqlMapClient sqlMapClient = ibatisHelper.getSqlMapClient();
		final Connection connection = ibatisHelper.getConnection();
		
		if (!isEntityExists(entity))
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_UPDATE + entity);
		
		logger.log(Level.INFO, UPDATING_E +entity+ INSTANCE);
		
		E model = null;
				
		try {
			sqlMapClient.startTransaction();
			
			final int affectedRows = sqlMapClient.update(this.statement, entity);
			if (affectedRows == 0)
				throw new IllegalArgumentException(DELETING_E +entity+ INSTANCE_FAILED_NO_ROWS_AFFECTED);
			
			model = findById(getId(entity));
			
			if (model != null) {
				connection.commit();
				
				logger.log(Level.INFO, UPDATE_SUCCESFUL);
				
			} else
				logger.log(Level.INFO, UPDATE_FAILED);
				
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, UPDATE_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
			try {
				if (!connection.isClosed()) 
				connection.rollback();
			} catch (SQLException sql) {
				logger.log(Level.SEVERE, UPDATE_FAILED + sql.getClass().getName() + MESSAGE + e.getMessage(), sql);
			}
			throw e;
		} catch (Exception e) {
			logger.log(Level.SEVERE, UPDATE_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
		} finally {
			try {
				sqlMapClient.endTransaction();
			} catch (SQLException sql) {
				logger.log(Level.SEVERE, UPDATE_FAILED + sql.getClass().getName() + MESSAGE + sql.getMessage(), sql);
			}
			ibatisHelper.close();
		}
		
		return model;
	}

	public E findById(Long id) {
		if (id == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_FIND + id);
		
		if (StringUtils.isBlank(StringUtils.trim(this.statement)))
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_FIND + this.statement);
		
		final IBatisHelper ibatisHelper = new IBatisHelper();
		final SqlMapClient sqlMapClient = ibatisHelper.getSqlMapClient();
		
		E entity = null;
		
		logger.log(Level.INFO, FINDING_E_BY_ID + id);
		
		try {
			sqlMapClient.startTransaction();
			
			entity = this.clazz.cast(sqlMapClient.queryForObject(this.statement, id));
			
			if (entity != null) {
				
				logger.log(Level.INFO, FIND_SUCCESSFUL);
				
			} else  {
				logger.log(Level.INFO, FIND_FAILED);
				throw new IllegalArgumentException(ILLEGAL_ARGUMENT_FIND + id);
			}
			
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, FIND_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.log(Level.SEVERE, FIND_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
		} finally {
			ibatisHelper.close();
		}
		
		return entity;
	}
	
	public List<E> findByProperty(String propertyName, Object propertyValue, int... rowStartIdxAndCount) {
		
		if (StringUtils.isBlank(StringUtils.trim(propertyName)) || propertyValue == null || rowStartIdxAndCount.length < 0)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_FIND + propertyName + ", propertyValue: " + propertyValue + ", rowStartIdxAndCount: " + rowStartIdxAndCount);
		
		if (StringUtils.isBlank(StringUtils.trim(this.statement)))
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_FIND + this.statement);
		
		final IBatisHelper ibatisHelper = new IBatisHelper();
		final SqlMapClient sqlMapClient = ibatisHelper.getSqlMapClient();
		
		logger.log(Level.INFO, FINDING_ALL_E_BY_PROPERTY_NAME + propertyName + ", propertyValue: " + propertyValue);
		
		List<E> entities = null;
		
		try {
			final Map<String, Object> map = new HashMap<String, Object>();
			map.put(propertyName, propertyValue);
			
			final List<?> resultList = sqlMapClient.queryForList(this.statement, map);
			
			if (resultList != null) {
				entities = new LinkedList<E>();
				for (Object object : resultList) {
					if (object instanceof Object) {
						final E model = this.clazz.cast(object);
						entities.add(model);
					}
				}
			 } 
			
			if (rowStartIdxAndCount != null && rowStartIdxAndCount.length > 0) {
				final int rowStartIdx = Math.max(0, rowStartIdxAndCount[0]);
				
				if (rowStartIdx > 0 && rowStartIdx <= entities.size())
					entities = entities.subList(rowStartIdx, entities.size());

				if (rowStartIdxAndCount.length > 1) {
					int rowCount = Math.max(0, rowStartIdxAndCount[1]);
					if (rowCount > 0)
						entities = entities.subList(0, rowCount);
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
			 ibatisHelper.close();
		 }
		
		return entities;
	}

	public List<E> findAll(int... rowStartIdxAndCount) {
		
		if (rowStartIdxAndCount.length < 0)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_FIND + ", rowStartIdxAndCount: " + rowStartIdxAndCount);

		if (StringUtils.isBlank(StringUtils.trim(this.statement)))
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_FIND + this.statement);
		
		final IBatisHelper ibatisHelper = new IBatisHelper();
		final SqlMapClient sqlMapClient = ibatisHelper.getSqlMapClient();
		
		logger.log(Level.INFO, FINDING_ALL_E_BY_ROWSSTARTIDXANDCOUNT + rowStartIdxAndCount);
		
		List<E> entities = null;
		
		try {	
			final List<?> resultList = sqlMapClient.queryForList(this.statement);
			
			if (resultList != null) {
				entities = new LinkedList<E>();
				for (Object object : resultList) {
					if (object instanceof Object) {
						final E model = this.clazz.cast(object);
						entities.add(model);
					}
				}
			 } 
			
			if (rowStartIdxAndCount != null && rowStartIdxAndCount.length > 0) {
				final int rowStartIdx = Math.max(0, rowStartIdxAndCount[0]);
				
				if (rowStartIdx > 0 && rowStartIdx <= entities.size())
					entities = entities.subList(rowStartIdx, entities.size());

				if (rowStartIdxAndCount.length > 1) {
					int rowCount = Math.max(0, rowStartIdxAndCount[1]);
					if (rowCount > 0)
						entities = entities.subList(0, rowCount);
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
			 ibatisHelper.close();
		 }
		
		return entities;
	}
	
	public List<E> getAll() {
		
		if (StringUtils.isBlank(StringUtils.trim(this.statement)))
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_GET + this.statement);
		
		logger.log(Level.INFO, GETTING_ALL_E);

		final IBatisHelper ibatisHelper = new IBatisHelper();
		final SqlMapClient sqlMapClient = ibatisHelper.getSqlMapClient();
		
		List<E> entities = null;
		
		try {	
			final List<?> resultList = sqlMapClient.queryForList(this.statement);
			
			if (resultList != null) {
				entities = new LinkedList<E>();
				for (Object object : resultList) {
					if (object instanceof Object) {
						final E model = this.clazz.cast(object);
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
			 ibatisHelper.close();
		 }
	
		return entities;
	}
}