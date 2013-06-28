package com.gabon.info.server.service.rpc.async.office;

import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.server.model.office.Office;
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
public final class OfficeServiceRPCASync extends AbstractServiceRPCASync<Office, Long> implements OfficeServiceFacadeRPCASync<Office> {
	
	private static final long serialVersionUID = -4064067004960010891L;

	
	public OfficeServiceRPCASync() {
		super();
		
		if (this.asyncCallback == null)
			this.asyncCallback = new AsyncCallback<Office>() {
				public void onFailure(Throwable throwable) {
					Window.alert(ASYNC_CALL_BACK_FAILURE);
					GWT.log(throwable.getMessage(), throwable);
				}

				public void onSuccess(Office entity) {
					Window.alert(ASYNC_CALL_BACK_SUCCESS);
				}
			};
	}
	
	
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void save(final Office entity) {
		super.save(entity, this.asyncCallback);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(final Office entity) {
		super.delete(entity, this.asyncCallback);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(final Long id) {
		super.delete(id, this.asyncCallback);
	}

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Office update(final Office entity) {
		return super.update(entity, this.asyncCallback);
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public Office findById(final Long id) {
		return super.findById(id, this.asyncCallback);
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<Office> findByOffice(final Object office, int... rowStartIdxAndCount) {
		return super.findByProperty("office", office, this.asyncCallback, rowStartIdxAndCount);
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<Office> findAll(final int... rowStartIdxAndCount) {
		return super.findAll(this.asyncCallback, rowStartIdxAndCount);
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public List<Office> getAll() {
		return super.getAll(this.asyncCallback);
	}
}