package com.helloweenvsfei.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestHttpServletRequest
 */
public class TestHttpServletRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestHttpServletRequest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> infomap = new HashMap<String, Object>();
		 
		infomap.put("getAuthType",request.getAuthType());
		infomap.put("getCharacterEncoding",request.getCharacterEncoding());
		infomap.put("getContentLength",request.getContentLength());
		infomap.put("getContextPath",request.getContextPath());
		//infomap.put("getCookies",request.getCookies());
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
//			System.out.println(key+" = "+infomap.get(key));
		}
		System.out.println(Thread.currentThread().getId());
		response.getWriter().write(infomap.toString());
	}

}
