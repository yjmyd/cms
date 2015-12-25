package org.liudan.cms.web;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.liudan.cms.model.CmsException;
import org.liudan.cms.model.User;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthInterceptor extends HandlerInterceptorAdapter {
	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		HandlerMethod hm = (HandlerMethod)handler;
		User user = (User)session.getAttribute("loginUser");
		if(user==null) {
			response.sendRedirect(request.getContextPath()+"/login");
		} else {
			boolean isAdmin = (Boolean)session.getAttribute("isAdmin");
			if(!isAdmin) {
				//不是超级管理人员，就需要判断是否有权限访问某些功能
				Set<String> actions = (Set<String>)session.getAttribute("allActions");
				String aname = hm.getBean().getClass().getName()+"."+hm.getMethod().getName();
				if(!actions.contains(aname)) throw new CmsException("没有权限访问该功能");
			}
		}
		return super.preHandle(request, response, handler);
	}
}
