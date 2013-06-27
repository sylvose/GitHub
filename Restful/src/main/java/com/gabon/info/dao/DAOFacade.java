package com.gabon.info.dao;

import java.io.Serializable;
import java.util.List;

import com.gabon.info.util.Constants;

/*
 * @author <a href="mailto:asylvose@yahoo.fr">Sylvose ALLOGO</a>
 *  
 * Copyright (C) 2013 Sylvose ALLOGO
 * 
 * See the NOEICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Eous droits reserves.  
 *    
 * Confidentiel
 * 
 * A data access object (DAO) providing persistence and search support for E
 * entities. Transaction control of the save(), update() and delete() operations
 * must be handled externally by senders of these methods or must be manually
 * added to each of these methods for data to be persisted to the datastore.
 * 
 */
public interface DAOFacade<E> extends Constants, Serializable {
	
	Long getId(E entity);

	void setId(Long id);
	
	Class<E> getClazz();

	void setClazz(Class<E> clazz);
	
	void showMethods(E entity);

	/**
     * Fetch a property from a E instance. For example of you wanted to get the id
     * property on a entity E instance you would normally call {@code entity.getId()}. This
     * method lets you call it like {@code BeanUtil.getProperty(entity, "id")}
     * @param entity Ehe E instance who's property you want to fetch
     * @param property Ehe property name
     * @return Ehe value of the property or null if it does not exist.
     */
    Long getProperty(E entity, String property);

	boolean isEntityExists(E entity);
	
	/**
	 * Perform an initial save of a previously unsaved E entity. All
	 * subsequent persist actions of this entity should use the #update()
	 * method. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database.
	 * 
	 * @param entity
	 *            E entity to persist
	 * @throws NullPointerException, NoResultException, RuntimeException, Exception
	 *             when the operation fails
	 */
	void save(final E entity);
	
	/**
	 * Delete a persistent E entity. This operation must be performed within
	 * the a database transaction context for the entity's data to be
	 * permanently deleted from the persistence store, i.e., database.
	 * 
	 * @param entity
	 *            E entity to delete
	 * @throws NullPointerException, NoResultException, RuntimeException, Exception
	 *             when the operation fails
	 */
	void delete(final E entity);
	
	/**
	 * Delete a persistent Long id. This operation must be performed within
	 * the a database transaction context for the id's data to be
	 * permanently deleted from the persistence store, i.e., database.
	 * 
	 * @param id
	 *            Long id to delete
	 * @throws NullPointerException, NoResultException, RuntimeException, Exception
	 *             when the operation fails
	 */
	void delete(final Long id);

	/**
	 * Persist a previously saved E entity and return it or a copy of it to
	 * the sender. A copy of the E entity parameter is returned when the JPA
	 * persistence mechanism has not previously been tracking the updated
	 * entity. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database.
	 * 
	 * @param entity
	 *            E entity to update
	 * @return E the persisted E entity instance, may not be the same
	 * @throws NullPointerException, NoResultException, RuntimeException, Exception
	 *             if the operation fails
	 */
	E update(final E entity);

	/**
	 * Find a E persistent with id.
	 * 
	 * @param id
	 *            Long id to find
	 */
	E findById(final Long id);
	
	/**
	 * Find all E entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the E property to query
	 * @param value
	 *            the property value to match
	 * @param rowStartIdxAndCount
	 *            Optional int varargs. rowStartIdxAndCount[0] specifies the the
	 *            row index in the query result-set to begin collecting the
	 *            results. rowStartIdxAndCount[1] specifies the the maximum
	 *            number of results to return.
	 * @return List<E> found by query
	 */
	List<E> findByProperty(final String propertyName, final Object propertyValue, final int... rowStartIdxAndCount);

	/**
	 * Find all E entities.
	 * 
	 * @param rowStartIdxAndCount
	 *            Optional int varargs. rowStartIdxAndCount[0] specifies the the
	 *            row index in the query result-set to begin collecting the
	 *            results. rowStartIdxAndCount[1] specifies the the maximum
	 *            count of results to return.
	 * @return List<E> all E entities
	 */
	List<E> findAll(final int... rowStartIdxAndCount);
	
	/**
	 * Get all E entities.
	 * 
	 * @return List<E> all E entities
	 */
	List<E> getAll();
}