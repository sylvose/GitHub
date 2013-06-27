package com.gabon.info.resource;

import static com.gabon.info.util.Constants.URI_PATH_ABSTRACT_RESOURCES;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gabon.info.dao.DAOSupportFacade;
import com.gabon.info.exception.NotDeleteException;
import com.gabon.info.exception.NotSaveException;
import com.gabon.info.exception.NotUpdateException;
import com.sun.jersey.api.NotFoundException;
import com.sun.jersey.api.client.ClientResponse.Status;

/*
  This class is a abstract resource class.
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
 * AbstractResource resource provides the methods for the base remote web service definition of the Models
 */

@Path(value = URI_PATH_ABSTRACT_RESOURCES)
public abstract class AbstractResource<T, E, PK extends Serializable> implements ResourceFacade<T, E, PK> {

	private static final long serialVersionUID = 3317842083231420880L;
		
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	
	protected String lastVisit;
	protected NewCookie newCookie;
	
	protected @Context UriInfo uriInfo;
	
	protected DAOSupportFacade<E, PK> daoSupportFacade;

	protected PK id;
	protected Class<T> clazzT;
	protected Class<E> clazzE;
	protected Class<PK> clazzPK;

	
	@SuppressWarnings("unchecked")
	public AbstractResource() { 
		super();
		
		this.lastVisit = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(new Date());
		this.newCookie = new NewCookie("last-visit", this.lastVisit);
		
		final Type type = getClass().getGenericSuperclass();
		if (type instanceof Type) {
			final Class<ParameterizedType> clazzTParameterizedType = ParameterizedType.class;
			final ParameterizedType parameterizedType = clazzTParameterizedType.cast(type);
		    final Object object = parameterizedType.getActualTypeArguments()[0];
		    this.clazzE = Class.class.cast(object); 
		}
		
		/*
		if (this.uriInfo == null) {
    		final WebApplicationImpl webApplicationImpl = new WebApplicationImpl();
            final ContainerRequest containerRequest = null; //new ContainerRequest();
            final ContainerResponse containerResponse = null; // new ContainerResponse();
            final WebApplicationContext webApplicationContext = new WebApplicationContext(webApplicationImpl, containerRequest, containerResponse);
            this.uriInfo = webApplicationContext.createMatchResourceContext(URI.create("/" + URI_PATH_USERS + "/" + URI_PATH_SAVE_BY_ENTITY  + "/" + id));
    	}
    	*/
	}
    
	/**
	 * @param daoSupportFacade
	 */
	@Autowired(required = true)
	public AbstractResource(@Qualifier DAOSupportFacade<E, PK> daoSupportFacade) {
		setDAOSupportFacade(daoSupportFacade);
	}
    
    
	
	
	/**
	 * @return the lastVisit
	 */
	public String getLastVisit() {
		return lastVisit;
	}

	/**
	 * @param lastVisit the lastVisit to set
	 */
	public void setLastVisit(String lastVisit) {
		this.lastVisit = lastVisit;
	}

	/**
	 * @return the newCookie
	 */
	public NewCookie getNewCookie() {
		return newCookie;
	}

	/**
	 * @param newCookie the newCookie to set
	 */
	public void setNewCookie(NewCookie newCookie) {
		this.newCookie = newCookie;
	}

	/**
	 * @return the uriInfo
	 */
	public UriInfo getUriInfo() {
		return uriInfo;
	}

	/**
	 * @param uriInfo the uriInfo to set
	 */
	public void setUriInfo(UriInfo uriInfo) {
		this.uriInfo = uriInfo;
	}

	/**
	 * @return the daoSupportFacade
	 */
	public DAOSupportFacade<E, PK> getDAOSupportFacade() {
		return daoSupportFacade;
	}

	/**
	 * @param daoSupportFacade the daoSupportFacade to set
	 */
	public void setDAOSupportFacade(DAOSupportFacade<E, PK> daoSupportFacade) {
		this.daoSupportFacade = daoSupportFacade;
	}
	
	/**
	 * @return the id
	 */
	public PK getId(E entity) {
		this.id = getProperty(entity, Id);
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(PK id) {
		this.id = id;
	}
	
	
	
	/**
	 * @return the clazzT
	 */
	public Class<T> getClazzT() {
		return this.clazzT;
	}

	/**
	 * @param clazzT the clazzT to set
	 */
	public void setClazzT(Class<T> clazzT) {
		this.clazzT = clazzT;
	}
	
	/**
	 * @return the clazzE
	 */
	public Class<E> getClazzE() {
		return this.clazzE;
	}

	/**
	 * @param clazzE the clazzE to set
	 */
	public void setClazzE(Class<E> clazzE) {
		this.clazzE = clazzE;
	}
	
	/**
	 * @return the clazzPK
	 */
	public Class<PK> getClazzPK() {
		return clazzPK;
	}

	/**
	 * @param clazzPK the clazzPK to set
	 */
	public void setClazzPK(Class<PK> clazzPK) {
		this.clazzPK = clazzPK;
	}

	
	
	public PK getProperty(E entity, String property) {
        
		PK returnValue = null;

			try {
				final String methodName = GET + property.substring(0, 1).toUpperCase() + property.substring(1, property.length());
				final Class<?>[] parameterTypes = new Class<?>[] {};
				final Object[] args = new Object[] {};

				final Method method = entity.getClass().getMethod(methodName, parameterTypes);
				final Object object = method.invoke(entity, args);
	            
	            if (object instanceof Object)
	            	returnValue = this.clazzPK.cast(object);
	            
			} catch (SecurityException e) {
				logger.debug(e.getClass().getName() + MESSAGE + e.getMessage(), e);
			} catch (IllegalArgumentException e) {
				logger.debug(e.getClass().getName() + MESSAGE + e.getMessage(), e);
			} catch (IllegalAccessException e) {
				logger.debug(e.getClass().getName() + MESSAGE + e.getMessage(), e);
			} catch (NoSuchMethodException e) {
				logger.debug(e.getClass().getName() + MESSAGE + e.getMessage(), e);
			} catch (InvocationTargetException e) {
				logger.debug(e.getClass().getName() + MESSAGE + e.getMessage(), e);
			}
            
        return returnValue;
    }

	
	
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
	public T save(final @QueryParam(E) E entity) {
	
		T response = null;
	
		if (entity == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_SAVE + entity + Status.NOT_FOUND.getReasonPhrase());
		
		try {
			if (this.daoSupportFacade instanceof DAOSupportFacade)
				this.daoSupportFacade.save(entity);
			
			if (this.uriInfo instanceof UriInfo)
				response = this.clazzT.cast(Response.created(this.uriInfo.getAbsolutePath()).entity(entity).cookie(this.newCookie).build());
			
			if (this.daoSupportFacade.findById(getId(entity)) == null)
				throw new NotSaveException(Status.BAD_REQUEST.getReasonPhrase());
			
		} catch (NotSaveException e) {
			logger.debug(Status.BAD_REQUEST.getReasonPhrase(), e);

			response = this.clazzT.cast(Response.status(Response.Status.BAD_REQUEST).build());
			
		} catch (WebApplicationException e) {
			logger.debug(NOT_ALLOW, e);
			
			response = this.clazzT.cast(Response.status(405).header(ALLOW, RequestMethod.POST.name()).build());
			
		} catch (Exception e) {
			logger.debug(Status.INTERNAL_SERVER_ERROR.getReasonPhrase(), e);
			
			response = this.clazzT.cast(Response.serverError().build());
		} 
		
		return response;
	}
	
	/**
	 * @param entity
	 *            E entity to delete
	 * @throws NotFoundException, NotDeletedException, WebApplicationException, Exception
	 *             when the operation fails
	 */
	@DELETE // Annotation for methods that delete representations
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path(value = URI_PATH_DELETE_BY_ENTITY)
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public T delete(final @QueryParam(E) E entity) {
		return delete(getId(entity));
	}
	
	/**
	 * @param id
	 *            PK id to delete
	 * @throws NotFoundException, NotDeletedException, WebApplicationException, Exception
	 *             when the operation fails
	 */
	@DELETE // Annotation for methods that delete representations
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path(value = URI_PATH_DELETE_BY_ID)
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public T delete(final @QueryParam(ID) PK id) {

		T response = null;
		
		if (id == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_DELETE + id + Status.NOT_FOUND.getReasonPhrase());
		
		try {
			E model = null;
			
			if (this.daoSupportFacade instanceof DAOSupportFacade)
				model = this.daoSupportFacade.findById(id);
			
			if (model == null)
				throw new NotFoundException(Status.NOT_FOUND.getReasonPhrase(), this.uriInfo.getAbsolutePath());
			
			this.daoSupportFacade.delete(model);
			
			response = this.clazzT.cast(Response.status(Response.Status.GONE).entity(model).cookie(this.newCookie).build());
			
			if (this.daoSupportFacade.findById(getId(model)) instanceof Object)
				throw new NotDeleteException(Status.BAD_REQUEST.getReasonPhrase());
			
		} catch (NotFoundException e) {
			logger.debug(Status.NOT_FOUND.getReasonPhrase(), e);

			response = this.clazzT.cast(Response.status(Response.Status.NOT_FOUND).build());
			
		} catch (NotDeleteException e) {
			logger.debug(Status.BAD_REQUEST.getReasonPhrase(), e);

			response = this.clazzT.cast(Response.status(Response.Status.BAD_REQUEST).build());
			
		} catch (WebApplicationException e) {
			logger.debug(NOT_ALLOW, e);
	
			response = this.clazzT.cast(Response.status(405).header(ALLOW, RequestMethod.DELETE.name()).build());
			
		} catch (Exception e) {
			logger.debug(Status.INTERNAL_SERVER_ERROR.getReasonPhrase(), e);
			
			response = this.clazzT.cast(Response.serverError().build());
		}
		
		return response;
	}

	/**
	 * @param entity
	 *            T entity to update
	 * @return T the persisted T entity instance, may not be the same
	 * @throws NotValidUpdateException, NotUpdatedException, WebApplicationException, Exception
	 *             if the operation fails
	 */
	@PUT // Annotation for methods that store submitted representations
	@Consumes({ MediaType.TEXT_XML, MediaType.TEXT_PLAIN, MediaType.TEXT_HTML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path(value = URI_PATH_UPDATE_BY_ENTITY)
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public T update(final @QueryParam(E) E entity) {

		T response = null;
		
		if (entity == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_UPDATE + entity + Status.NOT_FOUND.getReasonPhrase());
		
		try {
			E model = null;
			if (this.daoSupportFacade instanceof DAOSupportFacade)
				model = this.daoSupportFacade.findById(getId(entity));
			
			if (model == null)
				throw new NotFoundException(Status.NOT_FOUND.getReasonPhrase());
			
			model = this.daoSupportFacade.update(entity);
			
			response = this.clazzT.cast(Response.ok().entity(model).cookie(this.newCookie).build());
			
			if (this.daoSupportFacade.findById(getId(model)) == null)
				throw new NotUpdateException(Status.BAD_REQUEST.getReasonPhrase());
			
		} catch (NotFoundException e) {
			logger.debug(Status.NOT_FOUND.getReasonPhrase(), e);

			response = this.clazzT.cast(Response.status(Response.Status.NOT_FOUND).build());
			
		} catch (NotUpdateException e) {
			logger.debug(Status.BAD_REQUEST.getReasonPhrase(), e);

			response = this.clazzT.cast(Response.status(Response.Status.BAD_REQUEST).build());
			
		} catch (WebApplicationException e) {
			logger.debug(NOT_ALLOW, e);
			
			response = this.clazzT.cast(Response.status(405).header(ALLOW, RequestMethod.PUT.name()).build());
			
		} catch (Exception e) {
			logger.debug(Status.INTERNAL_SERVER_ERROR.getReasonPhrase(), e);
			
			response = this.clazzT.cast(Response.serverError().build());
		} 
		
		return response;
	}

	/**
	 * @param id
	 *            PK id to find
	 */
	@GET // Annotation for methods that retrieve a resource representation
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path(value = URI_PATH_FIND_BY_ID)
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public T findById(final @QueryParam(ID) PK id) {

		T response = null;
		
		if (id == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_DELETE + id + Status.NOT_FOUND.getReasonPhrase());
		
		try {
			E model = null;
			
			if (this.daoSupportFacade instanceof DAOSupportFacade)
				model = this.daoSupportFacade.findById(id);
			
			if (model == null)
				throw new NotFoundException(Status.NOT_FOUND.getReasonPhrase(), this.uriInfo.getAbsolutePath());
			
			response = this.clazzT.cast(Response.ok().entity(model).cookie(this.newCookie).build());
			
		} catch (NotFoundException e) {
			logger.debug(Status.NOT_FOUND.getReasonPhrase(), e);

			response = this.clazzT.cast(Response.status(Response.Status.NOT_FOUND).build());
			
		} catch (WebApplicationException e) {
			logger.debug(NOT_ALLOW, e);
	
			response = this.clazzT.cast(Response.status(405).header(ALLOW, RequestMethod.GET.name()).build());
			
		} catch (Exception e) {
			logger.debug(Status.INTERNAL_SERVER_ERROR.getReasonPhrase(), e);
			
			response = this.clazzT.cast(Response.serverError().build());
		}
		
		return response;
	}
	
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
	 */
	@GET // Annotation for methods that retrieve a resource representation
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path(value = URI_PATH_FIND_ALL_BY_PROPERTY)
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public T findByProperty(final @QueryParam(PROPERTYNAME) String propertyName, final @QueryParam(OBJECT) Object propertyValue, final @QueryParam(ROWSSTARTIDXANDCOUNT) int... rowStartIdxAndCount) {
	
		T response = null;
	
		if (StringUtils.isBlank(StringUtils.trim(propertyName)) || propertyValue == null || rowStartIdxAndCount.length < 0)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_FIND + propertyName + ", propertyValue: " + propertyValue + ", rowStartIdxAndCount: " + rowStartIdxAndCount);
		
		try {
			List<E> entities = null;
			if (this.daoSupportFacade instanceof DAOSupportFacade)
				entities = this.daoSupportFacade.findByProperty(propertyName, propertyValue, rowStartIdxAndCount);
			
			if (entities == null)
				throw new NotFoundException(Status.NOT_FOUND.getReasonPhrase(), this.uriInfo.getAbsolutePath());
			
			final GenericEntity<List<E>> genericEntity = new GenericEntity<List<E>>(entities) {};
			
			response = this.clazzT.cast(Response.ok().entity(genericEntity).cookie(this.newCookie).build());
			
		} catch (NotFoundException e) {
			logger.debug(Status.NOT_FOUND.getReasonPhrase(), e);
	
			response = this.clazzT.cast(Response.status(Response.Status.NOT_FOUND).build());
			
		} catch (WebApplicationException e) {
			logger.debug(NOT_ALLOW, e);
	
			response = this.clazzT.cast(Response.status(405).header(ALLOW, RequestMethod.GET.name()).build());
			
		} catch (Exception e) {
			logger.debug(Status.INTERNAL_SERVER_ERROR.getReasonPhrase(), e);
			
			response = this.clazzT.cast(Response.serverError().build());
		}
		
		return response;
	}

	/**
	 * 
	 * @param rowStartIdxAndCount
	 *            Optional int varargs. rowStartIdxAndCount[0] specifies the the
	 *            row index in the query result-set to begin collecting the
	 *            results. rowStartIdxAndCount[1] specifies the the maximum
	 *            count of results to return.
	 * @return List<T> all T entities
	 */
	@GET // Annotation for methods that retrieve a resource representation
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path(value = URI_PATH_FIND_ALL)
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public T findAll(final @QueryParam(ROWSSTARTIDXANDCOUNT) int... rowStartIdxAndCount) {
		
		T response = null;
		
		if (rowStartIdxAndCount.length < 0)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_FIND + ", rowStartIdxAndCount: " + rowStartIdxAndCount);
		
		try {
			List<E> entities = null;
			if (this.daoSupportFacade instanceof DAOSupportFacade)
				entities = this.daoSupportFacade.findAll(rowStartIdxAndCount);
			
			if (entities == null)
				throw new NotFoundException(Status.NOT_FOUND.getReasonPhrase(), this.uriInfo.getAbsolutePath());
			
			final GenericEntity<List<E>> genericEntity = new GenericEntity<List<E>>(entities) {};
			
			response = this.clazzT.cast(Response.ok().entity(genericEntity).cookie(this.newCookie).build());
			
		} catch (NotFoundException e) {
			logger.debug(Status.NOT_FOUND.getReasonPhrase(), e);

			response = this.clazzT.cast(Response.status(Response.Status.NOT_FOUND).build());
			
		} catch (WebApplicationException e) {
			logger.debug(NOT_ALLOW, e);
	
			response = this.clazzT.cast(Response.status(405).header(ALLOW, RequestMethod.GET.name()).build());
			
		} catch (Exception e) {
			logger.debug(Status.INTERNAL_SERVER_ERROR.getReasonPhrase(), e);
			
			response = this.clazzT.cast(Response.serverError().build());
		}
		
		return response;
	}
	
	/**
	 * 
	 * @return List<T> all T entities
	 */
	@GET // Annotation for methods that retrieve a resource representation
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path(value = URI_PATH_GET_ALL)
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public T getAll() {
		
		T response = null;
		
		try {
			List<E> entities = null;
			if (this.daoSupportFacade instanceof DAOSupportFacade)
				entities = this.daoSupportFacade.getAll();
			
			if (entities == null)
				throw new NotFoundException(Status.NOT_FOUND.getReasonPhrase(), this.uriInfo.getAbsolutePath());
			
			final GenericEntity<List<E>> genericEntity = new GenericEntity<List<E>>(entities) {};
			
			response = this.clazzT.cast(Response.ok().entity(genericEntity).cookie(this.newCookie).build());
			
		} catch (NotFoundException e) {
			logger.debug(Status.NOT_FOUND.getReasonPhrase(), e);

			response = this.clazzT.cast(Response.status(Response.Status.NOT_FOUND).build());
			
		} catch (WebApplicationException e) {
			logger.debug(NOT_ALLOW, e);
	
			response = this.clazzT.cast(Response.status(405).header(ALLOW, RequestMethod.GET.name()).build());
			
		} catch (Exception e) {
			logger.debug(Status.INTERNAL_SERVER_ERROR.getReasonPhrase(), e);
			
			response = this.clazzT.cast(Response.serverError().build());
		}
		
		return response;
	}
	
	public boolean allowGet() {
		return true;
	}

	public boolean allowPost() {
		return true;
	}

	public boolean allowPut() {
		return true;
	}

	public boolean allowDelete() {
		return true;
	}
	
	public void doInitialized() {
		this.id = this.clazzPK.cast(this.uriInfo.getPathParameters().getFirst(ID));
	}
}