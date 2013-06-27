package com.gabon.info.model.users;

import static com.gabon.info.util.Constants.BEAN_USERS;
import static com.gabon.info.util.Constants.EMAIL;
import static com.gabon.info.util.Constants.GET_ALL_USERS_NAME;
import static com.gabon.info.util.Constants.GET_ALL_USERS_QUERY;
import static com.gabon.info.util.Constants.NAME;
import static com.gabon.info.util.Constants.T_USERS;
import static com.gabon.info.util.Constants.UQAM;
import static com.gabon.info.util.Constants.USERS_SEQ;
import static com.gabon.info.util.Constants.USERS_SEQ_GEN;
import static com.gabon.info.util.Constants.Users;

import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.stereotype.Component;

import com.gabon.info.dao.jpa.JpaHelper;
import com.gabon.info.model.department.Department;
import com.gabon.info.model.office.Office;
import com.gabon.info.model.projects.Projects;
import com.gabon.info.model.roles.Roles;

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

@Component(BEAN_USERS)

@Entity(name = Users)
@Table(name = T_USERS, schema = UQAM, uniqueConstraints = { @UniqueConstraint(columnNames = { NAME, EMAIL }) })
@SequenceGenerator(name = USERS_SEQ_GEN, sequenceName = USERS_SEQ, initialValue = 1, allocationSize = 1)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

@NamedQueries({ 
	@NamedQuery(name = GET_ALL_USERS_NAME, query = GET_ALL_USERS_QUERY)
})

@Cacheable(true)

public final class Users extends AbstractUsers implements UsersFacade {

	private static final long serialVersionUID = -3486769881741316092L;
	
	
	// Constructors

	/** empty constructor */
	public Users() {
		super();
	}
	
	/** minimal constructor */
	public Users(Long id, String name, Office office, Department department) {
		super(id, name, office, department);
	}

	/** light constructor */
	public Users(Long id, String name, Office office, Department department, Set<Roles> roles) {
		super(id, name, office, department, roles);
	}
	
	/** light constructor */
	public Users(Long id, String name, Department department, Office office, Set<Projects> projects) {
		super(id, name, department, office, projects);
	}
	
	/** full constructor */
	public Users(Long id, String name, String email, Office office, Department department, Set<Roles> roles, Set<Projects> projects) {
		super(id, name, email, office, department, roles, projects);
	}

	/** default constructor */
	public Users(Long id, String name, String email, Office office, Department department) {
		super(id, name, email, office, department);
	}
	
	@XmlTransient
	@Transient
	public void addRoles(final Roles roles) {
		if (roles != null) {
			if (!getRoles().contains(roles)) {
				getRoles().add(roles);
			}
		}
	}
	
	@XmlTransient
	@Transient
	public void addRoles(final Set<Roles> rolesList) {
        if (rolesList != null) {
            for (Roles project : rolesList) {
            	roles.add(project);
            }
        }
    }
	
	@XmlTransient
	@Transient
	public void addProjects(final Projects projects) {
		if (projects != null) {
			if (!getProjects().contains(projects)) {
				getProjects().add(projects);
			}
			
			if (!projects.getUsers().contains(this)) {
				projects.getUsers().add(this);
			}
		}
	}
	
	@XmlTransient
	@Transient
	public void addProjects(final Set<Projects> projectsList) {
        if (projectsList != null) {
            for (Projects project : projectsList) {
            	projects.add(project);
            }
        }
    }
	
	@XmlTransient
	@Transient
	public void assignUsersToProjects(final Long usersId, final Long projectsId) {
		if (usersId != null && projectsId != null) {
			final JpaHelper jpaHelper = new JpaHelper();
			final EntityManager entityManager = jpaHelper.getEntityManager();
			final Projects projects = entityManager.find(Projects.class, projectsId);
			final Users users = entityManager.find(Users.class, usersId);
			
			projects.getUsers().add(users);
			users.getProjects().add(projects);
		}
	}
}