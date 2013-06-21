package com.gabon.info.resource.department;

import static com.gabon.info.util.RestFulConstants.URI_PATH_DEPARTMENTS;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.dao.spring.jpa.department.DepartmentDAO;
import com.gabon.info.model.department.Department;
import com.gabon.info.resource.AbstractResource;

/*
 * This class DepartmentResource return an instance of the Department model class.
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

@Path(value = URI_PATH_DEPARTMENTS)
public final class DepartmentResource extends AbstractResource<Response, Department, Long> implements DepartmentResourceFacade<Response, Department> {

	private static final long serialVersionUID = 568008232872615279L;

	
	public DepartmentResource() {
    	super();
    	
    	this.clazzE = Department.class;
    	this.clazzT = Response.class;
    	
    	this.daoSupportFacade = new DepartmentDAO(); 
    }
	
	
	@POST // Annotation for methods that accept submitted representations
	@Consumes({ MediaType.TEXT_XML, MediaType.TEXT_PLAIN, MediaType.TEXT_HTML })
   	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path(value = URI_PATH_SAVE_BY_ENTITY)
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Response save(final @QueryParam(Department) Department entity) {
		return super.save(entity);
	}
	
	@DELETE // Annotation for methods that delete representations
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path(value = URI_PATH_DELETE_BY_ENTITY)
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Response delete(final @QueryParam(Department) Department entity) {
		return super.delete(entity.getId());
	}
	
	@DELETE // Annotation for methods that delete representations
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path(value = URI_PATH_DELETE_BY_ID)
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Response delete(final @QueryParam(ID) Long id) {
		return super.delete(id);
	}

	@PUT // Annotation for methods that store submitted representations
	@Consumes({ MediaType.TEXT_XML, MediaType.TEXT_PLAIN, MediaType.TEXT_HTML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path(value = URI_PATH_UPDATE_BY_ENTITY)
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Response update(final @QueryParam(Department) Department entity) {
		return super.update(entity);
	}
	
	@GET // Annotation for methods that retrieve a resource representation
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path(value = URI_PATH_FIND_BY_ID)
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Response findById(final @QueryParam(ID) Long id) {
		return super.findById(id);
	}
	
	@GET // Annotation for methods that retrieve a resource representation
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path(value = URI_PATH_FIND_ALL_BY_DEPARTMENT)
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Response findByDepartment(final @QueryParam(DEPARTMENT) Object department, final @QueryParam(ROWSSTARTIDXANDCOUNT) int... rowStartIdxAndCount) {
		return super.findByProperty("department", department, rowStartIdxAndCount);
	}
	
	@GET // Annotation for methods that retrieve a resource representation
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path(value = URI_PATH_FIND_ALL)
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Response findAll(final @QueryParam(ROWSSTARTIDXANDCOUNT) int... rowStartIdxAndCount) {
		return super.findAll(rowStartIdxAndCount);
	}
	
	@GET // Annotation for methods that retrieve a resource representation
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path(value = URI_PATH_GET_ALL)
	@Modifying
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Response getAll() {
		return super.getAll();
	}
}