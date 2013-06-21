package com.gabon.info.enums.users;

import static com.gabon.info.util.RestFulConstants.MESSAGE;
import static com.gabon.info.util.RestFulConstants.NULL_ID;
import static com.gabon.info.util.RestFulConstants.FIVE_NAME_ID;
import static com.gabon.info.util.RestFulConstants.SIX_NAME_ID;
import static com.gabon.info.util.RestFulConstants.FIVE_USERS;
import static com.gabon.info.util.RestFulConstants.SIX_USERS;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.enums.Enum;
import org.apache.commons.lang.enums.EnumUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gabon.info.model.users.Users;

/*
 * This class is a enum class.
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

public class UsersEnum extends Enum implements UsersType {

	private static final long serialVersionUID = -2746275468646894160L;

	private static final Log log = LogFactory.getLog(UsersEnum.class);
	
	
	private static final Set<String> SET_STRING_ENUM = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList(new String [] { NULL_ID, FIVE_NAME_ID, SIX_NAME_ID })));
	
	private static final UsersEnum NULL = new UsersEnum(NULL_ID);
	
	private static final UsersEnum FIVE_USERSENUM = new UsersEnum(FIVE_NAME_ID, FIVE_USERS);
	private static final UsersEnum SIX_USERSENUM = new UsersEnum(SIX_NAME_ID, SIX_USERS);
	private static final Set<UsersEnum> SET_USERS_ENUM = Collections.unmodifiableSet(new HashSet<UsersEnum>(Arrays.asList(new UsersEnum [] { FIVE_USERSENUM, SIX_USERSENUM })));
	
	
	
	private String name;  
	
	private Users user;

	
	/**
	 * Constructor for UserEnum.
	 * @param name
	 */
	public UsersEnum(String name) {
		super(name);
		if (name == null)
			throw new NullPointerException("Unexpected value name : " + name + " was expected.");
		
		this.name = name;
	}
	
	/**
	 * Constructor for UserEnum.
	 * @param user
	 * @param name
	 */
	public UsersEnum(String name, Users user) {
		super(name);
		if (name == null || user == null)
			throw new NullPointerException("Unexpected value name : " + name + " found where " + user + " was expected.");
		
		this.user = user;
		this.name = name;
	}
	
	/**
	 * Method for UserEnum.
	 * @param name
	 */
	public static UsersEnum getEnum(String name) {
		if (name == null)
			throw new NullPointerException("Unexpected value name : " + name + " was expected.");
		
		return (UsersEnum) EnumUtils.getEnum(UsersEnum.class, name);
	}
		
	/**
	 * Method for UserEnum.
	 * @param name
	 */
	public static boolean isUserEnum(String name) {
		if (name == null)
			throw new NullPointerException("Unexpected value name : " + name + " was expected.");
		
		boolean isstring = Boolean.FALSE;
		
		if (StringUtils.isNotBlank(StringUtils.trim(name))) {
			final Iterator<String> iterator = SET_STRING_ENUM.iterator();
			while (iterator.hasNext()) {
				String string = iterator.next();
				if (name.equalsIgnoreCase(string)) {
					isstring = Boolean.TRUE;
				}
			}
		} else {
			throw new IllegalArgumentException("Unexpected value name : " + name + " was expected.");
		}
		
		return isstring;
	}
	
	/**
	 * Method for UserEnum.
	 * @param enumName
	 */
	public static boolean isUserEnum(UsersEnum userEnum) {
		if (userEnum == null)
			throw new NullPointerException("Unexpected value userEnum : " + userEnum + " was expected.");
		
		boolean isEnum = Boolean.FALSE;
		
		if (userEnum instanceof UsersEnum) {
			final Iterator<UsersEnum> iterator = SET_USERS_ENUM.iterator();
			while (iterator.hasNext()) {
				UsersEnum enums = iterator.next();
				if ((userEnum.equals(enums) && (userEnum.name.equals(enums.name)))) {
					isEnum = Boolean.TRUE;
				}
			}
		} else {
			throw new IllegalArgumentException("Unexpected value userEnum : " + userEnum + " was expected.");
		}
		
		return isEnum;
	}
				
	/**
	 * @return the name
	 */
	public String getEnumValue() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setEnumValue(String name) {
		this.name = name;
	}
    
	/**
	 * @param user the user to set
	 */
	public void setClazz(Users user) {
		this.user = user;
	}
	
	/**
	 * @return the UserEnum
	 */
	public UsersEnum getEnumValue(String name) {
		if (name == null)
			throw new NullPointerException("Unexpected value name : " + name + " was expected.");
		
		if ((name.equals(FIVE_USERSENUM.name)) == true) { return FIVE_USERSENUM; }
		else if ((name.equals(SIX_USERSENUM.name)) == true) { return SIX_USERSENUM; }
		else if ((name.equals(NULL.name)) == true) { return NULL; }
		else {
    		return NULL;
    	}
	}
	
	public String toStringBuffer() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("<UserEnum>");
		stringBuffer.append("<name>").append(name).append("</name>");
		stringBuffer.append("<user>").append(user).append("</user>");
		stringBuffer.append("</UserEnum>");
        return stringBuffer.toString();
	}
  
   public static UsersEnum getUserEnum(Class<?> user) {
		UsersEnum userEnum = null;
		
		if (user == null)
			throw new NullPointerException("Unexpected value, the Enum Class must not be null : " + user);
		
		if (!(Enum.class.isAssignableFrom(user)))
			throw new IllegalArgumentException("The Class must be a subclass of Enum : " + user);
		
		try {
			List<?> list = Enum.getEnumList(ClassUtils.getClass(user.getName()));
			if (!(list.isEmpty())) {
				for (Iterator<?> iteratorList = list.iterator(); iteratorList.hasNext();) {
					UsersEnum enums = (UsersEnum) iteratorList.next();
					if (enums.user.equals(user))
						userEnum = enums;
				}
			}
		} catch (Exception e) {
			log.debug("Exception Type : " + e.getClass().getName() + MESSAGE + e.getMessage() + ", Unexpected value " + user + " found where " + userEnum + " was expected.");
		}
		
		if ((userEnum == null) && (!(SET_USERS_ENUM.isEmpty()))) {
				final Iterator<UsersEnum> iteratorSet = SET_USERS_ENUM.iterator();
				while (iteratorSet.hasNext()) {
					UsersEnum enums = iteratorSet.next();
					if (enums.user.equals(user)) {
						userEnum = enums;
					}
				}
		}
		
		return userEnum;
	}
}