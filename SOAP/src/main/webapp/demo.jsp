<%@ page isErrorPage="false" contentType="text/html" language="java" autoFlush="true" isThreadSafe="false" session="false" pageEncoding="ISO-8859-1"%>

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
		<h2><a href="./img/welcome.gif">Démonstration et résultats - RESTful Web Service by Sylvose ALLOGO</a></h2><br /><br />
		
		    <p>
		        <a href="./img/add.gif">Création d'un utilisateur</a><br />
		        <a href="./img/update.gif">Mise à jour d'un utilisateur</a><br />
		        <a href="./img/delete.gif">Supression d'un utilisateur</a><br />
		        <a href="./img/find.gif">Recherche d'un utilisateur</a><br />
		        <a href="./img/get.gif">Recherche de tous les utilisateurs</a><br /><br />
		         
		    	<a href="./img/diagramme-class-Restful-source.png">Diagramme de classe : source</a><br /><br />
		    	
		    	<a href="./img/diagramme-class-Restful-test.png">A venir ... Diagramme de classe : test</a><br /><br />
		    	
				<a href="./img/mcd.gif">A venir ... MCD - Modèle Conceptuel des Données</a><br />
				<a href="./img/mpd.gif">A venir ... MPD - Modèle physique des données</a><br />
				<a href="./img/ddl.gif">A venir ... DDL - Data Definition Language</a><br /><br />
				 
				<a href="./img/user.gif">Table User</a><br />
				<a href="./img/project.gif">Table Project</a><br />
				<a href="./img/build.gif">Table Build</a><br />
				<a href="./img/department.gif">Table Department</a><br />
				<a href="./img/role.gif">Table Role</a><br />
				<a href="./img/hability.gif">Table Hability</a><br />
				<a href="./img/workstation.gif">Table Workstation</a><br /><br />
				
				<a href="./img/wadl.gif">WADL - Web Application Description Language : restful-1.0.wadl</a><br />
		    <p>
		    
			<a href="http://www.linkedin.com/pub/allogo-sylvose/23/289/529">Visitez mon profil Linked in : http://www.linkedin.com/pub/allogo-sylvose/23/289/529</a>
  </body>
</html>