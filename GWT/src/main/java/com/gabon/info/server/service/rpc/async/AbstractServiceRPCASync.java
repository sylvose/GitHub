package com.gabon.info.server.service.rpc.async;

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

import com.gabon.info.server.service.rpc.sync.ServiceFacadeRPCSync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;


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

public abstract class AbstractServiceRPCASync<E, PK extends Serializable> implements ServiceFacadeRPCASync<E, PK> {

	private static final long serialVersionUID = 5533421659786415500L;

	
	protected static final Logger logger = Logger.getLogger(AbstractServiceRPCASync.class.getName());
	
	
	protected final ServiceFacadeRPCASync<E, PK> serviceFacadeRPCASync = GWT.create(ServiceFacadeRPCSync.class);
	
	protected AsyncCallback<E> asyncCallback;
    
	
	protected PK id;
	protected Class<E> clazzE;
	protected Class<PK> clazzPK;
	
	
	
	@SuppressWarnings("unchecked")
	public AbstractServiceRPCASync() { 
		
		final Type type = getClass().getGenericSuperclass();
		if (type instanceof Type) {
			final Class<ParameterizedType> clazzTParameterizedType = ParameterizedType.class;
			final ParameterizedType parameterizedType = clazzTParameterizedType.cast(type);
		    final Object object = parameterizedType.getActualTypeArguments()[0];
		    this.clazzE = Class.class.cast(object); 
		}
	}
	
	public AbstractServiceRPCASync(Class<E> clazzE) { 
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

	
	
	public AsyncCallback<E> getAsyncCallback() {
		return asyncCallback;
	}

	public void setAsyncCallback(AsyncCallback<E> asyncCallback) {
		this.asyncCallback = asyncCallback;
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
	
	
	
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void save(final E entity, final AsyncCallback<E> asyncCallback) {
		serviceFacadeRPCASync.save(entity, asyncCallback);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(final E entity, final AsyncCallback<E> asyncCallback) {
		serviceFacadeRPCASync.delete(getId(entity), asyncCallback);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(final PK id, final AsyncCallback<E> asyncCallback) {
		serviceFacadeRPCASync.delete(id, asyncCallback);
	}

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public E update(final E entity, final AsyncCallback<E> asyncCallback) {
		return this.clazzE.cast(serviceFacadeRPCASync.update(entity, asyncCallback));
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public E findById(final PK id, final AsyncCallback<E> asyncCallback) {
		return this.clazzE.cast(serviceFacadeRPCASync.findById(id, asyncCallback));
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public List<E> findByProperty(String propertyName, final Object propertyValue, final AsyncCallback<E> asyncCallback, final int... rowStartIdxAndCount) {
		return serviceFacadeRPCASync.findByProperty(propertyName, propertyValue, asyncCallback, rowStartIdxAndCount);
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<E> findAll(final AsyncCallback<E> asyncCallback, final int... rowStartIdxAndCount) {
		return serviceFacadeRPCASync.findAll(asyncCallback, rowStartIdxAndCount);
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public List<E> getAll(final AsyncCallback<E> asyncCallback) {
		return serviceFacadeRPCASync.getAll(asyncCallback);
	}
}