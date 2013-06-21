package com.gabon.info.auditor.awar;

import org.springframework.data.domain.AuditorAware;
import static com.gabon.info.util.RestFulConstants.SYSTEM;

/*
 * This class UserResource return an instance of the User domain class.
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

public class UserAuditorAwar implements AuditorAware<String> {

	@Override
	public String getCurrentAuditor() {
		
		 return SYSTEM;
	}
}