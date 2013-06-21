<%@ page isErrorPage="false" contentType="text/html" language="java" autoFlush="true" isThreadSafe="false" session="false" pageEncoding="ISO-8859-1"%>
<%@ page import="java.net.InetAddress, java.io.File, org.apache.commons.lang.StringUtils, javax.servlet.jsp.JspFactory" %>

		<%
			final String path = request.getContextPath();
			final String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Tableau de données du Serveur / Dashboard of the Server</title>
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
		<h1>
			Welcome - The JVM and Server Version
		</h1>

		<h2>
			Java VM
		</h2>
		<p>
			Java VM Vendor :
			<b><%=System.getProperty("java.vm.vendor")%></B>
		<p>
			Java VM Name :
			<b><%=System.getProperty("java.vm.name")%></B>
		<p>
			Java VM Version :
			<b><%=System.getProperty("java.vm.version")%></B>
		<h2>
			Java RE
		</h2>
		<p>
			Java Home :
			<b><%=System.getProperty("java.home")%></B>
		<p>
			Java Vendor :
			<b><%=System.getProperty("java.vendor")%></B>
		<p>
			Java Version :
			<b><%=System.getProperty("java.version")%></B>
		<h2>
			Platform
		</h2>
		<p>
			OS Name :
			<b><%=System.getProperty("os.name")%></B>
		<p>
			OS Arch :
			<b><%=System.getProperty("os.arch")%></B>
		<p>
			OS Version :
			<b><%=System.getProperty("os.version")%></B>

			<%
				final String IBM = "IBM";
				final String PROFILES = "profiles";

				final String Apache = "Apache";
				final String WEBAPPS = "webapps";

				String home = application.getRealPath(request.getContextPath());

				final String container = application.getServerInfo();
				if (container == null) {
					throw new NullPointerException("Unexpected value " + container
							+ " was expected.");

				} else {
					if (home == null || StringUtils.isBlank(home))
						throw new IllegalArgumentException(
								"Unexpected value in JSP index.jsp : " + home
										+ " was expected.");

					if (container.startsWith(IBM)) {
						if (!StringUtils.contains(home, PROFILES))
							throw new IllegalArgumentException(
									"Unexpected value in JSP index.jsp : " + home
											+ " was expected.");

						home = StringUtils.removeEnd(
								StringUtils.substringBefore(home, PROFILES),
								File.separator);
					} else if (container.startsWith(Apache)) {
						if (!StringUtils.contains(home, WEBAPPS))
							throw new IllegalArgumentException(
									"Unexpected value in JSP index.jsp : " + home
											+ " was expected.");

						home = StringUtils.removeEnd(
								StringUtils.substringBefore(home, WEBAPPS),
								File.separator);
					}
				}
			%>
		
		<h2>
			Servlet Engine
		</h2>
		<p>
			Application Server Home :
			<b><%=home%></B>
		<p>
			Application Base Path :
			<b><%=basePath%></B>
		<p>
		<p>
			Application Server Info :
			<b><%=application.getServerInfo()%></B>
		<p>
			Application Major Version :
			<b><%=application.getMajorVersion()%></B>
		<p>
			Application Minor Version :
			<b><%=application.getMinorVersion()%></B>
		<p>
			Application Host Name :
			<b><%=request.getServerName()%></B>
		<p>
			Application IP Address :
			<b><%=InetAddress.getLocalHost().getHostAddress()%></B>
		<p>
			Application Title :
			<b><%=application.getServletContextName()%></B>
		<p>
			Application Context :
			<b><%=request.getContextPath()%></B>
		<p>
			Application Name :
			<b><%=StringUtils.removeStart(request.getContextPath(), "/")%></B>
		<p>
			Application JSP version :
			<b><%=JspFactory.getDefaultFactory().getEngineInfo().getSpecificationVersion()%></B>
		<p>
		
	</body>
</html>