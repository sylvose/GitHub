package com.gabon.info.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

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

	
	// Fields
	
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