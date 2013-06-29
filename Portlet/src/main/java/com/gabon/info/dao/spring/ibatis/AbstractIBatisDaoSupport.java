package com.gabon.info.dao.spring.ibatis;

import static com.gabon.info.util.Constants.BEAN_ABSTRACT_IBATIS_DAO_SUPPORT;

import java.io.Serializable;
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
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
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
 * This class has the DAO class extend SqlSessionDaoSupport and uses the SqlSessionTemplate , but Spring recommends the native iBatis style of coding.
 * 
 */

@Transactional
@Repository(BEAN_ABSTRACT_IBATIS_DAO_SUPPORT)
public abstract class AbstractIBatisDaoSupport<E, PK extends Serializable> extends SqlMapClientDaoSupport implements DAOSupportFacade<E, PK> {	
	
	private static final long serialVersionUID = -5733408712041255431L;

	protected static final Logger logger = Logger.getLogger(AbstractIBatisDaoSupport.class.getName());
	
	protected static final Object lock = new Object();
	
	protected static final ApplicationContext applicationContext = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_ROOT);
	
	
	
	protected PK id;
	protected Class<E> clazzE;
	protected Class<PK> clazzPK;
	protected String statement;
	
	
	
	@SuppressWarnings("unchecked")
	public AbstractIBatisDaoSupport() { 
		super();
		
		final Type type = getClass().getGenericSuperclass();
		if (type instanceof Type) {
			final Class<ParameterizedType> clazzEParameterizedType = ParameterizedType.class;
			final ParameterizedType parameterizedType = clazzEParameterizedType.cast(type);
		    final Object object = parameterizedType.getActualTypeArguments()[0];
		    this.clazzE = Class.class.cast(object); 
		}
		
		if (applicationContext instanceof ApplicationContext) {
			final SqlMapClientTemplate sqlMapClientTemplate = (SqlMapClientTemplate) applicationContext.getBean(BEAN_IBATIS_TEMPLATE);
			setSqlMapClientTemplate(sqlMapClientTemplate);
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
		
		final String queryString = QUERY_STRING_SELECT_STAR_FROM_UQAM_TABLE + this.clazzE.getSimpleName().toUpperCase();
		
		try {
			Connection connection = this.getSqlMapClient().getCurrentConnection();
			
			if (connection == null || connection.isClosed()) {
				final DataSource dataSource = this.getSqlMapClient().getDataSource();
				connection = dataSource.getConnection();
			}
			
			if (connection != null)
				connection.setAutoCommit(false);
			
			final PreparedStatement preparedStatement = connection.prepareStatement(queryString, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			final ResultSet resultSet = preparedStatement.executeQuery();
			
			if (resultSet == null)
				throw new IllegalArgumentException();
			
			while (resultSet.next()) {		
				final PK entityId = getId(entity);
				final PK modelId = getId(this.clazzE.cast(resultSet.getLong(ID)));
			
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
		
		if (StringUtils.isBlank(StringUtils.trim(this.statement)))
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_SAVE + this.statement);
		
		if (isEntityExists(entity))
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_SAVE + entity);
				
		logger.log(Level.INFO, SAVING_E +entity+ INSTANCE);
		
		Connection connection = null;
				
		try {
			connection = this.getSqlMapClient().getCurrentConnection();
			
			this.getSqlMapClient().startTransaction();
			
			final Object affectedRows = this.getSqlMapClient().insert(this.statement, entity);
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
				this.getSqlMapClient().endTransaction();
				connection.close();
			} catch (SQLException sql) {
				logger.log(Level.SEVERE, SAVE_FAILED + sql.getClass().getName() + MESSAGE + sql.getMessage(), sql);
			}
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
		
		if (StringUtils.isBlank(StringUtils.trim(this.statement)))
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_DELETE + this.statement);
		
		final E entity = findById(id);
		if (entity == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_DELETE + id);
		
		logger.log(Level.INFO, DELETING_E + id);
		
		Connection connection = null;
		
		try {
			connection = this.getSqlMapClient().getCurrentConnection();
			
			this.getSqlMapClient().startTransaction();
			
			final int affectedRows = this.getSqlMapClient().delete(this.statement, id);
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
				this.getSqlMapClient().endTransaction();
				connection.close();
			} catch (SQLException sql) {
				logger.log(Level.SEVERE, DELETE_FAILED + sql.getClass().getName() + MESSAGE + sql.getMessage(), sql);
			}
		}
	}

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public E update(E entity) {
		if (entity == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_UPDATE + entity);
		
		if (StringUtils.isBlank(StringUtils.trim(this.statement)))
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_UPDATE + this.statement);
		
		if (!isEntityExists(entity))
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_UPDATE + entity);
		
		logger.log(Level.INFO, UPDATING_E +entity+ INSTANCE);
		
		Connection connection = null;
		
		E model = null;
				
		try {
			connection = this.getSqlMapClient().getCurrentConnection();
			
			this.getSqlMapClient().startTransaction();
			
			final int affectedRows = this.getSqlMapClient().update(this.statement, entity);
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
				this.getSqlMapClient().endTransaction();
				connection.close();
			} catch (SQLException sql) {
				logger.log(Level.SEVERE, UPDATE_FAILED + sql.getClass().getName() + MESSAGE + sql.getMessage(), sql);
			}
		}
		
		return model;
	}

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public E findById(PK id) {
		if (id == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_FIND + id);
		
		if (StringUtils.isBlank(StringUtils.trim(this.statement)))
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_FIND + this.statement);
		
		E entity = null;
		
		logger.log(Level.INFO, FINDING_E_BY_ID + id);
		
		try {
			this.getSqlMapClient().startTransaction();
			
			entity = this.clazzE.cast(this.getSqlMapClient().queryForObject(this.statement, id));
			
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
		}
		
		return entity;
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<E> findByProperty(String propertyName, Object propertyValue, int... rowStartIdxAndCount) {
		
		if (StringUtils.isBlank(StringUtils.trim(propertyName)) || propertyValue == null || rowStartIdxAndCount.length < 0)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_FIND + propertyName + ", propertyValue: " + propertyValue + ", rowStartIdxAndCount: " + rowStartIdxAndCount);
		
		if (StringUtils.isBlank(StringUtils.trim(this.statement)))
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_FIND + this.statement);
		
		logger.log(Level.INFO, FINDING_ALL_E_BY_PROPERTY_NAME + propertyName + ", propertyValue: " + propertyValue);
		
		List<E> entities = null;
		
		try {
			final Map<String, Object> map = new HashMap<String, Object>();
			map.put(propertyName, propertyValue);
			
			final List<?> resultList = this.getSqlMapClient().queryForList(this.statement, map);
			
			if (resultList != null) {
				entities = new LinkedList<E>();
				for (Object object : resultList) {
					if (object instanceof Object) {
						final E model = this.clazzE.cast(object);
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
		 }
		
		return entities;
	}

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<E> findAll(int... rowStartIdxAndCount) {
		
		if (rowStartIdxAndCount.length < 0)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_FIND + ", rowStartIdxAndCount: " + rowStartIdxAndCount);

		if (StringUtils.isBlank(StringUtils.trim(this.statement)))
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_FIND + this.statement);
		
		logger.log(Level.INFO, FINDING_ALL_E_BY_ROWSSTARTIDXANDCOUNT + rowStartIdxAndCount);
		
		List<E> entities = null;
		
		try {	
			final List<?> resultList = this.getSqlMapClient().queryForList(this.statement);
			
			if (resultList != null) {
				entities = new LinkedList<E>();
				for (Object object : resultList) {
					if (object instanceof Object) {
						final E model = this.clazzE.cast(object);
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
		 }
		
		return entities;
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<E> getAll() {
		
		if (StringUtils.isBlank(StringUtils.trim(this.statement)))
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_GET + this.statement);
		
		logger.log(Level.INFO, GETTING_ALL_E);

		List<E> entities = null;
		
		try {	
			final List<?> resultList = this.getSqlMapClient().queryForList(this.statement);
			
			if (resultList != null) {
				entities = new LinkedList<E>();
				for (Object object : resultList) {
					if (object instanceof Object) {
						final E model = this.clazzE.cast(object);
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