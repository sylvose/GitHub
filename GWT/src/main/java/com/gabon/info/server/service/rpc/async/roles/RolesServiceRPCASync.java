package com.gabon.info.server.service.rpc.async.roles;

import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.server.model.roles.Roles;
import com.gabon.info.server.service.rpc.async.AbstractServiceRPCASync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;


/*
 * This class is a service class.
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

@NotThreadSafe
public final class RolesServiceRPCASync extends AbstractServiceRPCASync<Roles, Long> implements RolesServiceFacadeRPCASync<Roles> {

	private static final long serialVersionUID = 3092366441225763108L;

	
	public RolesServiceRPCASync() {
		super();
		
		if (this.asyncCallback == null)
			this.asyncCallback = new AsyncCallback<Roles>() {
				public void onFailure(Throwable throwable) {
					Window.alert(ASYNC_CALL_BACK_FAILURE);
					GWT.log(throwable.getMessage(), throwable);
				}

				public void onSuccess(Roles entity) {
					Window.alert(ASYNC_CALL_BACK_SUCCESS);
				}
			};
	}
	
	
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void save(final Roles entity) {
		super.save(entity, this.asyncCallback);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(final Roles entity) {
		super.delete(entity, this.asyncCallback);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(final Long id) {
		super.delete(id, this.asyncCallback);
	}

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Roles update(final Roles entity) {
		return super.update(entity, this.asyncCallback);
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public Roles findById(final Long id) {
		return super.findById(id, this.asyncCallback);
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<Roles> findByRole(final Object role, int... rowStartIdxAndCount) {
		return super.findByProperty("role", role, this.asyncCallback, rowStartIdxAndCount);
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<Roles> findAll(final int... rowStartIdxAndCount) {
		return super.findAll(this.asyncCallback);
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public List<Roles> getAll() {
		return super.getAll(this.asyncCallback);
	}
}