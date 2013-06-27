package com.gabon.info.resource;

import java.io.Serializable;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.util.Constants;
import com.sun.jersey.api.NotFoundException;

/*
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

public interface ResourceFacade<T, E, PK extends Serializable> extends Constants, Serializable {
	
	/**
	 * @param entity
	 *            T entity to save
	 * @throws NotValidSaveException, NotSaveException, WebApplicationException, Exception
	 *             when the operation fails
	 */
	@POST // Annotation for methods that accept submitted representations
	@Consumes({ MediaType.TEXT_XML, MediaType.TEXT_PLAIN, MediaType.TEXT_HTML })
   	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path(value = URI_PATH_SAVE_BY_ENTITY)
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	T save(final @QueryParam(E) E entity);
	
	/**
	 * @param entity
	 *            E entity to delete
	 * @throws NotFoundException, NotDeleteException, WebApplicationException, Exception
	 *             when the operation fails
	 */
	@DELETE // Annotation for methods that delete representations
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path(value = URI_PATH_DELETE_BY_ENTITY)
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	T delete(final @QueryParam(E) E entity);
	
	/**
	 * @param id
	 *            PK id to delete
	 * @throws NotFoundException, NotDeleteException, WebApplicationException, Exception
	 *             when the operation fails
	 */
	@DELETE // Annotation for methods that delete representations
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path(value = URI_PATH_DELETE_BY_ID)
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	T delete(final @QueryParam(ID) PK id);

	/**
	 * @param entity
	 *            T entity to update
	 * @return T the remoted T entity instance, may not be the same
	 * @throws NotValidUpdateException, NotUpdateException, WebApplicationException, Exception
	 *             if the operation fails
	 */
	@PUT // Annotation for methods that store submitted representations
	@Consumes({ MediaType.TEXT_XML, MediaType.TEXT_PLAIN, MediaType.TEXT_HTML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path(value = URI_PATH_UPDATE_BY_ENTITY)
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	T update(final @QueryParam(E) E entity);

	/**
	 * @param id
	 *            PK id to find
	 * @return T the remoted T entity instance, may not be the same
	 * @throws NotFoundException, WebApplicationException, Exception
	 *             if the operation fails
	 */
	@GET // Annotation for methods that retrieve a resource representation
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path(value = URI_PATH_FIND_BY_ID)
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	T findById(final @QueryParam(ID) PK id);
	
	/**
	 * @param propertyName
	 *            the name of the T property to query
	 * @param value
	 *            the property value to match
	 * @param rowStartIdxAndCount
	 *            Optional int varargs. rowStartIdxAndCount[0] specifies the the
	 *            row index in the query result-set to begin collecting the
	 *            results. rowStartIdxAndCount[1] specifies the the maximum
	 *            number of results to return.
	 * @return List<T> found by query
	 * @throws NotFoundException, WebApplicationException, Exception
	 *             if the operation fails
	 */
	@GET // Annotation for methods that retrieve a resource representation
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path(value = URI_PATH_FIND_ALL_BY_PROPERTY)
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	T findByProperty(final @QueryParam(PROPERTYNAME) String propertyName, final @QueryParam(OBJECT) Object propertyValue, final @QueryParam(ROWSSTARTIDXANDCOUNT) int... rowStartIdxAndCount);

	/**
	 * 
	 * @param rowStartIdxAndCount
	 *            Optional int varargs. rowStartIdxAndCount[0] specifies the the
	 *            row index in the query result-set to begin collecting the
	 *            results. rowStartIdxAndCount[1] specifies the the maximum
	 *            count of results to return.
	 * @return List<T> all T entities
	 * @throws NotFoundException, WebApplicationException, Exception
	 *             if the operation fails
	 */
	@GET // Annotation for methods that retrieve a resource representation
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path(value = URI_PATH_FIND_ALL)
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	T findAll(final @QueryParam(ROWSSTARTIDXANDCOUNT) int... rowStartIdxAndCount);
	
	/**
	 * 
	 * @return List<T> all T entities
	 * @throws NotFoundException, WebApplicationException, Exception
	 *             if the operation fails
	 */
	@GET // Annotation for methods that retrieve a resource representation
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path(value = URI_PATH_GET_ALL)
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	T getAll();
	
	boolean allowGet();

	boolean allowPost();

	boolean allowPut();

	boolean allowDelete();
}