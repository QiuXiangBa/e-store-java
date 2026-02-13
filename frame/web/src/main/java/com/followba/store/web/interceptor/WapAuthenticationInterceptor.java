/*
 * WapAuthenticationInterceptor.java
 * Copyright 2021 Qunhe Tech, all rights reserved.
 * Qunhe PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */
package com.followba.store.web.interceptor;

import cn.hutool.core.util.StrUtil;
import com.followba.store.web.util.RequestAttrUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 祥霸
 * @since 1.0.0
 */
public abstract class WapAuthenticationInterceptor implements HandlerInterceptor {


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 未登录
		String userId = RequestAttrUtil.getUserIdFromRequest();
		if (StrUtil.isBlank(userId)) {
			doNoLogin(request,response);
			return false;
		}

		doLogin(request, response);
		return true;
	}

	/**
	 * 未登录处理 子类实现该方法来指定未登录处理方案 如返回错误码，或者重定向到登录页面
	 *
	 * @param request
	 * @param response
	 */
	protected abstract void doNoLogin(HttpServletRequest request, HttpServletResponse response) throws Exception;

	/**
	 * 无操作权限
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	protected abstract void doNoPermission(HttpServletRequest request, HttpServletResponse response) throws Exception;

	/**
	 * 已登录处理
	 *
	 * @param request
	 * @param response
	 */
	protected void doLogin(HttpServletRequest request, HttpServletResponse response) {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
