package com.gabon.info.server.dto.extended;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;

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

public class GetterMethodComparator implements Comparator<Object>, Serializable {

	private static final long serialVersionUID = -2411218161022174770L;
	
	
	private Method[] getters;

	public GetterMethodComparator(final List<Method> gettersList) {
		super();
		
		getters = new Method[gettersList.size()];
		getters = gettersList.toArray(getters);
	}

	/**
	 * compares the result of executing the getter method defined in the
	 * receiver. in both o1 and o2.
	 */
	public int compare(final Object o1, final Object o2) {
		try {

			if (o1.getClass() != o2.getClass())
				return -1;

			for (Method method : getters) {
				Object value1 = method.invoke(o1);
				Object value2 = method.invoke(02);
				if (value1 != null || value2 != null) {
					if (value1 == null || !value1.equals(value2))
						return -1;
				}
			}

			return 0;
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Unabled to compare objects");
		} catch (InvocationTargetException e) {
			throw new RuntimeException("Unabled to compare objects");
		}
	}
}