package com.gabon.info.server.dto.extended;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Comparator;

import org.dynadto.MapDTO;
import org.dynadto.MapProxy;

/*
 * Compares two objects by comparing the map of properties of them.
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

public class MapDTOComparator implements Comparator<Object>, Serializable {

	private static final long serialVersionUID = 6008609019559410008L;

	
	/**
	 * 
	 * {@inheritDoc}
	 */
	public int compare(final Object o1, final Object o2) {
		if (o1 == o2) {
			return 0;
		}

		if (o1 == null) {
			return -1;
		}

		if (o2 == null) {
			return -1;
		}

		return getMap(o1).equals(getMap(o2)) ? 0 : -1;
	}

	Object getMap(final Object object) {
		if (Proxy.isProxyClass(object.getClass())) {
			InvocationHandler ih = Proxy.getInvocationHandler(object);
			MapProxy mp = null;
			mp = (MapProxy) ih;
			return mp.getWrappedMap();
		} else {
			if (MapDTO.class.isAssignableFrom(object.getClass())) {
				return object;
			}
		}
		return object;
	}
}