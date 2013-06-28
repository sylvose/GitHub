package com.gabon.info.server.service.rpc.sync;

import static com.gabon.info.server.util.Constants.SERVICE_FACADE_RPC_SYNC;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.server.dao.DAOSupportFacade;
import com.gabon.info.server.util.Constants;
import com.google.gwt.rpc.client.RpcService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

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

@RemoteServiceRelativePath(SERVICE_FACADE_RPC_SYNC)
public interface ServiceFacadeRPCSync<E, PK extends Serializable> extends Constants, Serializable, RpcService {

	PK getProperty(E entity, String property);
	
	PK getId(E entity);

	PK getId();
	
	void setId(Long id);
	
	Class<E> getClazzE();

	void setClazzE(Class<E> clazzE);
	
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
	E update(final E entity);
	
	@Modifying
	@Transactional(readOnly = true)
	E findById(final PK id);
	
	@Modifying
	@Transactional(readOnly = true)
	List<E> findByProperty(String propertyName, final Object propertyValue, final int... rowStartIdxAndCount);

	@Modifying
	@Transactional(readOnly = true)
	List<E> findAll(final int... rowStartIdxAndCount);
	
	@Modifying
	@Transactional(readOnly = true)
	List<E> getAll();
}