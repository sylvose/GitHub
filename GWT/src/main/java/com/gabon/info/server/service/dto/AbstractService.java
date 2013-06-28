package com.gabon.info.server.service.dto;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;

import net.sf.dozer.util.mapping.DozerBeanMapper;
import net.sf.dozer.util.mapping.MapperIF;

import org.dynadto.Builder;
import org.dynadto.BuilderFactory;
import org.dynadto.ConfigurationLoader;
import org.dynadto.exception.ConfigurationException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.server.dao.DAOSupportFacade;


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

public abstract class AbstractService<T, E, PK extends Serializable> implements ServiceFacade<T, E, PK> {

	private static final long serialVersionUID = 2359098849638961111L;

	protected static final Logger logger = Logger.getLogger(AbstractService.class.getName());
	
	protected static final Object lock = new Object();
	
	
	// DAO
	protected DAOSupportFacade<E, PK> daoSupportFacade;

	// DynaDTO BuilderFactory
	@Resource(name = BEAN_BUILDER_FACTORY)
	protected BuilderFactory builderFactory;

	// DynaDTO Builders
	protected Builder builder;

	// mapperIF
	protected MapperIF mapperIF;
	
	protected ClassLoader classLoader;
    
	protected URL url;
	
    
	protected PK id;
	protected Class<E> clazzE;
	protected Class<T> clazzT;
	protected Class<PK> clazzPK;
	
	
	@SuppressWarnings("unchecked")
	public AbstractService() { 
		
		final Type type = getClass().getGenericSuperclass();
		if (type instanceof Type) {
			final Class<ParameterizedType> clazzTParameterizedType = ParameterizedType.class;
			final ParameterizedType parameterizedType = clazzTParameterizedType.cast(type);
		    final Object object = parameterizedType.getActualTypeArguments()[0];
		    this.clazzE = Class.class.cast(object); 
		}
		
		if (this.classLoader == null)
			this.classLoader = Thread.currentThread().getContextClassLoader();
		
		if (this.url == null)
			this.url = classLoader.getResource(DTO_CONTEXT_ROOT);
		
		if (this.mapperIF == null)
			this.mapperIF = new DozerBeanMapper();
		
		if (this.builderFactory == null)
			this.builderFactory = BuilderFactory.getInstance();

		if (this.url != null && this.builderFactory != null) { 
			try {
				ConfigurationLoader.loadMapping(this.url, this.builderFactory);
			} catch (ConfigurationException e) {
				logger.log(Level.SEVERE, e.getClass().getName() + MESSAGE + e.getMessage(), e);
			}
		}
	}
	
	public AbstractService(Class<E> clazzE) { 
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
	
	public Class<T> getClazzT() {
		return this.clazzT;
	}

	public void setClazzT(Class<T> clazzT) {
		this.clazzT = clazzT;
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

	/**
	 * @return the builderFactory
	 */
	public BuilderFactory getBuilderFactory() {
		return builderFactory;
	}

	/**
	 * @param builderFactory the builderFactory to set
	 */
	public void setBuilderFactory(final BuilderFactory builderFactory) {
		this.builderFactory = builderFactory;
	}

	/**
	 * @return the builder
	 */
	public Builder getBuilder() {
		return builder;
	}

	/**
	 * @param builder the builder to set
	 */
	public void setBuilder(final Builder builder) {
		this.builder = builder;
	}
	
	/**
	 * @return the classLoader
	 */
	public ClassLoader getClassLoader() {
		return classLoader;
	}

	/**
	 * @param classLoader the classLoader to set
	 */
	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	/**
	 * @return the url
	 */
	public URL getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(URL url) {
		this.url = url;
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
	public T update(final E entity) {

		T dto = null;
		
		// synchronization between Model to DTO
		if (entity instanceof Object)
			mapperIF.map(entity, dto);
		
		if (dto == null)
			dto = this.clazzT.cast(this.builder.build(entity));
		
		if (this.daoSupportFacade instanceof DAOSupportFacade) {
			this.daoSupportFacade.update(entity);
			setId(getId(entity));
		}
		
		return dto;
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public T findById(final PK id) {
		
		T dto = null;
		
		final E model = this.daoSupportFacade.findById(id);
			
		if (model instanceof Object)
			dto = this.clazzT.cast(this.builder.build(model));
	
		return dto;
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public List<T> findByProperty(String propertyName, final Object propertyValue, final int... rowStartIdxAndCount) {
		
		List<E> entitiesList = null;
		if (this.daoSupportFacade instanceof DAOSupportFacade)
			entitiesList = this.daoSupportFacade.findByProperty(propertyName, propertyValue, rowStartIdxAndCount);
			
		final List<T> dtosList = new LinkedList<T>();
		
		if (entitiesList instanceof List)
			for (Object object : builder.buildList(entitiesList)) {
				if (object instanceof Object) {
					final T dto = this.clazzT.cast(this.builder.build(object));
					dtosList.add(dto);
				}
	        }
		
		return dtosList;
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<T> findAll(final int... rowStartIdxAndCount) {
		
		List<E> entitiesList = null;
		if (this.daoSupportFacade instanceof DAOSupportFacade)
			entitiesList = this.daoSupportFacade.findAll(rowStartIdxAndCount);
			
		final List<T> dtosList = new LinkedList<T>();
		
		if (entitiesList instanceof List)
			for (Object object : builder.buildList(entitiesList)) {
				if (object instanceof Object) {
					final T dto = this.clazzT.cast(this.builder.build(object));
					dtosList.add(dto);
				}
	    }
		
		return dtosList;
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public List<T> getAll() {
		
		List<E> entitiesList = null;
		if (this.daoSupportFacade instanceof DAOSupportFacade)
			entitiesList = this.daoSupportFacade.getAll();
			
		final List<T> dtosList = new LinkedList<T>();
		
		if (entitiesList instanceof List)
			for (Object object : builder.buildList(entitiesList)) {
				if (object instanceof Object) {
					final T dto = this.clazzT.cast(this.builder.build(object));
					dtosList.add(dto);
				}
	    }
		
		return dtosList;
	}
}