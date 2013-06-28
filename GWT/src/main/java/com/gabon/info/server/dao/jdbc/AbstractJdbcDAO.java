package com.gabon.info.server.dao.jdbc;

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

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.gabon.info.server.dao.DAOFacade;


/*
 * This class is a abstract JDBC class.
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
 * AbstractJdbcDAO entity provides the methods for the base persistence definition of the Models
 */

public abstract class AbstractJdbcDAO<E> implements DAOFacade<E> {

	private static final long serialVersionUID = 2174055874945098874L;
    
	protected static final Logger logger = Logger.getLogger(AbstractJdbcDAO.class.getName());
	
	protected static final Object lock = new Object();
	
	
	protected Long id;
	protected Class<E> clazz;
	protected String queryString;
	protected Object[] values;
	
	
	@SuppressWarnings("unchecked")
	public AbstractJdbcDAO() { 
		
		final Type type = getClass().getGenericSuperclass();
		if (type instanceof Type) {
			final Class<ParameterizedType> clazzEParameterizedType = ParameterizedType.class;
			final ParameterizedType parameterizedType = clazzEParameterizedType.cast(type);
		    final Object object = parameterizedType.getActualTypeArguments()[0];
		    this.clazz = Class.class.cast(object); 
		}
	}
	
	public AbstractJdbcDAO(Class<E> clazz) { 
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

	private void setValues(final PreparedStatement preparedStatement, final Object... values) throws SQLException {
        for (int i = 0; i < values.length; i++)
            preparedStatement.setObject(i + 1, values[i]);
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
		final JdbcHelper jdbcHelper = new JdbcHelper();
		Connection connection = jdbcHelper.getConnection();
		
		try {
			if (connection == null || connection.isClosed()) {
				final DataSource dataSource = jdbcHelper.getDataSource();
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
			jdbcHelper.close();
		}
		
		return exists;
	}
	
	private E mapperResultSetToGeneric(final ResultSet resultSet) {
        
		E entity = null;
				
		if (resultSet != null)  {
			
			try {
				final RowProcessor rowProcessor = new BasicRowProcessor(); 
				final Object object = rowProcessor.toBean(resultSet, this.clazz);
				
				entity = this.clazz.cast(object);
				
			} catch (SQLException e) {
				logger.log(Level.SEVERE, e.getClass().getName() + MESSAGE + e.getMessage(), e);
			}
		}
       
        return entity;
    }
	
	

	
	public void save(E entity) {
		if (entity == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_SAVE + entity);
		
		if (StringUtils.isBlank(StringUtils.trim(this.queryString)))
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_SAVE + this.queryString);
		
		if (ArrayUtils.isEmpty(this.values))
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_SAVE + this.values);
		
		if (isEntityExists(entity))
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_SAVE + entity);
		
		final JdbcHelper jdbcHelper = new JdbcHelper();
		Connection connection = jdbcHelper.getConnection();
		
		logger.log(Level.INFO, SAVING_E +entity+ INSTANCE);
		
		try {
			if (connection == null || connection.isClosed()) {
				final DataSource dataSource = jdbcHelper.getDataSource();
				connection = dataSource.getConnection();
			}
			
			if (connection != null)
				connection.setAutoCommit(false);
			
			final PreparedStatement preparedStatement = connection.prepareStatement(this.queryString, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			setValues(preparedStatement, this.values);
			final int affectedRows = preparedStatement.executeUpdate();
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
				logger.log(Level.SEVERE, SAVE_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), sql);
			}
			throw e;
		} catch (SQLException sql) {
			logger.log(Level.SEVERE, SAVE_FAILED + sql.getClass().getName() + MESSAGE + sql.getMessage(), sql);
	    } catch (Exception e) {
			logger.log(Level.SEVERE, SAVE_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
		} finally {
			jdbcHelper.close();
		}
	}
	
	public void delete(E entity) {
		delete(getId(entity));
	}
	
	public void delete(Long id) {
		if (id == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_DELETE + id);
		
		final E entity = findById(id);
		if (entity == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_DELETE + id);
		
		final JdbcHelper jdbcHelper = new JdbcHelper();
		Connection connection = jdbcHelper.getConnection();
		
		logger.log(Level.INFO, DELETING_E + id);
		
		try {
			if (connection == null || connection.isClosed()) {
				final DataSource dataSource = jdbcHelper.getDataSource();
				connection = dataSource.getConnection();
			}
			
			if (connection != null)
				connection.setAutoCommit(false);
			
			this.queryString = QUERY_STRING_DELETE_FROM_UQAM_TABLE + this.clazz.getSimpleName().toUpperCase() + CLAUSE_STRING_WHERE_ID + id;
			final PreparedStatement preparedStatement = connection.prepareStatement(this.queryString, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			final int affectedRows = preparedStatement.executeUpdate();
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
			jdbcHelper.close();
		}
	}

	public E update(E entity) {
		if (entity == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_UPDATE + entity);
		
		if (StringUtils.isBlank(StringUtils.trim(this.queryString)))
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_UPDATE + this.queryString);
		
		if (ArrayUtils.isEmpty(this.values))
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_UPDATE + this.values);
		
		if (!isEntityExists(entity))
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_UPDATE + entity);
		
		final JdbcHelper jdbcHelper = new JdbcHelper();
		Connection connection = jdbcHelper.getConnection();
		
		logger.log(Level.INFO, UPDATING_E +entity+ INSTANCE);
		
		E model = null;
				
		try {
			if (connection == null || connection.isClosed()) {
				final DataSource dataSource = jdbcHelper.getDataSource();
				connection = dataSource.getConnection();
			}
			
			if (connection != null)
				connection.setAutoCommit(false);
			
			final PreparedStatement preparedStatement = connection.prepareStatement(this.queryString, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			setValues(preparedStatement, this.values);
			final int affectedRows = preparedStatement.executeUpdate();
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
				logger.log(Level.SEVERE, UPDATE_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), sql);
			}
			throw e;
		} catch (SQLException sql) {
			logger.log(Level.SEVERE, SAVE_FAILED + sql.getClass().getName() + MESSAGE + sql.getMessage(), sql);
	    } catch (Exception e) {
			logger.log(Level.SEVERE, UPDATE_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
		} finally {
			jdbcHelper.close();
		}
		
		return model;
	}

	public E findById(Long id) {
		if (id == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_FIND + id);
		
		final JdbcHelper jdbcHelper = new JdbcHelper();
		Connection connection = jdbcHelper.getConnection();
		
		E entity = null;
		
		logger.log(Level.INFO, FINDING_E_BY_ID + id);
		
		try {
			if (connection == null || connection.isClosed()) {
				final DataSource dataSource = jdbcHelper.getDataSource();
				connection = dataSource.getConnection();
			}
			
			if (connection != null)
				connection.setAutoCommit(false);
			
			this.queryString = QUERY_STRING_SELECT_STAR_FROM_UQAM_TABLE + this.clazz.getSimpleName().toUpperCase() + CLAUSE_STRING_WHERE_ID + id;
			final PreparedStatement preparedStatement = connection.prepareStatement(this.queryString, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			final ResultSet resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next())
				entity = mapperResultSetToGeneric(resultSet);
			
			if (entity != null) {
				
				logger.log(Level.INFO, FIND_SUCCESSFUL);
				
			} else  {
				logger.log(Level.INFO, FIND_FAILED);
				throw new IllegalArgumentException(ILLEGAL_ARGUMENT_FIND + id);
			}
			
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, FIND_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
			throw e;
		} catch (SQLException sql) {
			logger.log(Level.SEVERE, SAVE_FAILED + sql.getClass().getName() + MESSAGE + sql.getMessage(), sql);
	    } catch (Exception e) {
			logger.log(Level.SEVERE, FIND_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
		} finally {
			jdbcHelper.close();
		}
		
		return entity;
	}
	
	public List<E> findByProperty(String propertyName, Object propertyValue, int... rowStartIdxAndCount) {
		
		if (StringUtils.isBlank(StringUtils.trim(propertyName)) || propertyValue == null || rowStartIdxAndCount.length < 0)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_FIND + propertyName + ", propertyValue: " + propertyValue + ", rowStartIdxAndCount: " + rowStartIdxAndCount);
		
		final JdbcHelper jdbcHelper = new JdbcHelper();
		Connection connection = jdbcHelper.getConnection();
		
		logger.log(Level.INFO, FINDING_ALL_E_BY_PROPERTY_NAME + propertyName + ", propertyValue: " + propertyValue);
		
		List<E> entities = null;
		
		try {
			if (connection == null || connection.isClosed()) {
				final DataSource dataSource = jdbcHelper.getDataSource();
				connection = dataSource.getConnection();
			}
			
			if (connection != null)
				connection.setAutoCommit(false);
			
			this.queryString = QUERY_STRING_SELECT_STAR_FROM_UQAM_TABLE + this.clazz.getSimpleName().toUpperCase() + CLAUSE_STRING_WHERE + propertyName + " = " + "'" + propertyValue + "'";
			final PreparedStatement preparedStatement = connection.prepareStatement(this.queryString, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			final ResultSet resultSet = preparedStatement.executeQuery();
			
			if (resultSet != null) {
				entities = new LinkedList<E>();
				while (resultSet.next())
					entities.add(mapperResultSetToGeneric(resultSet));
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
		 } catch (SQLException sql) {
				logger.log(Level.SEVERE, SAVE_FAILED + sql.getClass().getName() + MESSAGE + sql.getMessage(), sql);
		 } catch (Exception e) {
			 logger.log(Level.SEVERE, FIND_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
		 } finally {
			 jdbcHelper.close();
		 }
		
		return entities;
	}

	public List<E> findAll(int... rowStartIdxAndCount) {
		
		if (rowStartIdxAndCount.length < 0)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_FIND + ", rowStartIdxAndCount: " + rowStartIdxAndCount);

		final JdbcHelper jdbcHelper = new JdbcHelper();
		Connection connection = jdbcHelper.getConnection();
		
		logger.log(Level.INFO, FINDING_ALL_E_BY_ROWSSTARTIDXANDCOUNT + rowStartIdxAndCount);
		
		List<E> entities = null;
		
		try {
			if (connection == null || connection.isClosed()) {
				final DataSource dataSource = jdbcHelper.getDataSource();
				connection = dataSource.getConnection();
			}
			
			if (connection != null)
				connection.setAutoCommit(false);
			
			this.queryString = QUERY_STRING_SELECT_STAR_FROM_UQAM_TABLE + this.clazz.getSimpleName().toUpperCase();
			final PreparedStatement preparedStatement = connection.prepareStatement(this.queryString, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			final ResultSet resultSet = preparedStatement.executeQuery();
			
			if (resultSet != null) {
				entities = new LinkedList<E>();
				while (resultSet.next())
					entities.add(mapperResultSetToGeneric(resultSet));
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
		 } catch (SQLException sql) {
				logger.log(Level.SEVERE, SAVE_FAILED + sql.getClass().getName() + MESSAGE + sql.getMessage(), sql);
		 } catch (Exception e) {
			 logger.log(Level.SEVERE, FIND_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
		 } finally {
			 jdbcHelper.close();
		 }
		
		return entities;
	}
	
	public List<E> getAll() {
		logger.log(Level.INFO, GETTING_ALL_E);

		final JdbcHelper jdbcHelper = new JdbcHelper();
		Connection connection = jdbcHelper.getConnection();
		
		List<E> entities = null;
		
		try {
			if (connection == null || connection.isClosed()) {
				final DataSource dataSource = jdbcHelper.getDataSource();
				connection = dataSource.getConnection();
			}
			
			if (connection != null)
				connection.setAutoCommit(false);
			
			this.queryString = QUERY_STRING_SELECT_STAR_FROM_UQAM_TABLE + this.clazz.getSimpleName().toUpperCase();
			final PreparedStatement preparedStatement = connection.prepareStatement(this.queryString, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			final ResultSet resultSet = preparedStatement.executeQuery();
			
			if (resultSet != null) {
				entities = new LinkedList<E>();
				while (resultSet.next())
					entities.add(mapperResultSetToGeneric(resultSet));
			 }
			
			if (entities != null && !entities.isEmpty())
				logger.log(Level.INFO, GET_SUCCESSFUL);
			else 
				logger.log(Level.INFO, GET_FAILED);
			
		 } catch (RuntimeException e) {
			 logger.log(Level.SEVERE, GET_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
			throw e;
		 }  catch (SQLException sql) {
				logger.log(Level.SEVERE, SAVE_FAILED + sql.getClass().getName() + MESSAGE + sql.getMessage(), sql);
		 } catch (Exception e) {
			 logger.log(Level.SEVERE, GET_FAILED + e.getClass().getName() + MESSAGE + e.getMessage(), e);
		 } finally {
			 jdbcHelper.close();
		 }
	
		return entities;
	}
}