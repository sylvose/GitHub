package com.gabon.info.server.service.rpc.async;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.server.util.Constants;
import com.google.gwt.user.client.rpc.AsyncCallback;


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

public interface ServiceFacadeRPCASync<E, PK extends Serializable> extends Constants, Serializable {

	
	PK getId(E entity);

	PK getId();
	
	void setId(Long id);
	
	Class<E> getClazzE();

	void setClazzE(Class<E> clazzE);
	
	
	
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	void save(final E entity, final AsyncCallback<E> asyncCallback);
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	void delete(final E entity, final AsyncCallback<E> asyncCallback);
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	void delete(final PK id, final AsyncCallback<E> asyncCallback);

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	E update(final E entity, final AsyncCallback<E> asyncCallback);
	
	@Modifying
	@Transactional(readOnly = true)
	E findById(final PK id, final AsyncCallback<E> asyncCallback);
	
	@Modifying
	@Transactional(readOnly = true)
	List<E> findByProperty(String propertyName, final Object propertyValue, final AsyncCallback<E> asyncCallback, final int... rowStartIdxAndCount);

	@Modifying
	@Transactional(readOnly = true)
	List<E> findAll(final AsyncCallback<E> asyncCallback, final int... rowStartIdxAndCount);
	
	@Modifying
	@Transactional(readOnly = true)
	List<E> getAll(final AsyncCallback<E> asyncCallback);
}