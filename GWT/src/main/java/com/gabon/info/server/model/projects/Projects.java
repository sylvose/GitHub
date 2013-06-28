package com.gabon.info.server.model.projects;


import static com.gabon.info.server.util.Constants.BEAN_PROJECTS;
import static com.gabon.info.server.util.Constants.GET_ALL_PROJECTS_NAME;
import static com.gabon.info.server.util.Constants.GET_ALL_PROJECTS_QUERY;
import static com.gabon.info.server.util.Constants.PROJECTS_SEQ;
import static com.gabon.info.server.util.Constants.PROJECTS_SEQ_GEN;
import static com.gabon.info.server.util.Constants.Projects;
import static com.gabon.info.server.util.Constants.T_PROJECTS;
import static com.gabon.info.server.util.Constants.UQAM;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.logging.Level;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.sql.DataSource;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.stereotype.Component;

import com.gabon.info.server.dao.jdbc.JdbcHelper;
import com.gabon.info.server.model.users.Users;

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
			
			final JdbcHelper jdbcHelper = new JdbcHelper();
			
			try {
				Connection connection = jdbcHelper.getConnection();
				connection.setAutoCommit(false);
				
				if (connection == null || connection.isClosed()) {
					final DataSource dataSource = jdbcHelper.getDataSource();
					connection = dataSource.getConnection();
				}
				
				if (connection != null)
					connection.setAutoCommit(false);
				
				
				this.clazz = Users.class;
				this.queryString = QUERY_STRING_SELECT_STAR_FROM_UQAM_TABLE + this.clazz.getSimpleName().toUpperCase() + CLAUSE_STRING_WHERE_ID + usersId;
				this.preparedStatement = connection.prepareStatement(this.queryString, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				this.resultSet = preparedStatement.executeQuery();
				Users users = null;
				if (this.resultSet.next())
					users = Users.class.cast(mapperResultSetToGeneric(this.resultSet));
				
				
				this.clazz = Projects.class;
				this.queryString = QUERY_STRING_SELECT_STAR_FROM_UQAM_TABLE + this.clazz.getSimpleName().toUpperCase() + CLAUSE_STRING_WHERE_ID + projectsId;
				this.preparedStatement = connection.prepareStatement(this.queryString, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				Projects projects = null;
				if (this.resultSet.next())
				this.resultSet = preparedStatement.executeQuery();
					projects = Projects.class.cast(mapperResultSetToGeneric(this.resultSet));
				
					
				if (users != null) 	
					users.getProjects().add(projects);
				
				if (projects != null) 
					projects.getUsers().add(users);
				
			} catch (RuntimeException r) {
				logger.log(Level.SEVERE, r.getClass().getName() + MESSAGE + r.getMessage(), r);
				throw r;
			} catch (SQLException sql) {
				logger.log(Level.SEVERE, sql.getClass().getName() + MESSAGE + sql.getMessage(), sql);
		    } catch (Exception e) {
		    	logger.log(Level.SEVERE, e.getClass().getName() + MESSAGE + e.getMessage(), e);
			} finally {
				jdbcHelper.close();
			}
		}
	}
}