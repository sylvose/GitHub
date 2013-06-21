package com.gabon.info.dao.spring.jpa.department;

import java.util.List;

import com.gabon.info.dao.DAOSupportFacade;

/**
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
 * Interface for DepartmentDAO.
 *
 */

public interface DepartmentDAOFacade<Department> extends DAOSupportFacade<Department, Long> {
	
	List<Department> findByDepartment(final Object department, final int... rowStartIdxAndCount);
}