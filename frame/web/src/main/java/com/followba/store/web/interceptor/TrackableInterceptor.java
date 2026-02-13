/*
 * TrackableInterceptor.java
 * Copyright 2021 Qunhe Tech, all rights reserved.
 * Qunhe PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.followba.store.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 祥霸
 * @since 1.0.0
 */
public abstract class TrackableInterceptor implements HandlerInterceptor {

    protected static final String THE_INTERCEPTORS = "__THE_INTERCEPTORS";

    @Override
    public final boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        getInterceptorClassList(request).add(getClass());
        return doPreHandle(request, response, handler);
    }

    private List<Class<? extends HandlerInterceptor>> getInterceptorClassList(
            HttpServletRequest request) {
        Object attribute = request.getAttribute(THE_INTERCEPTORS);
        if (attribute instanceof List) {
            return (List<Class<? extends HandlerInterceptor>>) attribute;
        } else {
            List<Class<? extends HandlerInterceptor>> interceptors = new ArrayList<>();
            request.setAttribute(THE_INTERCEPTORS, interceptors);
            return interceptors;
        }
    }

    protected boolean isAfterInterceptor(
            HttpServletRequest request, Class<? extends HandlerInterceptor> interceptorClass) {
        List<Class<? extends HandlerInterceptor>> interceptorClassList = getInterceptorClassList(request);
        int posTarget = interceptorClassList.indexOf(interceptorClass);
        int posSelf = interceptorClassList.indexOf(getClass());

        if (posTarget < 0) {
            return false;
        } else if (posSelf < 0) {
            return true;
        } else {
            return posTarget < posSelf;
        }
    }

    protected abstract boolean doPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
