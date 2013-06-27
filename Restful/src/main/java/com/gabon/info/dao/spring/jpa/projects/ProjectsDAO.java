package com.gabon.info.dao.spring.jpa.projects;

import static com.gabon.info.util.Constants.BEAN_PROJECTS_DAO_JPA_SPRING;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.dao.spring.jpa.ConcreteDAOJpaSpring;
import com.gabon.info.model.projects.Projects;

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
@Repository(BEAN_PROJECTS_DAO_JPA_SPRING)
public class ProjectsDAO extends ConcreteDAOJpaSpring<Projects, Long> implements ProjectsDAOFacade<Projects> {

	private static final long serialVersionUID = -8420547962061693241L;
	
	
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
		super.save(entity);
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public void delete(final Projects entity) {
		super.delete(entity);
	}
	
	@Modifying
	@Transactional(readOnly = true)
	public void delete(final Long id) {
		super.delete(id);
	}

	@Modifying
	@Transactional(readOnly = true)
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
	public List<Projects> findByProject(final Object project, final int... rowStartIdxAndCount) {
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