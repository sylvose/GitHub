package com.gabon.info.server.service.rpc.sync.department;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.server.service.rpc.sync.ServiceFacadeRPCSync;



/*
 * This class is a interface service class.
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

public interface DepartmentServiceFacadeRPCSync<Department> extends ServiceFacadeRPCSync<Department, Long> {

	@Modifying
	@Transactional(readOnly = true)
	List<Department> findByDepartment(final Object department, int... rowStartIdxAndCount);
}