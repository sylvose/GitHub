package com.gabon.info.server.service.rpc.async.department;

import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.server.model.department.Department;
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
public final class DepartmentServiceRPCASync extends AbstractServiceRPCASync<Department, Long> implements DepartmentServiceFacadeRPCASync<Department> {
	
	private static final long serialVersionUID = 4461148254826968141L;

	
	public DepartmentServiceRPCASync() {
		super();
		
		if (this.asyncCallback == null)
			this.asyncCallback = new AsyncCallback<Department>() {
				public void onFailure(Throwable throwable) {
					Window.alert(ASYNC_CALL_BACK_FAILURE);
					GWT.log(throwable.getMessage(), throwable);
				}

				public void onSuccess(Department entity) {
					Window.alert(ASYNC_CALL_BACK_SUCCESS);
				}
			};
	}

	
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void save(final Department entity) {
		super.save(entity, this.asyncCallback);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(final Department entity) {
		super.delete(entity, this.asyncCallback);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(final Long id) {
		super.delete(id, this.asyncCallback);
	}

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Department update(final Department entity) {
		return super.update(entity, this.asyncCallback);
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public Department findById(final Long id) {
		return super.findById(id, this.asyncCallback);
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<Department> findByDepartment(final Object department, int... rowStartIdxAndCount) {
		return super.findByProperty("department", department, this.asyncCallback, rowStartIdxAndCount);
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<Department> findAll(final int... rowStartIdxAndCount) {
		return super.findAll(this.asyncCallback, rowStartIdxAndCount);
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public List<Department> getAll() {
		return super.getAll(this.asyncCallback);
	}
}