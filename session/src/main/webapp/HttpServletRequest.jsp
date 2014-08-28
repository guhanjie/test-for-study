<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="java.util.HashMap"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
	Map<String, Object> infomap = new HashMap<String, Object>();
	 
	infomap.put("getAuthType",request.getAuthType());
	infomap.put("getCharacterEncoding",request.getCharacterEncoding());
	infomap.put("getContentLength",request.getContentLength());
	infomap.put("getContextPath",request.getContextPath());
	infomap.put("getCookies",request.getCookies());
	infomap.put("getHeaderNames",request.getHeaderNames());
	infomap.put("getLocalAddr",request.getLocalAddr());
	infomap.put("getLocalName",request.getLocalName());
	infomap.put("getLocalPort",request.getLocalPort());
	infomap.put("getMethod",request.getMethod());
	infomap.put("getParameterMap",request.getParameterMap());
	infomap.put("getPathInfo",request.getPathInfo());
	infomap.put("getPathTranslated",request.getPathTranslated());
	infomap.put("getProtocol",request.getProtocol());
	infomap.put("getQueryString",request.getQueryString());
	infomap.put("getRemoteAddr",request.getRemoteAddr());
	infomap.put("getRemoteHost",request.getRemoteHost());
	infomap.put("getRemotePort",request.getRemotePort());
	infomap.put("getRemoteUser",request.getRemoteUser());
	infomap.put("getRequestedSessionId",request.getRequestedSessionId());
	infomap.put("getRequestURI",request.getRequestURI());
	infomap.put("getRequestURL",request.getRequestURL());
	infomap.put("getScheme",request.getScheme());
	infomap.put("getServerName",request.getServerName());
	infomap.put("getServletPath",request.getServletPath());
	infomap.put("getUserPrincipal",request.getUserPrincipal());

	Set<String> keySet = infomap.keySet();
	for(String key : keySet) {
		out.println(key+" = "+infomap.get(key)+"<br/>");
	}
%>

</body>
</html>