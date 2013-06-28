package com.gabon.info.server.dto.extended;

import java.io.Serializable;
import java.util.Comparator;

import org.dynadto.DTO;
import org.dynadto.MapDTO;

/*
 * A {@link Comparator} to use with {@link DTO}. It compares the map properties of both {@link DTO}.
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

public class DTOComparator implements Comparator<Object>, Serializable {

	private static final long serialVersionUID = 4385280699742449599L;

	
	/**
	 * 
	 * {@inheritDoc}
	 */

	public int compare(final Object object1, final Object object2) {
		if (object1 == object2)
			return 0;

		if (object1 == null)
			return -1;

		if (object2 == null)
			return -1;

		if (DTO.class.isAssignableFrom(object1.getClass())
				&& DTO.class.isAssignableFrom(object2.getClass())) {
			MapDTO dto1 = ((DTO) object1).getMap();
			MapDTO dto2 = ((DTO) object2).getMap();
			boolean equals = dto1.equals(dto2);
			return equals ? 0 : -1;
		}

		return object1.equals(object2) ? 0 : -1;
	}
}