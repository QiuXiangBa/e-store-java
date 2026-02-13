/*
 * ThreadLocalInterceptor.java
 * Copyright 2021 Qunhe Tech, all rights reserved.
 * Qunhe PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.followba.store.web.interceptor;

import com.followba.store.common.context.IToolRequest;
import com.followba.store.common.context.RequestContext;
import com.followba.store.web.constent.DistCommonConstant;
import com.followba.store.web.util.RequestAttrUtil;
import com.followba.store.web.util.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 祥霸
 * @since 1.0.0
 */
public class ThreadLocalInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        DistCommonConstant.requestTL.set(request);
        DistCommonConstant.responseTL.set(response);
        // 将公共参数封装成ZeusRequest
        IToolRequest zeusRequest = getZeusRequest(request);
        RequestContext.bindRequest(zeusRequest);
        return true;
    }

    /**
     * 将公共参数封装成ZeusRequest
     */
    private IToolRequest getZeusRequest(HttpServletRequest request) {

        IToolRequest zeusRequest = new IToolRequest();

        zeusRequest.setToken(RequestAttrUtil.getTokenFromRequest());
        zeusRequest.setUserId(RequestAttrUtil.getUserIdFromRequest());
        zeusRequest.setIp(RequestUtil.getLastIp(request));
        zeusRequest.setCurrentUrl(request.getRequestURI());
        zeusRequest.setUserAgent(request.getHeader("User-Agent"));
        zeusRequest.setReferUrl(request.getHeader("Referer"));
        return zeusRequest;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView){
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        RequestContext.unbindRequest();
    }

}
