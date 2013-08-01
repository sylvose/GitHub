<%@ page isErrorPage="true" contentType="text/html" language="java" autoFlush="true" isThreadSafe="false" session="false" pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.*, java.util.*, java.lang.Exception, java.io.PrintWriter" %>

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
		 <p>
			   Codes du Protocole HTTP<br />
			   Code : 500 - Message : Internal Server Error       - Signification : Une erreur est survenue sur le serveur pendant le traitement de la requete.<br /> 
			   Code : 505 - Message : HTTP Version Not Supported  - Signification : Le serveur ne supporte pas la version HTTP indiquee par le client.<br /> 
		 <p>
		   
		Message erreur Jersey Web Application :
		<%=exception.getMessage()%>
		
		StackTrace :
		<%
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			exception.printStackTrace(printWriter);
			out.println(stringWriter);
			printWriter.close();
			stringWriter.close();
		%>
	</body>
</html>