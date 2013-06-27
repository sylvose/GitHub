package com.gabon.info.util;

import static com.gabon.info.util.Constants.NAME;
import static com.gabon.info.util.Constants.URL_SERVER;
import static com.gabon.info.util.Constants.URL_WEB_SERVICE_PROJECT_ON_THE_SERVER;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.StringUtils;

import com.gabon.info.model.users.Users;
import com.google.common.base.Objects;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.core.util.Base64;

/*
 * This class RestFulUtils provides many methods tools.
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

public class RestFulUtils {
	
		private @Context Request request;
	
		private @Context HttpHeaders httpHeaders;
		
		
		public static URI getAbsolutePath(@Context UriInfo uriInfo) {
			URI absolutePath = null;
			
			if (uriInfo != null)
				absolutePath = uriInfo.getAbsolutePath();
			
			return absolutePath;
		}

		public static URI getBaseURI(String path) {
			if (StringUtils.isBlank(StringUtils.trim(path)))
				path = URL_WEB_SERVICE_PROJECT_ON_THE_SERVER;
			
		    return UriBuilder.fromUri(path).build();
		}
		
		public static URI getBaseURI2(@Context UriInfo uriInfo) {
			URI baseURI = null;
			
			if (uriInfo != null)
				baseURI = uriInfo.getBaseUri();
			
			return baseURI;
		}

		public static URI getRequestUri(@Context UriInfo uriInfo) {
			URI requestUri = null;
			
			if (uriInfo != null)
				requestUri = uriInfo.getRequestUri();
			
			return requestUri;
		}
		
		private Client createClient() {
			final ClientConfig clientConfig = new DefaultClientConfig();
			clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
			
			return Client.create(clientConfig);
		}

		public static WebResource webResource() {
			final ClientConfig clientConfig = new DefaultClientConfig();
			final Client client = Client.create(clientConfig);
			final WebResource webResource = client.resource(UriBuilder.fromUri(URL_WEB_SERVICE_PROJECT_ON_THE_SERVER).build());
			
			return webResource;
		}

		private WebResource webResource(String path) {
			return createClient().resource(URL_SERVER).path(path);
		}

		public WebResource webResource(String name, Map<String, ?> map) {
			final URI uri = UriBuilder.fromPath(name).buildFromMap(map);
			
			return webResource(uri.getPath());
		}
		
		private Map<String, Users> getUser() {
			final URI uri = UriBuilder.fromPath(URL_WEB_SERVICE_PROJECT_ON_THE_SERVER).build();
			final WebResource webResource = Client.create().resource(URL_SERVER).path(uri.getPath());
			final ClientResponse clientResponse = webResource.get(ClientResponse.class);
			
			return clientResponse.getEntity(new GenericType<Map<String, Users>>() {});
		}
		
		public Users getUser(String name) {
			
			return getUser().get(name);
		}
		
	    private EntityTag entityTag(String name) {
	        return new EntityTag(String.valueOf(Objects.hashCode(name)));
	    }
	    
	    public Response responseBuiler(String name) {
	    	
	    	final EntityTag tag = entityTag(name);
	    	final ResponseBuilder responseBuilder = request.evaluatePreconditions(tag);
	    	
	        if (responseBuilder != null)
	            return responseBuilder.build();
	        
	        return Response.ok(name).tag(tag).build();
	    }
	    
	    public boolean authenticate() {
	        
	    	final String header = httpHeaders.getRequestHeader("authorization").get(0).substring("Basic ".length());
	    	final String[] credetials = new String(Base64.base64Decode(header)).split(":");
	    	final String login = credetials[0];
	    	final String password = credetials[1];
	    	
	    	return (StringUtils.isNotBlank(StringUtils.trim(login)) && StringUtils.isNotBlank(StringUtils.trim(password)) ? true : false);
	    }
	    
	    public static Set<String> getRequestHeaders(@Context HttpHeaders httpHeaders) {
	    	Set<String> requestHeaders = null;
			
			if (httpHeaders != null) {
				final MultivaluedMap<String, String> multivaluedMap = httpHeaders.getRequestHeaders();
				requestHeaders = multivaluedMap.keySet();
			}
			
			return requestHeaders;
		}
	    
	    public static Set<String> getCookies(@Context HttpHeaders httpHeaders) {
	    	Set<String> cookies = null;
			
			if (httpHeaders != null) {
				final Map<String, Cookie> mapCookies = httpHeaders.getCookies();
				cookies = mapCookies.keySet();
			}
			
			return cookies;
		}
	    
	    public static List<PathSegment> getPathSegments(@Context UriInfo uriInfo, @PathParam(NAME) String name) {
	    	List<PathSegment> pathSegments = null;
			
			if (uriInfo != null)
				pathSegments = uriInfo.getPathSegments();
			
			return pathSegments;
		}
	    
	    public static Set<String> getPathParameters(@Context UriInfo uriInfo, @PathParam(NAME) String name) {
	    	Set<String> pathParameters = null;
			
			if (uriInfo != null) {
				final MultivaluedMap<String, String> multivaluedMap = uriInfo.getPathParameters();
				pathParameters = multivaluedMap.keySet();
			}
			
			return pathParameters;
		}
	    
	    public static Set<String> getQueryParameters(@Context UriInfo uriInfo, @PathParam(NAME) String name) {
	    	Set<String> queryParameters = null;
			
			if (uriInfo != null) {
				final MultivaluedMap<String, String> multivaluedMap = uriInfo.getQueryParameters();
				queryParameters = multivaluedMap.keySet();
			}
			
			return queryParameters;
		}
	    
	    public static Long getSequenceNextValFromDual() {
			
	    	final Random random = new Random();
	    	Long userid = random.nextLong();
	    	
	    	return userid;
		}
}