/*
 * WapAjaxAuthInterceptor.java
 * Copyright 2021 Qunhe Tech, all rights reserved.
 * Qunhe PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */
package com.followba.store.web.interceptor;


import com.followba.store.common.constent.CodeEnum;
import com.followba.store.common.resp.Out;
import com.alibaba.fastjson2.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author 祥霸
 * @since 1.0.0
 */
public class WapAjaxAuthInterceptor extends WapAuthenticationInterceptor {


	@Override
	protected void doNoLogin(HttpServletRequest request, HttpServletResponse response) {

		try {
			response.setContentType("application/json; charset=utf-8");
			response.getWriter().print(JSON.toJSONString(Out.build(CodeEnum.NO_LOGIN)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doNoPermission(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			response.setContentType("application/json; charset=utf-8");
			response.getWriter().print(JSON.toJSONString(Out.build(CodeEnum.NO_PERMISSION)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
