package com.gabon.info.server.model.users;

import static com.gabon.info.server.util.Constants.BEAN_USERS;
import static com.gabon.info.server.util.Constants.EMAIL;
import static com.gabon.info.server.util.Constants.GET_ALL_USERS_NAME;
import static com.gabon.info.server.util.Constants.GET_ALL_USERS_QUERY;
import static com.gabon.info.server.util.Constants.NAME;
import static com.gabon.info.server.util.Constants.T_USERS;
import static com.gabon.info.server.util.Constants.UQAM;
import static com.gabon.info.server.util.Constants.USERS_SEQ;
import static com.gabon.info.server.util.Constants.USERS_SEQ_GEN;
import static com.gabon.info.server.util.Constants.Users;

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
import javax.persistence.UniqueConstraint;
import javax.sql.DataSource;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.stereotype.Component;

import com.gabon.info.server.dao.jdbc.JdbcHelper;
import com.gabon.info.server.model.department.Department;
import com.gabon.info.server.model.office.Office;
import com.gabon.info.server.model.projects.Projects;
import com.gabon.info.server.model.roles.Roles;

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
				
				
				this.clazz = Projects.class;
				this.queryString = QUERY_STRING_SELECT_STAR_FROM_UQAM_TABLE + this.clazz.getSimpleName().toUpperCase() + CLAUSE_STRING_WHERE_ID + projectsId;
				this.preparedStatement = connection.prepareStatement(this.queryString, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				Projects projects = null;
				if (this.resultSet.next())
				this.resultSet = preparedStatement.executeQuery();
					projects = Projects.class.cast(mapperResultSetToGeneric(this.resultSet));
					
					
				this.clazz = Users.class;
				this.queryString = QUERY_STRING_SELECT_STAR_FROM_UQAM_TABLE + this.clazz.getSimpleName().toUpperCase() + CLAUSE_STRING_WHERE_ID + usersId;
				this.preparedStatement = connection.prepareStatement(this.queryString, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				this.resultSet = preparedStatement.executeQuery();
				Users users = null;
				if (this.resultSet.next())
					users = Users.class.cast(mapperResultSetToGeneric(this.resultSet));
				
					
				if (projects != null) 
					projects.getUsers().add(users);
				
				if (users != null) 	
					users.getProjects().add(projects);
				
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