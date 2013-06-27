package com.gabon.info.dto;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.dynadto.DTO;
import org.dynadto.MapDTO;

/*
 * This class is a abstract dto class.
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
 * AbstractDTO entity provides the methods for the base persistence definition of the DTOs
 */

public abstract class AbstractDTO<E extends DTO> implements DTO {
		
	private static final long serialVersionUID = -8837585141337865589L;

	
	protected Long id;
	
	protected Class<E> clazz;
	 
	

	// Property accessors
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the clazz
	 */
	public Class<E> getClazz() {
		return clazz;
	}

	/**
	 * @param clazz the clazz to set
	 */
	public void setClazz(Class<E> clazz) {
		this.clazz = clazz;
	}

	/*
     * (non-Javadoc)
     * 
     * @see org.dynadto.DTO#init()
     */
    public void init() {
        // handled in the proxy.
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dynadto.DTO#getClassName()
     */
    public String getClassName() {
        return this.getClass().getName();
    }
	
    /* either throw an exception of refactor the ListDTO interface
     *into its own hierarchy separate from DTO
     */
    public MapDTO getMap() {
        return null;
    }
    
	@Override
	public int compareTo(Object object) {
		// return CompareToBuilder.reflectionCompare(this, object);
		return new CompareToBuilder().toComparison();
	}
}