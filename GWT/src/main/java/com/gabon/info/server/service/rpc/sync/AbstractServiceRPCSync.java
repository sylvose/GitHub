package com.gabon.info.server.service.rpc.sync;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.server.dao.DAOSupportFacade;
import com.google.gwt.rpc.server.RpcServlet;


/*
 * This class is a abstract service class.
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

public abstract class AbstractServiceRPCSync<E, PK extends Serializable> extends RpcServlet implements ServiceFacadeRPCSync<E, PK> {

	private static final long serialVersionUID = 6570628464526795091L;

	
	protected static final Logger logger = Logger.getLogger(AbstractServiceRPCSync.class.getName());
	
	protected static final Object lock = new Object();
	
	
	// DAO
	protected DAOSupportFacade<E, PK> daoSupportFacade;
	
    
	protected PK id;
	protected Class<E> clazzE;
	protected Class<PK> clazzPK;
	
	
	@SuppressWarnings("unchecked")
	public AbstractServiceRPCSync() { 
		
		final Type type = getClass().getGenericSuperclass();
		if (type instanceof Type) {
			final Class<ParameterizedType> clazzTParameterizedType = ParameterizedType.class;
			final ParameterizedType parameterizedType = clazzTParameterizedType.cast(type);
		    final Object object = parameterizedType.getActualTypeArguments()[0];
		    this.clazzE = Class.class.cast(object); 
		}
	}
	
	public AbstractServiceRPCSync(Class<E> clazzE) { 
		setClazzE(clazzE);
	}
	
	public PK getProperty(E entity, String property) {
        
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

	
	public PK getId(E entity) {
		this.id = getProperty(entity, Id);
		return this.id;
	}

	public PK getId() {
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
	
	/**
	 * @return the clazzPK
	 */
	public Class<PK> getClazzPK() {
		return clazzPK;
	}

	/**
	 * @param clazzPK the clazzPK to set
	 */
	public void setClazzPK(Class<PK> clazzPK) {
		this.clazzPK = clazzPK;
	}
	
	
	
	
	/**
	 * @return the daoSupportFacade
	 */
	public DAOSupportFacade<E, PK> getDAOSupportFacade() {
		return daoSupportFacade;
	}

	/**
	 * @param daoSupportFacade the daoSupportFacade to set
	 */
	public void setDAOSupportFacade(final DAOSupportFacade<E, PK> daoSupportFacade) {
		this.daoSupportFacade = daoSupportFacade;
	}

	
	
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void save(final E entity) {
		
		if (this.daoSupportFacade instanceof DAOSupportFacade) {
			this.daoSupportFacade.save(entity);
			setId(getId(entity));
		}
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(final E entity) {
		 delete(getId(entity));
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(final PK id) {
	
		E entity = null;
		
		if (this.daoSupportFacade instanceof DAOSupportFacade)
			entity = this.daoSupportFacade.findById(id);
		
		if (entity instanceof Object)
			this.daoSupportFacade.delete(entity);
	}

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public E update(final E entity) {

		E model = null;
		
		if (this.daoSupportFacade instanceof DAOSupportFacade) {
			model = this.daoSupportFacade.update(entity);
			setId(getId(entity));
		}
		
		return model;
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public E findById(final PK id) {
		
		final E model = this.daoSupportFacade.findById(id);
	
		return model;
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public List<E> findByProperty(String propertyName, final Object propertyValue, final int... rowStartIdxAndCount) {
		
		List<E> entitiesList = null;
		if (this.daoSupportFacade instanceof DAOSupportFacade)
			entitiesList = this.daoSupportFacade.findByProperty(propertyName, propertyValue, rowStartIdxAndCount);
			
		return entitiesList;
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<E> findAll(final int... rowStartIdxAndCount) {
		
		List<E> entitiesList = null;
		if (this.daoSupportFacade instanceof DAOSupportFacade)
			entitiesList = this.daoSupportFacade.findAll(rowStartIdxAndCount);
			
		return entitiesList;
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public List<E> getAll() {
		
		List<E> entitiesList = null;
		if (this.daoSupportFacade instanceof DAOSupportFacade)
			entitiesList = this.daoSupportFacade.getAll();
		
		return entitiesList;
	}
}