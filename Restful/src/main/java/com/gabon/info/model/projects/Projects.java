package com.gabon.info.model.projects;

import static com.gabon.info.util.Constants.BEAN_PROJECTS;
import static com.gabon.info.util.Constants.GET_ALL_PROJECTS_NAME;
import static com.gabon.info.util.Constants.GET_ALL_PROJECTS_QUERY;
import static com.gabon.info.util.Constants.PROJECTS_SEQ;
import static com.gabon.info.util.Constants.PROJECTS_SEQ_GEN;
import static com.gabon.info.util.Constants.Projects;
import static com.gabon.info.util.Constants.T_PROJECTS;
import static com.gabon.info.util.Constants.UQAM;

import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.stereotype.Component;

import com.gabon.info.dao.spring.jpa.projects.ProjectsDAO;
import com.gabon.info.dao.spring.jpa.projects.ProjectsDAOFacade;
import com.gabon.info.dao.spring.jpa.users.UsersDAO;
import com.gabon.info.dao.spring.jpa.users.UsersDAOFacade;
import com.gabon.info.model.users.Users;

/*
 * This class is a model class.
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

@Component(BEAN_PROJECTS)

@Entity(name = Projects)
@Table(name = T_PROJECTS, schema = UQAM)
@SequenceGenerator(name = PROJECTS_SEQ_GEN, sequenceName = PROJECTS_SEQ, initialValue = 1, allocationSize = 1)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

@NamedQueries({ 
	@NamedQuery(name = GET_ALL_PROJECTS_NAME, query = GET_ALL_PROJECTS_QUERY)
})

@Cacheable(true)

public final class Projects extends AbstractProjects implements ProjectsFacade {

	private static final long serialVersionUID = 8973892901026556179L;

	
	// Constructors
	
	/** default constructor */
	public Projects() {
		super();
	}

	/** full constructor */
	public Projects(Long id, String project, Set<Users> users) {
		super(id, project, users);
	}
	
	/** default constructor */
	public Projects(Long id, String projec) {
		super(id, projec);
	}
	
	@XmlTransient
	@Transient
	public void addUsers(final Users users) {
		if (users != null) {
			if (!getUsers().contains(users)) {
				getUsers().add(users);
			}
			
			if (!users.getProjects().contains(this)) {
				users.getProjects().add(this);
			}
		}
	}
	
	@XmlTransient
	@Transient
	public void addUsers(final Set<Users> usersList) {
        if (usersList != null) {
            for (Users project : usersList) {
            	users.add(project);
            }
        }
    }
	
	@XmlTransient
	@Transient
	public void assignProjectsToUsers(final Long projectsId, final Long usersId) {
		if (projectsId != null && usersId != null) {
			final ProjectsDAOFacade<Projects> projectsDAOFacade = ProjectsDAO.getInstance();
			final Projects projects = projectsDAOFacade.findById(projectsId);
			
			final UsersDAOFacade<Users> usersDAOFacade = UsersDAO.getInstance();
			final Users users = usersDAOFacade.findById(usersId);
			
			if (projects != null) 
				projects.getUsers().add(users);
			
			if (users != null) 	
				users.getProjects().add(projects);
		}
	}
}