package com.gabon.info.server;

import static com.gabon.info.util.Constants.LOCALHOST;
import static com.gabon.info.util.Constants.PORT;
import static com.gabon.info.util.Constants.PROJECT;
import static com.gabon.info.util.Constants.URI_PACKAGE;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;


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

public class EmbeddedServer {

	public static HttpServer startServer(URI uri) throws IOException {
		
		// Port can be pulled from an environmental variable
		System.out.println("Starting grizzly...");
		final ResourceConfig resourceConfig = new PackagesResourceConfig(URI_PACKAGE);
			 
		// Classes under resources and its subpackages will be included when routing requests
		return GrizzlyServerFactory.createHttpServer(uri, resourceConfig);
	}

	public static void main(String[] args) throws IOException {
		final URI uri = UriBuilder.fromUri(LOCALHOST).port(PORT).build();
		final HttpServer httpServer = startServer(uri);
		
		System.out.println(String.format("Jersey app started with WADL available at " + "%sapplication.wadl\nTry out %s " + PROJECT + "\nHit enter to stop it...", uri, uri));
		System.in.read();
		httpServer.stop();
		
		System.out.println("Stopping grizzly...");
	}
}