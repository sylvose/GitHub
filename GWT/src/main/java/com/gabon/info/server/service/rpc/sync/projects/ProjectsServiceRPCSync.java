package com.gabon.info.server.service.rpc.sync.projects;

import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.server.dao.spring.jdbc.projects.ProjectsDAO;
import com.gabon.info.server.model.projects.Projects;
import com.gabon.info.server.service.rpc.sync.AbstractServiceRPCSync;


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
public final class ProjectsServiceRPCSync extends AbstractServiceRPCSync<Projects, Long> implements ProjectsServiceFacadeRPCSync<Projects> {

	private static final long serialVersionUID = -1515496178309347032L;

	
	public ProjectsServiceRPCSync() {
		super();
		
		if (this.daoSupportFacade == null)
			this.daoSupportFacade = ProjectsDAO.getInstance();
	}
	
	
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void save(final Projects entity) {
		super.save(entity);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(final Projects entity) {
		super.delete(entity);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(final Long id) {
		super.delete(id);
	}

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Projects update(final Projects entity) {
		return super.update(entity);
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public Projects findById(final Long id) {
		return super.findById(id);
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<Projects> findByProject(final Object project, int... rowStartIdxAndCount) {
		return super.findByProperty("project", project, rowStartIdxAndCount);
	}

	@Modifying
	@Transactional(readOnly = true)
	public List<Projects> findAll(final int... rowStartIdxAndCount) {
		return super.findAll(rowStartIdxAndCount);
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public List<Projects> getAll() {
		return super.getAll();
	}
}