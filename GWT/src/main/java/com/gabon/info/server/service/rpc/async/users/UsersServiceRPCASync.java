package com.gabon.info.server.service.rpc.async.users;

import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.server.model.users.Users;
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
public final class UsersServiceRPCASync extends AbstractServiceRPCASync<Users, Long> implements UsersServiceFacadeRPCASync<Users> {
	
	private static final long serialVersionUID = 6393801519315741473L;
	
	
	public UsersServiceRPCASync() {
		super();
		
		if (this.asyncCallback == null)
			this.asyncCallback = new AsyncCallback<Users>() {
				public void onFailure(Throwable throwable) {
					Window.alert(ASYNC_CALL_BACK_FAILURE);
					GWT.log(throwable.getMessage(), throwable);
				}

				public void onSuccess(Users entity) {
					Window.alert(ASYNC_CALL_BACK_SUCCESS);
				}
			};
	}
	
	
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void save(final Users entity) {
		super.save(entity, this.asyncCallback);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(final Users entity) {
		super.delete(entity, this.asyncCallback);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(final Long id) {
		super.delete(id, this.asyncCallback);
	}

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Users update(final Users entity) {
		return super.update(entity, this.asyncCallback);
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public Users findById(final Long id) {
		return super.findById(id, this.asyncCallback);
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<Users> findByName(final Object name, int... rowStartIdxAndCount) {
		return super.findByProperty("name", name, this.asyncCallback, rowStartIdxAndCount);
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<Users> findByEmail(final Object email, int... rowStartIdxAndCount) {
		return super.findByProperty("email", email, this.asyncCallback, rowStartIdxAndCount);
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<Users> findAll(final int... rowStartIdxAndCount) {
		return super.findAll(this.asyncCallback, rowStartIdxAndCount);
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public List<Users> getAll() {
		return super.getAll(this.asyncCallback);
	}
}