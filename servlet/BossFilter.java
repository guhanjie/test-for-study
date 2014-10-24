package com.glodon.paas.boss.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;
import org.springframework.stereotype.Component;

import com.glodon.paas.account.sdk.UserHolder;
import com.glodon.paas.boss.api.dto.UserResourceDto;
import com.glodon.paas.boss.api.service.BossRpcService;
import com.glodon.paas.boss.filter.cache.UserResourcesCache;
import com.glodon.paas.boss.filter.exception.AccessDeniedException;

/**
 * Boss Filter
 * @author Guhanjie on 2014-08-15 12:59:12
 */
@Component("bossFilter")
public class BossFilter implements Filter{
    private static Logger LOGGER = LoggerFactory.getLogger(BossFilter.class);
    
    @Autowired
	private BossRpcService bossRpcService;
    
    @Autowired
	private UserResourcesCache userResourcesCache;
	
	public List<UserResourceDto> loadUserResources(Long userId, String appContext) {
    	//读取缓存资源
    	LOGGER.debug("Start to access cache...");
		List<UserResourceDto> userResources = userResourcesCache.getUserResourcesFromCache(userId);
		//缓存未命中，RPC读数据库
		if (userResources == null) {
			LOGGER.warn("Cache not hited, start to access database by RPC...");
			// 得到用户的资源列表
			userResources = bossRpcService.getUserResource(userId, appContext);
			//置入缓存
			LOGGER.debug("put new element into Cache...");
			userResourcesCache.putUserResourcesInCache(userId, userResources);						
        }
    	
    	userResources = bossRpcService.getUserResource(userId, appContext);
        return userResources;
	}
    
    /**
	 * 验证权限
	 */
	public void decide(HttpServletRequest request) {
		List<UserResourceDto> resources = loadUserResources(UserHolder.getUser().getId(), request.getContextPath());
				
		//返回当前用户拥有的资源列表
		request.setAttribute("avaliableMenus", resources);
		//用户请求的URL（去除？后的字符串）
		String requestURL = request.getServletPath();
		//首页"/"放行
		if(requestURL.equals("/")) {
			return;
		}
				
		for(UserResourceDto res: resources){
			//验权
			String resURL = res.getResource();			
			if(resURL == null || resURL.length()==0) {
				continue;
			}
			if(resURL.charAt(0) != '/') {
				resURL = "/" + resURL;
			}
			RequestMatcher resURLMatcher = new AntPathRequestMatcher(resURL);
			if (resURLMatcher.matches(request)) {
				return;
			}
		}
		throw new AccessDeniedException("Access Denied by BossFilterSecurityIntercepter.");
	}
	
	@Override
    public void init(FilterConfig filterConfig) throws ServletException {
//    	LOGGER.info("init boss-filter...");
//    	LOGGER.info("start instantiation of filed[userResourcesCache]...");
//    	userResourcesCache = new UserResourcesCache();
//    	LOGGER.info("set the filed[userResourcesCache] successfully.");
    }
	
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
    	HttpServletRequest httpReq = (HttpServletRequest) request;
    	if(LOGGER.isDebugEnabled()){
    		requestDebug((HttpServletRequest) request);
    	}
    	//Start to validate access
    	try {
    		decide(httpReq);
    	}catch(AccessDeniedException e) {
    		LOGGER.info("Access for request:[{}] was denied", httpReq.getRequestURI());
    		response.setCharacterEncoding("UTF-8");
    		response.setContentType("text/html");
    		response.getWriter().println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
    		response.getWriter().println("<html><p><h3><center>对不起，你无权访问该页面!<p>请联系广联云运营后台管理员：<a href=\"mailto:glodon_admin@163.com\">glodon_admin@163.com</a></center></h3></div></html>");
    		return;
		}
    	chain.doFilter(request, response);
    	
    }        
    
    @Override
    public void destroy(){
    
    }
    
	void requestDebug(HttpServletRequest request){
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
	
		LOGGER.debug("requestInfo:\n{}", infomap.toString());
	}
}