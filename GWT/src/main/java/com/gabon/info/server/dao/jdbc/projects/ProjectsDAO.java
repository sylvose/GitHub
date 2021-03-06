package com.gabon.info.server.dao.jdbc.projects;

import java.util.List;

import com.gabon.info.server.dao.jdbc.ConcreteDAOJdbc;
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

public class ProjectsDAO extends ConcreteDAOJdbc<Projects> implements ProjectsDAOFacade<Projects> {

	private static final long serialVersionUID = -1682809434359966989L;
	
	
	private static ProjectsDAOFacade<Projects> projectsDAOFacade;
	

	public ProjectsDAO() { 
		super();
		this.clazz = Projects.class;
	}
	
	public ProjectsDAO(Class<Projects> clazz) { 
		super(clazz);
		this.clazz = clazz; 
	}
	
	
	public static ProjectsDAOFacade<Projects> getInstance() {
		synchronized(lock) {
			if (projectsDAOFacade == null)
				projectsDAOFacade = new ProjectsDAO();
		}
	
		return projectsDAOFacade;
	}
	
	
	
	
	public void save(final Projects entity) {
		
		if (entity.getId() != null && entity.getProject() != null) {
			
			this.queryString = QUERY_STRING_SAVE_PROJECTS_BY_ENTITY;
			this.values = new Object[] { entity.getId(), entity.getProject() };
			
			super.save(entity);
		}
	}
	
	public void delete(final Projects entity) {
		super.delete(entity);
	}
	
	public void delete(final Long id) {
		super.delete(id);
	}

	public Projects update(final Projects entity) {
		
		Projects projects = null;
		
		if (entity.getId() != null && entity.getProject() != null) {
			
			this.queryString = QUERY_STRING_UPDATE_PROJECTS_BY_ENTITY;
			this.values = new Object[] { entity.getProject(), entity.getId() };
			
			projects = super.update(entity);
		}
		
		return projects;
	}

	public Projects findById(final Long id) {
		return super.findById(id);
	}
	
	public List<Projects> findByProject(final Object project, final int... rowStartIdxAndCount) {
		return super.findByProperty("project", project, rowStartIdxAndCount);
	}
	
	public List<Projects> findAll(final int... rowStartIdxAndCount) {
		return super.findAll(rowStartIdxAndCount);
	}
	
	public List<Projects> getAll() {
		return super.getAll();
	}
}