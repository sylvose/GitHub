package com.gabon.info.server.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/*
 * This class is a abstract model class.
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
 * AbstractModel entity provides the methods for the base persistence definition of the Models
 */

public abstract class AbstractModel implements Model {
	
	private static final long serialVersionUID = 247551994675168307L;

	protected static final Logger logger = Logger.getLogger(AbstractModel.class.getName());
	
	
	
	// Fields
	
	@XmlTransient
	@Transient
	protected Class<?> clazz;
	
	@XmlTransient
	@Transient
	protected String queryString;
	
	@XmlTransient
	@Transient
	protected PreparedStatement preparedStatement;
	
	@XmlTransient
	@Transient
	protected ResultSet resultSet;
	
	@XmlTransient
	@Transient
	protected Date lastModified;
	
	
	@XmlTransient
	@Transient
	protected long version;
	
	
	
	// Constructors

	/** empty constructor */
	public AbstractModel() {
		this.lastModified = new Date();
	}
	
	
	
	public Class<?> getClazz() {
		return this.clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public PreparedStatement getPreparedStatement() {
		return preparedStatement;
	}

	public void setPreparedStatement(PreparedStatement preparedStatement) {
		this.preparedStatement = preparedStatement;
	}
	
	public ResultSet getResultSet() {
		return resultSet;
	}

	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}


	
	
	
	/**
	 * @return the lastModified
	 */
	public Date getLastModified() {
		return lastModified;
	}

	/**
	 * @param lastModified the lastModified to set
	 */
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	/**
	 * @return the version
	 */
	public long getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(long version) {
		this.version = version;
	}

	@XmlTransient
	@Transient
	public Date getVersion(Model model) {
		final Date lastModified = model.getLastModified();
		final GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(lastModified);
		calendar.set(Calendar.MILLISECOND, 0);
		
		return calendar.getTime();
	}

	@XmlTransient
	@Transient
	protected Object mapperResultSetToGeneric(final ResultSet resultSet) {
        
		Object entity = null;
				
		if (resultSet != null)  {
			
			try {
				final RowProcessor rowProcessor = new BasicRowProcessor(); 
				entity = rowProcessor.toBean(resultSet, this.clazz);
				
			} catch (SQLException e) {
				logger.log(Level.SEVERE, e.getClass().getName() + MESSAGE + e.getMessage(), e);
			}
		}
       
        return entity;
    }

	
	
	
	
	@Override
	public boolean equals(Object object) {
		// return EqualsBuilder.reflectionEquals(this, object);
		return new EqualsBuilder().appendSuper(super.equals(object)).isEquals();
	}
    
	@Override
	public int hashCode() {
		// return HashCodeBuilder.reflectionHashCode(this);
		return new HashCodeBuilder(357504959, 1759723435).appendSuper(super.hashCode()).toHashCode();
	}

	@Override
	public String toString() {
		 // return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE, false, this.getClass());
		 return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).appendSuper(super.toString()).toString();
	}
}