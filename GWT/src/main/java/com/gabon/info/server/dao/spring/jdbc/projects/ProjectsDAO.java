package com.gabon.info.server.dao.spring.jdbc.projects;

import static com.gabon.info.server.util.Constants.BEAN_PROJECTS_DAO_JDBC_SPRING;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.server.dao.spring.jdbc.ConcreteDAOJdbcSpring;
import com.gabon.info.server.model.projects.Projects;

/**
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

@Transactional
@Repository(BEAN_PROJECTS_DAO_JDBC_SPRING)
public class ProjectsDAO extends ConcreteDAOJdbcSpring<Projects, Long> implements ProjectsDAOFacade<Projects> {

	private static final long serialVersionUID = -1682809434359966989L;
	
	
	private static ProjectsDAOFacade<Projects> projectsDAOFacade;
	

	public ProjectsDAO() { 
		super();
		this.clazzE = Projects.class;
		this.clazzPK = Long.class;
	}
	
	
	public static ProjectsDAOFacade<Projects> getInstance() {
		synchronized(lock) {
			if (projectsDAOFacade == null)
				projectsDAOFacade = new ProjectsDAO();
		}
	
		return projectsDAOFacade;
	}
	
	
	
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void save(final Projects entity) {
		
		if (entity.getId() != null && entity.getProject() != null) {
			
			this.queryString = QUERY_STRING_SAVE_PROJECTS_BY_ENTITY;
			this.values = new Object[] { entity.getId(), entity.getProject() };
			
			super.save(entity);
		}
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
		
		Projects projects = null;
		
		if (entity.getId() != null && entity.getProject() != null) {
			
			this.queryString = QUERY_STRING_UPDATE_PROJECTS_BY_ENTITY;
			this.values = new Object[] { entity.getProject(), entity.getId() };
			
			projects = super.update(entity);
		}
		
		return projects;
	}

	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Projects findById(final Long id) {
		return super.findById(id);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<Projects> findByProject(final Object project, final int... rowStartIdxAndCount) {
		return super.findByProperty("project", project, rowStartIdxAndCount);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<Projects> findAll(final int... rowStartIdxAndCount) {
		return super.findAll(rowStartIdxAndCount);
	}
	
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<Projects> getAll() {
		return super.getAll();
	}
}