<%--
  Copyright (c) 2013 by Sylvose ALLOGO
  All rights reserved.
  
  You may study, use, modify, and distribute this
  software for any purpose provided that this
  copyright notice appears in all copies.
  
  This software is provided without warranty
  either expressed or implied.
--%>

<%@ page isErrorPage="false" contentType="text/html" language="java" autoFlush="true" isThreadSafe="false" session="false" pageEncoding="ISO-8859-1"%>
<%@ page import="com.gabon.info.model.users.User,com.gabon.info.model.roles.Role,com.gabon.info.util.Constants,java.lang.String,java.util.Set,java.util.HashSet,java.util.Collections,java.util.Arrays" %>

		<%
			final String path = request.getContextPath();
			final String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Rest - RESTful Web Services</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="ALLOGO,Sylvose,Rest,RESTful,Web,Service">
	<meta http-equiv="description" content="Jersey RESTful Web Service by Sylvose ALLOGO">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  <body>
		<h2>Jersey RESTful Web Service by Sylvose ALLOGO</h2>
		
		<%
					final User user1 = Constants.FIRST_USER;
									final User user2 = Constants.SECOND_USER;
									
									final String name3 = "EDOU";
									final String email3 = "jacque@gmail.com";
									final Set<Role> roles3 = Collections.unmodifiableSet(new HashSet<Role>(Arrays.asList(new Role [] { new Role("manager"), new Role("architect") })));
									final User user3 = new User(name3, email3, roles3);
									
								    final String name4 = "George";
									final String email4 = "guy@msn.com";
									final Set<Role> roles4 = Collections.unmodifiableSet(new HashSet<Role>(Arrays.asList(new Role [] { new Role("analyst"), new Role("scrumaster") })));
									final User user4 = new User(name4, email4, roles4);
									
									final String name5 = null;
									final String email5 = "diane@yahoo.fr.com";
									final Set<Role> roles5 = Collections.unmodifiableSet(new HashSet<Role>(Arrays.asList(new Role [] { new Role("driver"), new Role("tester") })));
									final User user5 = new User(null, email5, roles5);
									
									final String name6 = "EDOU";
									final String email6 = null;
									final Set<Role> roles6 = Collections.unmodifiableSet(new HashSet<Role>(Arrays.asList(new Role [] { new Role("manager"), new Role("analyst") })));
									final User user6 = new User(name6, email6, roles6);
									
									final String name7 = "George";
									final String email7 = "george@cogeco.com";
									final Set<Role> roles7 = null;
									final User user7 = new User(name7, email7, roles7);
									
									final String name8 = null;
									final String email8 = null;
									final Set<Role> roles8 = null;
									final User user8 = null;
									
									final String name9 = "ALLOGO";
									final String email9 = "jacque@gmail.com";
									final Set<Role> roles9 = Collections.unmodifiableSet(new HashSet<Role>(Arrays.asList(new Role [] { new Role("manager"), new Role("architect") })));
									final User user9 = new User(name9, email9, roles9);
									
									final String name10 = "Sylvose";
									final String email10 = "guy@msn.com";
									final Set<Role> roles10 = Collections.unmodifiableSet(new HashSet<Role>(Arrays.asList(new Role [] { new Role("analyst"), new Role("scrumaster") })));
									final User user10 = new User(name10, email10, roles10);
									
									final String name11 = null;
									final String email11 = "diane@yahoo.fr.com";
									final Set<Role> roles11 = Collections.unmodifiableSet(new HashSet<Role>(Arrays.asList(new Role [] { new Role("driver"), new Role("tester") })));
									final User user11 = new User(null, email11, roles11);
									
									final String name12 = "ALLOGO";
									final String email12 = null;
									final Set<Role> roles12 = Collections.unmodifiableSet(new HashSet<Role>(Arrays.asList(new Role [] { new Role("manager"), new Role("analyst") })));
									final User user12 = new User(name12, email12, roles12);
									
									final String name13 = "Sylvose";
									final String email13 = "george@cogeco.com";
									final Set<Role> roles13 = null;
									final User user13 = new User(name13, email13, roles13);
									
									final String name14 = null;
									final String email14 = null;
									final Set<Role> roles14 = null;
									final User user14 = null;
				%>
		<p>
			WADL : Web Application Description Language<br />
			Start your jersey REST Restful, GET the restful-1.0-SNAPSHOT.wadl (e.g. via curl http://localhost:8080/Restful/restful-1.0-SNAPSHOT.wadl)<br />
			<a href="./restful-1.0-SNAPSHOT.wadl">restful-1.0-SNAPSHOT.wadl                                                            : http://localhost:8080/Restful/restful-1.0-SNAPSHOT.wadl</a><br /><br /><br />
			
			addUser avec le protocole POST pour la la mise à jour d'un utilisateur<br />
			Création d'un utilisateur cote Client<br />
			<a href="rest/userService/add/client?name=PATRICK&email=jacque@gmail.com&roles=architect,manager">public Response addUser(@QueryParam("name") String name, @QueryParam("email") String email, @QueryParam("roles") Set&lt;Role&gt; roles) : http://localhost:8080/Restful/rest/userService/add/client?name=PATRICK&email=jacque@gmail.com&roles=architect,manager</a><br />
			<a href="rest/userService/add/client?name=ALLOGO&email=jacque@gmail.com&roles=architect,manager">public Response addUser(@QueryParam("name") String name, @QueryParam("email") String email, @QueryParam("roles") Set&lt;Role&gt; roles) : http://localhost:8080/Restful/rest/userService/add/client?name=ALLOGO&email=jacque@gmail.com&roles=architect,manager</a><br />
      		<a href="rest/userService/add/client?name=null&email=jacque@gmail.com&roles=architect,manager">public Response addUser(@QueryParam("name") String name, @QueryParam("email") String email, @QueryParam("roles") Set&lt;Role&gt; roles) : http://localhost:8080/Restful/rest/userService/add/client?name=null&email=jacque@gmail.com&roles=architect,manager</a><br />
			<a href="rest/userService/add/client?name=PATRICK&email=null&roles=architect,manager">public Response addUser(@QueryParam("name") String name, @QueryParam("email") String email, @QueryParam("roles") Set&lt;Role&gt; roles) : http://localhost:8080/Restful/rest/userService/add/client?name=PATRICK&email=null&roles=architect,manager</a><br />
			<a href="rest/userService/add/client?name=PATRICK&email=jacque@gmail.com&roles=null">public Response addUser(@QueryParam("name") String name, @QueryParam("email") String email, @QueryParam("roles") Set&lt;Role&gt; roles) : http://localhost:8080/Restful/rest/userService/udate?name=PATRICK&email=jacque@gmail.com&roles=null</a><br />
			<a href="rest/userService/add/client?name=PATRICK&email=null&roles=null">public Response addUser(@QueryParam("name") String name, @QueryParam("email") String email, @QueryParam("roles") Set&lt;Role&gt; roles) : http://localhost:8080/Restful/rest/userService/add/client?name=PATRICK&email=null&roles=null</a><br />
			<a href="rest/userService/add/client?name=<%=name3%>&email=<%=email3%>&roles=<%=roles3%>">public Response addUser(@QueryParam("name") String name, @QueryParam("email") String email, @QueryParam("roles") Set&lt;Role&gt; roles) : http://localhost:8080/Restful/rest/userService/add/client?name=<%=name3%>&email=<%=email3%>&roles=<%=roles3%></a><br /><br />
			Création d'un utilisateur cote Serveur<br />
			<a href="rest/userService/add/server?user=<%=user1%>">public Response addUser(@QueryParam("user") User addtUser)           : http://localhost:8080/Restful/rest/userService/add/server?user=user1</a><br />
			<a href="rest/userService/add/server?user=<%=user2%>">public Response addUser(@QueryParam("user") User addtUser)           : http://localhost:8080/Restful/rest/userService/add/server?user=user2</a><br />
			<a href="rest/userService/add/server?user=<%=user3%>">public Response addUser(@QueryParam("user") User addtUser)           : http://localhost:8080/Restful/rest/userService/add/server?user=user3</a><br />
			<a href="rest/userService/add/server?user=<%=user4%>">public Response addUser(@QueryParam("user") User addtUser)           : http://localhost:8080/Restful/rest/userService/add/server?user=user4</a><br />
			<a href="rest/userService/add/server?user=<%=user5%>">public Response addUser(@QueryParam("user") User addtUser)           : http://localhost:8080/Restful/rest/userService/add/server?user=user5</a><br />
			<a href="rest/userService/add/server?user=<%=user6%>">public Response addUser(@QueryParam("user") User addtUser)           : http://localhost:8080/Restful/rest/userService/add/server?user=user6</a><br />
			<a href="rest/userService/add/server?user=<%=user7%>">public Response addUser(@QueryParam("user") User addtUser)           : http://localhost:8080/Restful/rest/userService/add/server?user=user7</a><br />
      		<a href="rest/userService/add/server?user=<%=user8%>">public Response addUser(@QueryParam("user") User addtUser)           : http://localhost:8080/Restful/rest/userService/add/server?user=user8</a><br /><br /><br />
			
			updateUser avec le protocole PUT pour la la mise à jour d'un utilisateur<br />
			Mise à jour d'un utilisateur cote Client<br />
			<a href="rest/userService/update/client?name=ALLOGO&email=jacque@gmail.com&roles=architect,manager">public Response updateUser(@QueryParam("name") String name, @QueryParam("email") String email, @QueryParam("roles") Set&lt;Role&gt; roles) : http://localhost:8080/Restful/rest/userService/update/client?name=ALLOGO&email=jacque@gmail.com&roles=architect,manager</a><br />
			<a href="rest/userService/update/client?name=null&email=jacque@gmail.com&roles=architect,manager">public Response updateUser(@QueryParam("name") String name, @QueryParam("email") String email, @QueryParam("roles") Set&lt;Role&gt; roles) : http://localhost:8080/Restful/rest/userService/update/client?name=null&email=jacque@gmail.com&roles=architect,manager</a><br />
			<a href="rest/userService/update/client?name=ALLOGO&email=null&roles=architect,manager">public Response updateUser(@QueryParam("name") String name, @QueryParam("email") String email, @QueryParam("roles") Set&lt;Role&gt; roles) : http://localhost:8080/Restful/rest/userService/update/client?name=ALLOGO&email=null&roles=architect,manager</a><br />
			<a href="rest/userService/update/client?name=ALLOGO&email=jacque@gmail.com&roles=null">public Response updateUser(@QueryParam("name") String name, @QueryParam("email") String email, @QueryParam("roles") Set&lt;Role&gt; roles) : http://localhost:8080/Restful/rest/userService/udate?name=ALLOGO&email=jacque@gmail.com&roles=null</a><br />
			<a href="rest/userService/update/client?name=ALLOGO&email=null&roles=null">public Response updateUser(@QueryParam("name") String name, @QueryParam("email") String email, @QueryParam("roles") Set&lt;Role&gt; roles) : http://localhost:8080/Restful/rest/userService/update/client?name=ALLOGO&email=null&roles=null</a><br />
			<a href="rest/userService/update/client?name=<%=name3%>&email=<%=email3%>&roles=<%=roles3%>">public Response updateUser(@QueryParam("name") String name, @QueryParam("email") String email, @QueryParam("roles") Set&lt;Role&gt; roles) : http://localhost:8080/Restful/rest/userService/update/client?name=<%=name3%>&email=<%=email3%>&roles=<%=roles3%></a><br /><br />
			Mise à jour d'un utilisateur cote Serveur<br />
			<a href="rest/userService/update/server?user=<%=user1%>">public Response updateUser(@QueryParam("user") User updatetUser)  : http://localhost:8080/Restful/rest/userService/update/server?user=user1</a><br />
			<a href="rest/userService/update/server?user=<%=user2%>">public Response updateUser(@QueryParam("user") User updatetUser)  : http://localhost:8080/Restful/rest/userService/update/server?user=user2</a><br />
			<a href="rest/userService/update/server?user=<%=user9%>">public Response updateUser(@QueryParam("user") User updatetUser)  : http://localhost:8080/Restful/rest/userService/update/server?user=user9</a><br />
			<a href="rest/userService/update/server?user=<%=user10%>">public Response updateUser(@QueryParam("user") User updatetUser) : http://localhost:8080/Restful/rest/userService/update/server?user=user10</a><br />
			<a href="rest/userService/update/server?user=<%=user11%>">public Response updateUser(@QueryParam("user") User updatetUser) : http://localhost:8080/Restful/rest/userService/update/server?user=user11</a><br />
			<a href="rest/userService/update/server?user=<%=user12%>">public Response updateUser(@QueryParam("user") User updatetUser) : http://localhost:8080/Restful/rest/userService/update/server?user=user12</a><br />
			<a href="rest/userService/update/server?user=<%=user13%>">public Response updateUser(@QueryParam("user") User updatetUser) : http://localhost:8080/Restful/rest/userService/update/server?user=user13</a><br />
			<a href="rest/userService/update/server?user=<%=user14%>">public Response updateUser(@QueryParam("user") User updatetUser) : http://localhost:8080/Restful/rest/userService/update/server?user=user14</a><br /><br /><br />
			
			deleteUser avec le protocole DELETE pour la supression d'un utilisateur<br />
			<a href="rest/userService/delete?name=ALLOGO">public Response deleteUser(@QueryParam("name") String name)                  : http://localhost:8080/Restful/rest/userService/delete?name=ALLOGO</a><br />
			<a href="rest/userService/delete?name=Patrick">public Response deleteUser(@QueryParam("name") String name)                 : http://localhost:8080/Restful/rest/userService/delete?name=Patrick</a><br />
			<a href="rest/userService/delete?name=Sylvose">public Response deleteUser(@QueryParam("name") String name)                 : http://localhost:8080/Restful/rest/userService/delete?name=Sylvose</a><br /><br /><br />
			
			findUser avec le protocole GET pour la lecture d'un utilisateur<br />
			<a href="rest/userService/find?name=ALLOGO">public Response findUser(@QueryParam("name") String name)                      : http://localhost:8080/Restful/rest/userService/find?name=ALLOGO</a><br />
			<a href="rest/userService/find?name=Sylvose">public Response findUser(@QueryParam("name") String name)                     : http://localhost:8080/Restful/rest/userService/find?name=Sylvose</a><br />
			<a href="rest/userService/find?name=Patrick">public Response findUser(@QueryParam("name") String name)                     : http://localhost:8080/Restful/rest/userService/find?name=Patrick</a><br />
			<a href="rest/userService/find?name=Name User 4">public Response findUser(@QueryParam("name") String name)                 : http://localhost:8080/Restful/rest/userService/find?name=Name User 4</a><br /><br /><br />
			
			getUser avec le protocole GET pour la lecture de tous les utilisateurs<br /><br /><br />
			<a href="rest/userService/get">public Response getUser()                                                   : http://localhost:8080/Restful/rest/userService/get</a>
		<p>
			Visit the <a href="http://jersey.java.net">Project Jersey website</a> for more information on Jersey !<br />
			<a href="./server.jsp">Tableau de données du Serveur / Dashboard of the Server : Information about the server</a>
  </body>
</html>