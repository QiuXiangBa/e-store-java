/*
 * PreLoginInterceptor.java
 * Copyright 2021 Qunhe Tech, all rights reserved.
 * Qunhe PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.followba.store.web.interceptor;

import com.followba.store.web.constent.DistCommonConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 祥霸
 * @since 1.0.0
 */
public class PreLoginInterceptor extends TrackableInterceptor {

    @Override
    public boolean doPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        DistCommonConstant.requestTL.set(request);
        DistCommonConstant.responseTL.set(response);
        DistCommonConstant.httpsProtocolTL.set("https".equalsIgnoreCase(request.getHeader("X-Forwarded-Proto")));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        DistCommonConstant.requestTL.remove();
        DistCommonConstant.responseTL.remove();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        DistCommonConstant.requestTL.remove();
        DistCommonConstant.responseTL.remove();
    }
}
