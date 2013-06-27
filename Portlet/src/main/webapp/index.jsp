<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>
<%@ page import="javax.portlet.PortletPreferences" %>

<portlet:defineObjects />

<%
	PortletPreferences prefs = renderRequest.getPreferences();
	String hello = (String) prefs.getValue("greeting", "Hello World Portlet ! Welcome to our portal.");
%>

<p><%=hello%></p>
<portlet:renderURL var="edit">
	<portlet:param name="jspPage" value="/edit.jsp" />
</portlet:renderURL>
<p>
	<a href="<%=edit%>">Edit greeting</a>
</p>