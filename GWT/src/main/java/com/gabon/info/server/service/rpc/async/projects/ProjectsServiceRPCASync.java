package com.gabon.info.server.service.rpc.async.projects;

import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.server.model.projects.Projects;
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
public final class ProjectsServiceRPCASync extends AbstractServiceRPCASync<Projects, Long> implements ProjectsServiceFacadeRPCASync<Projects> {

	private static final long serialVersionUID = 824791972736800358L;

	
	public ProjectsServiceRPCASync() {
		super();
		
		if (this.asyncCallback == null)
			this.asyncCallback = new AsyncCallback<Projects>() {
				public void onFailure(Throwable throwable) {
					Window.alert(ASYNC_CALL_BACK_FAILURE);
					GWT.log(throwable.getMessage(), throwable);
				}

				public void onSuccess(Projects entity) {
					Window.alert(ASYNC_CALL_BACK_SUCCESS);
				}
			};
	}
	
	
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void save(final Projects entity) {
		super.save(entity, this.asyncCallback);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(final Projects entity) {
		super.delete(entity, this.asyncCallback);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(final Long id) {
		super.delete(id, this.asyncCallback);
	}

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Projects update(final Projects entity) {
		return super.update(entity, this.asyncCallback);
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public Projects findById(final Long id) {
		return super.findById(id, this.asyncCallback);
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<Projects> findByProject(final Object project, int... rowStartIdxAndCount) {
		return super.findByProperty("project", project, this.asyncCallback, rowStartIdxAndCount);
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<Projects> findAll(final int... rowStartIdxAndCount) {
		return super.findAll(this.asyncCallback, rowStartIdxAndCount);
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public List<Projects> getAll() {
		return super.getAll(this.asyncCallback);
	}
}