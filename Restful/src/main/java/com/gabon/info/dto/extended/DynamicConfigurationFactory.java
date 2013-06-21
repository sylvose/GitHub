package com.gabon.info.dto.extended;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dynadto.CollectionMappingConfiguration;
import org.dynadto.Configuration;
import org.dynadto.DTO;
import org.dynadto.PersistableDTO;
import org.dynadto.util.Strings;

/*
 * This object automatically creates a {@link Configuration} for a given type of object.
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

public class DynamicConfigurationFactory {

	static Set<Method> dtoExcludedMethods;

	static {
		dtoExcludedMethods = new HashSet<Method>();
		dtoExcludedMethods.addAll(Arrays.asList(PersistableDTO.class
				.getMethods()));
	}

	/**
	 * Crea una configuracion en base una interface DTO y reflection.
	 * 
	 * @param <T>
	 * @param dtoClass
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public <T extends DTO> Configuration createConfiguration(
			final Class<T> dtoClass) throws Exception {

		Class<?> modelClass = Object.class;
		Configuration configuration = new Configuration();
		// target
		configuration.setTarget(dtoClass);
		// Voy a crear un proxy java
		configuration.setUseProxy(true);
		// Consigo todos lo metodos getters de la interface
		List<Method> getterMethods = getGetterMethods(dtoClass);

		// Creo el mapa de alias
		configuration.setGetterAliasesMap(new HashMap<String, String>());
		configuration.getGetterAliasesMap().put(modelClass, new HashMap<String, String>());

		// Creo el mapa de colecciones
		configuration.setGetterCollectionMaps(new HashMap<String, String>());
		configuration.getGetterCollectionMaps().put(modelClass, new HashMap<String, String>());

		// Creo el mapa de propiedades
		configuration.setGetterToSetterMap(new HashMap<String, String>());
		configuration.getGetterToSetterMap().put(modelClass, new HashMap<String, String>());

		for (Method getterMethod : getterMethods) {
			processGetterMethod(getterMethod, modelClass, dtoClass,
					configuration);
		}

		configuration.setComparator(new DTOComparator());
		return configuration;
	}

	private void processGetterMethod(final Method getterMethod,
			final Class<?> modelClass, final Class<? extends DTO> dtoClass,
			final Configuration configuration) throws Exception {
		// Es una collecion?
		if (Collection.class.isAssignableFrom(getterMethod.getReturnType())) {
			processesCollectionGetterMethod(getterMethod, modelClass,
					configuration);
			return;
		}

		// Es un mapa?
		if (Map.class.isAssignableFrom(getterMethod.getReturnType())) {
			processesMapGetterMethod(getterMethod, modelClass, dtoClass,
					configuration);
			return;
		}

		// Cualquier otra propiedad
		processPropertyGetterMethod(getterMethod, modelClass, dtoClass,
				configuration);

	}
	
	private void processesCollectionGetterMethod(final Method getterMethod,
			final Class<?> modelClass, final Configuration configuration)
			throws Exception {
		
		@SuppressWarnings("unchecked")
		Map<String, CollectionMappingConfiguration> collectionMap = (Map<String, CollectionMappingConfiguration>) configuration.getGetterCollectionMaps().get(modelClass);
		
		@SuppressWarnings("unchecked")
		Map<String, String> aliasesMap = (Map<String, String>) configuration.getGetterAliasesMap().get(modelClass);

		// De que tipo es la collecion del getter
		ParameterizedType type = (ParameterizedType) getterMethod.getGenericReturnType();

		Class<?> targetType = (Class<?>) type.getActualTypeArguments()[0];

		String action = "list";
		String property = Strings.getPropertyNameForMethod(getterMethod
				.getName());

		String targetList = property;

		CollectionMappingConfiguration cmc = new CollectionMappingConfiguration();
		cmc.setAction(action);
		cmc.setProperty(property);
		cmc.setTargetType(targetType.getName());
		cmc.setTargetList(targetList);
		// cmc.setCollectionType(collectionType);

		aliasesMap.put(property, targetList);
		collectionMap.put(property, cmc);

	}

	/**
	 * Used to validate that a given dtoGetter exists in the modelClass
	 * according to what is required by the DTO interface.
	 * 
	 * @param dtoGetter
	 * @param modelClass
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private void processesMapGetterMethod(
			final Method getterMethod,
			final Class<?> modelClass,
			final Class<? extends DTO> dtoClass,
			final Configuration configuration) {

		throw new UnsupportedOperationException();
	}

	private void processPropertyGetterMethod(final Method getterMethod,
			final Class<?> modelClass,
			final Class<? extends DTO> dtoClass,
			final Configuration configuration) throws Exception {

		@SuppressWarnings("unchecked")
		Map<String, String> getterToSetterMap = (Map<String, String>) configuration.getGetterToSetterMap().get(modelClass);

		// Espero que el objeto tenga la misma propiedad
		String property = Strings.getPropertyNameForMethod(getterMethod
				.getName());
		getterToSetterMap.put(property, property);
	}

	/**
	 * Retorna todos los metodos getters de una interface, excluyendo los que
	 * estan en la interface DTO.
	 * 
	 * @param dtoClass
	 * @return
	 */
	private List<Method> getGetterMethods(final Class<? extends DTO> dtoClass) {
		List<Method> getterMethods = new LinkedList<Method>();
		Method[] allMethods = dtoClass.getMethods();
		for (Method method : allMethods) {
			if (Strings.isGetter(method.getName())
					&& method.getParameterTypes().length == 0
					&& !method.getReturnType().equals(Void.TYPE)
					&& shouldInclude(method)) {
				getterMethods.add(method);
			}
		}
		return getterMethods;
	}

	private static boolean shouldInclude(final Method method) {
		return !dtoExcludedMethods.contains(method);
	}
}