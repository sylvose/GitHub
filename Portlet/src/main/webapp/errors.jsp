<%--
  Copyright (c) 2013 by Sylvose ALLOGO
  All rights reserved.
  
  You may study, use, modify, and distribute this
  software for any purpose provided that this
  copyright notice appears in all copies.
  
  This software is provided without warranty
  either expressed or implied.
--%>

<%@ page isErrorPage="true" import="java.io.*" contentType="text/plain"%>
<%@ page errorPage="./errors.jsp" import="java.io.*" import="java.util.*" %>
<%@ page import="java.lang.Exception" %>
<%@ page import="java.io.PrintWriter" %>

Message :
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
