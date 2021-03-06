package com.gabon.info.server.dao.spring.jdbc;

import static com.gabon.info.server.util.Constants.BEAN_ABSTRACT_JDBC_DAO_SUPPORT;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.server.dao.DAOSupportFacade;

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
 * This class has the DAO class extend JdbcDaoSupport and uses the JdbcTemplate, but Spring recommends the native JDBC style of coding.
 * 
 */

@Transactional
@Repository(BEAN_ABSTRACT_JDBC_DAO_SUPPORT)
public abstract class AbstractJdbcDaoSupport<E, PK extends Serializable> extends JdbcDaoSupport implements DAOSupportFacade<E, PK> {	
	
	private static final long serialVersionUID = -8371363577624602529L;

	protected static final Logger logger = Logger.getLogger(AbstractJdbcDaoSupport.class.getName());
	
	protected static final Object lock = new Object();
	
	protected static final ApplicationContext applicationContext = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_ROOT);
	
	
	protected PK id;
	protected Class<E> clazzE;
	protected Class<PK> clazzPK;
	protected String queryString;
	protected Object[] values;
	
	
	
	@SuppressWarnings("unchecked")
	public AbstractJdbcDaoSupport() { 
		super();
		
		final Type type = getClass().getGenericSuperclass();
		if (type instanceof Type) {
			final Class<ParameterizedType> clazzEParameterizedType = ParameterizedType.class;
			final ParameterizedType parameterizedType = clazzEParameterizedType.cast(type);
		    final Object object = parameterizedType.getActualTypeArguments()[0];
		    this.clazzE = Class.class.cast(object); 
		}
		
		if (applicationContext instanceof ApplicationContext) {
			final JdbcTemplate jdbcTemplate = (JdbcTemplate) applicationContext.getBean(BEAN_JBDC_TEMPLATE);
			setJdbcTemplate(jdbcTemplate);
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
	
	
	
	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public Object[] getValues() {
		return values;
	}

	public void setValues(Object[] values) {
		this.values = values;
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
		Connection connection = this.getConnection();
		
		try {
			if (connection == null || connection.isClosed()) {
				final DataSource dataSource = this.getJdbcTemplate().getDataSource();
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
				final PK modelId = this.clazzPK.cast(resultSet.getLong(ID));
			
				if (entityId instanceof Serializable && modelId instanceof Serializable) {
					exists = entityId.equals(modelId);
					
					if (exists)
					break;
				}
			}
		} catch (SQLException sql) {
			logger.log(Level.SEVERE, SAVE_FAILED + sql.getClass().getName() + MESSAGE + sql.getMessage(), sql);	
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, e.getClass().getName() + MESSAGE + e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getClass().getName() + MESSAGE + e.getMessage(), e);
		} finally {
			 releaseConnection(connection);
		}
		
		return exists;
	}
    
    public boolean isEntityExists(PK id) {
		boolean exists = false;
		
		try {
			final E entity = this.clazzE.cast(findById(id));
			
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
		
		if (StringUtils.isBlank(StringUtils.trim(this.queryString)))
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_SAVE + this.queryString);
		
		if (ArrayUtils.isEmpty(this.values))
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_SAVE + this.values);
		
		if (isEntityExists(entity))
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_SAVE + entity);
		
		Connection connection = this.getConnection();
		
		logger.log(Level.INFO, SAVING_E +entity+ INSTANCE);
		
		try {
			if (connection == null || connection.isClosed()) {
				final DataSource dataSource = this.getJdbcTemplate().getDataSource();
				connection = dataSource.getConnection();
			}
			
			if (connection != null)
				connection.setAutoCommit(false);
			
			final int affectedRows = this.getJdbcTemplate().update(this.queryString, this.values);
			if (affectedRows == 0)
				throw new IllegalArgumentException(SAVING_E +entity+ INSTANCE_FAILED_NO_ROWS_AFFECTED);
		
			connection.commit();
		
			logger.log(Level.INFO, SAVE_SUCCESSFUL);
				
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, SAVE_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
			try {
				if (!connection.isClosed()) 
				connection.rollback();
			} catch (SQLException sql) {
				logger.log(Level.SEVERE, SAVE_FAILED + sql.getClass().getName() + MESSAGE + sql.getMessage(), sql);
			}
			throw e;
		} catch (SQLException sql) {
			logger.log(Level.SEVERE, SAVE_FAILED + sql.getClass().getName() + MESSAGE + sql.getMessage(), sql);
	    } catch (Exception e) {
			logger.log(Level.SEVERE, SAVE_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
		} finally {
			releaseConnection(connection);
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
		
		final E entity = findById(id);
		if (entity == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_DELETE + id);
		
		Connection connection = this.getConnection();
		
		logger.log(Level.INFO, DELETING_E + id);
		
		try {
			if (connection == null || connection.isClosed()) {
				final DataSource dataSource = this.getJdbcTemplate().getDataSource();
				connection = dataSource.getConnection();
			}
			
			if (connection != null)
				connection.setAutoCommit(false);
			
			this.queryString = QUERY_STRING_DELETE_FROM_UQAM_TABLE + this.clazzE.getSimpleName().toUpperCase() + CLAUSE_STRING_WHERE_ID + id;
			final int affectedRows = this.getJdbcTemplate().update(this.queryString);
			if (affectedRows == 0)
				throw new IllegalArgumentException(DELETING_E +id+ INSTANCE_FAILED_NO_ROWS_AFFECTED);
			
			connection.commit();
			
			logger.log(Level.INFO, DELETE_SUCCESSFUL);

		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, DELETE_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
			try {
				if (!connection.isClosed()) 
				connection.rollback();
			} catch (SQLException sql) {
				logger.log(Level.SEVERE, DELETE_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), sql);
			}
			throw e;
		} catch (SQLException sql) {
			logger.log(Level.SEVERE, SAVE_FAILED + sql.getClass().getName() + MESSAGE + sql.getMessage(), sql);
	    } catch (Exception e) {
			logger.log(Level.SEVERE, DELETE_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
		} finally {
			releaseConnection(connection);
		}
	}

    @Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public E update(E entity) {
		if (entity == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_UPDATE + entity);
		
		if (StringUtils.isBlank(StringUtils.trim(this.queryString)))
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_UPDATE + this.queryString);
		
		if (ArrayUtils.isEmpty(this.values))
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_UPDATE + this.values);
		
		if (!isEntityExists(entity))
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_UPDATE + entity);
		
		Connection connection = this.getConnection();
		
		logger.log(Level.INFO, UPDATING_E +entity+ INSTANCE);
		
		E model = null;
				
		try {
			if (connection == null || connection.isClosed()) {
				final DataSource dataSource = this.getJdbcTemplate().getDataSource();
				connection = dataSource.getConnection();
			}
			
			if (connection != null)
				connection.setAutoCommit(false);
			
			final int affectedRows = this.getJdbcTemplate().update(this.queryString, this.values);
			if (affectedRows == 0)
				throw new IllegalArgumentException(UPDATING_E +entity+ INSTANCE_FAILED_NO_ROWS_AFFECTED);
			
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
				logger.log(Level.SEVERE, UPDATE_FAILED + sql.getClass().getName() + MESSAGE + sql.getMessage(), sql);
			}
			throw e;
		} catch (SQLException sql) {
			logger.log(Level.SEVERE, SAVE_FAILED + sql.getClass().getName() + MESSAGE + sql.getMessage(), sql);
	    } catch (Exception e) {
			logger.log(Level.SEVERE, UPDATE_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
		} finally {
			releaseConnection(connection);
		}
		
		return model;
	}

    @Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public E findById(PK id) {
		if (id == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_FIND + id);
		
		E entity = null;
		
		logger.log(Level.INFO, FINDING_E_BY_ID + id);
		
		try {
			this.queryString = QUERY_STRING_SELECT_STAR_FROM_UQAM_TABLE + this.clazzE.getSimpleName().toUpperCase() + CLAUSE_STRING_WHERE_ID + id;
			entity = this.getJdbcTemplate().queryForObject(this.queryString, this.clazzE);
			
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
		
		logger.log(Level.INFO, FINDING_ALL_E_BY_PROPERTY_NAME + propertyName + ", propertyValue: " + propertyValue);
		
		List<E> entities = new LinkedList<E>();
		
		try {
			this.queryString = QUERY_STRING_SELECT_STAR_FROM_UQAM_TABLE + this.clazzE.getSimpleName().toUpperCase() + CLAUSE_STRING_WHERE + propertyName + " = " + "'" + propertyValue + "'";
			entities = this.getJdbcTemplate().queryForList(this.queryString, this.clazzE);
			
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

		logger.log(Level.INFO, FINDING_ALL_E_BY_ROWSSTARTIDXANDCOUNT + rowStartIdxAndCount);
		
		List<E> entities = new LinkedList<E>();
		
		try {
			this.queryString = QUERY_STRING_SELECT_STAR_FROM_UQAM_TABLE + this.clazzE.getSimpleName().toUpperCase();
			entities = this.getJdbcTemplate().queryForList(this.queryString, this.clazzE);
			
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
		logger.log(Level.INFO, GETTING_ALL_E);

		List<E> entities = new LinkedList<E>();
		
		try {
			this.queryString = QUERY_STRING_SELECT_STAR_FROM_UQAM_TABLE + this.clazzE.getSimpleName().toUpperCase();
			entities = this.getJdbcTemplate().queryForList(this.queryString, this.clazzE);
			
			if (entities != null && !entities.isEmpty())
				logger.log(Level.INFO, GET_SUCCESSFUL);
			else 
				logger.log(Level.INFO, GET_FAILED);
			
		 } catch (RuntimeException e) {
			 logger.log(Level.SEVERE, GET_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
			throw e;
		 }  catch (Exception e) {
			 logger.log(Level.SEVERE, GET_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
		 }
	
		return entities;
	}
}