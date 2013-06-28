package com.gabon.info.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

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
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Client implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
	  final Label pleaseLogIn = new Label("Please Log In");
	  
	  final Grid grid = new Grid(3, 2);
	  
	  final Label loginPrompt = new Label("Login");
	  final TextBox textBox = new TextBox();
	  
	  final Label passwordPrompt = new Label("Password");
	  final PasswordTextBox passwordTextbox = new PasswordTextBox();
	  
	  final Button buttonLogin = new Button("Login");
	  final Button buttonCancel = new Button("Cancel");
	  
	  grid.setWidget(0, 0, loginPrompt);
	  grid.setWidget(0, 1, textBox);
	  
	  grid.setWidget(1, 0, passwordPrompt);
	  grid.setWidget(1, 1, passwordTextbox);
	  
	  grid.setWidget(2, 0, buttonLogin);
	  grid.setWidget(2, 1, buttonCancel);
	  
	  RootPanel.get().add(pleaseLogIn);
	  RootPanel.get().add(grid);
	}
}