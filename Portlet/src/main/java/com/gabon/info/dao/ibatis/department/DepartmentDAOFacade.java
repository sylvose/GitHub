package com.gabon.info.dao.ibatis.department;

import java.util.List;

import com.gabon.info.dao.DAOFacade;

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

public interface DepartmentDAOFacade<Department> extends DAOFacade<Department> {
	
	List<Department> findByDepartment(final Object department, final int... rowStartIdxAndCount);
}