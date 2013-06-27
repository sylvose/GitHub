package com.gabon.info.integ;

import static com.gabon.info.util.Constants.APPLICATION_CONTEXT_ROOT;
import static com.gabon.info.util.Constants.BEAN_JPA_TRANSACTION_MANAGER;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.gabon.info.util.Constants;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.test.framework.spi.container.TestContainerFactory;
import com.sun.jersey.test.framework.spi.container.http.HTTPContainerFactory;

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

@Transactional 
@RunWith(JUnit4.class)
@ContextConfiguration(locations = { APPLICATION_CONTEXT_ROOT }) 
@TransactionConfiguration(transactionManager = BEAN_JPA_TRANSACTION_MANAGER, defaultRollback = true)
public abstract class AbstractIntegTest extends JerseyTest implements Constants {
	
	private static final long serialVersionUID = 7053071465704447515L;
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	
	protected static final ApplicationContext applicationContext = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_ROOT);
	protected static final AppDescriptor appDescriptor = new WebAppDescriptor.Builder(URI_PACKAGE).build();
	
	
	protected Client client;
	protected ClientResponse clientResponse ;
	protected WebResource webResource;
	protected TestContainerFactory testContainerFactory;
	
	
	
	public AbstractIntegTest() {
		super(appDescriptor);
		
		this.testContainerFactory = new HTTPContainerFactory();
		setTestContainerFactory(this.testContainerFactory);
	}
	
	
	
	@Before
	/**
	 * Purpose:
	 * Prerequisites:
	 * Description: 
	 * Expected result: 
	 * @TEST_STATUS	        Ready
	 * @DESIGNER            Sylvose ALLOGO
	 * @TEST_TYPE	        Unit testing
	 * @TEST_COVERAGE       
	 * @TEST_DESTINATION    
	 * 
	 * @throws java.lang.Exception
	 */
	public void setUp() throws Exception {
		super.setUp();
		
		assertNotNull(applicationContext);	
		assertTrue(applicationContext instanceof ApplicationContext);
		
		assertNotNull(appDescriptor);	
		assertTrue(appDescriptor instanceof AppDescriptor);
		
		final ClientConfig clientConfig = new DefaultClientConfig();
		this.client = Client.create(clientConfig);
		
		assertNotNull(this.client);	
		assertTrue(this.client instanceof Client);
	}
	
	/**
	 * Test method for {@link com.gabon.info}.
	 * 
	 * 
	 */
	// @Ignore("Not Ready, to be changed")
	@Test
	/**
	 * Purpose:				
	 * 						
	 * Prerequisites:       
	 * Description:         
	 * Expected result:     Test OK
	 * @TEST_STATUS	        Ready
	 * @DESIGNER            Sylvose ALLOGO
	 * @TEST_TYPE	        Unit testing
	 * @TEST_COVERAGE       
	 * @TEST_DESTINATION    
	 */
	public void testInitialization() {
		
		assertNull(this.clientResponse);	
		assertFalse(this.clientResponse instanceof ClientResponse);
		
		assertNull(this.webResource);	
		assertFalse(this.webResource instanceof WebResource);
	}
	
	@After
	/**
	 * Purpose:
	 * Prerequisites:
	 * Description: 
	 * Expected result: 
	 * @TEST_STATUS	        Ready
	 * @DESIGNER            Sylvose ALLOGO
	 * @TEST_TYPE	        Unit testing
	 * @TEST_COVERAGE       
	 * @TEST_DESTINATION    
	 * 
	 * @throws java.lang.Exception
	 */
	public void tearDown() throws Exception {
		super.tearDown();
		
		this.clientResponse = null;
		this.webResource = null;
	}
}