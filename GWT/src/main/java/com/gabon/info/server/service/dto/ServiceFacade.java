package com.gabon.info.server.service.dto;

import java.io.Serializable;
import java.util.List;

import org.dynadto.Builder;
import org.dynadto.BuilderFactory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.server.dao.DAOSupportFacade;
import com.gabon.info.server.util.Constants;


/*
 * This class is a interface service class.
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

public interface ServiceFacade<T, E, PK extends Serializable> extends Constants, Serializable {

	PK getProperty(E entity, String property);
	
	PK getId(E entity);

	PK getId();
	
	void setId(Long id);
	
	Class<E> getClazzE();

	void setClazzE(Class<E> clazzE);
	
	Class<T> getClazzT();

	void setClazzT(Class<T> clazzT);
	
	Class<PK> getClazzPK();

	void setClazzPK(Class<PK> clazzPK);
	
	
	
	/**
	 * @return the daoSupportFacade
	 */
	DAOSupportFacade<E, PK> getDAOSupportFacade();

	/**
	 * @param daoSupportFacade the daoSupportFacade to set
	 */
	void setDAOSupportFacade(final DAOSupportFacade<E, PK> daoSupportFacade);

	/**
	 * @return the builderFactory
	 */
	BuilderFactory getBuilderFactory();

	/**
	 * @param builderFactory the builderFactory to set
	 */
	void setBuilderFactory(final BuilderFactory builderFactory);

	/**
	 * @return the builder
	 */
	Builder getBuilder();

	/**
	 * @param builder the builder to set
	 */
	void setBuilder(final Builder builder);

	
	
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	void save(final E entity);
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	void delete(final E entity);
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	void delete(final PK id);

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	T update(final E entity);
	
	@Modifying
	@Transactional(readOnly = true)
	T findById(final PK id);
	
	@Modifying
	@Transactional(readOnly = true)
	List<T> findByProperty(String propertyName, final Object propertyValue, final int... rowStartIdxAndCount);

	@Modifying
	@Transactional(readOnly = true)
	List<T> findAll(final int... rowStartIdxAndCount);
	
	@Modifying
	@Transactional(readOnly = true)
	List<T> getAll();
}