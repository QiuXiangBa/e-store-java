/*
 * CROSInterceptor.java
 * Copyright 2021 Qunhe Tech, all rights reserved.
 * Qunhe PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.followba.store.web.interceptor;

import com.followba.store.web.config.WebProperties;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


/**
 * @author 祥霸
 * @since 1.0.0
 */
@Log4j2
public class CROSInterceptor implements HandlerInterceptor {

    @Resource
    private WebProperties webProperties;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        try {
            response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("P3P", "CP=CAO PSA OUR");
            if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())) {
                response.addHeader("Access-Control-Allow-Methods", "POST,GET,PUT,DELETE,TRACE,OPTIONS");
                response.addHeader("Access-Control-Allow-Headers", "Content-Type,Origin,Accept,"+webProperties.getHeadTokenName());
                response.addHeader("Access-Control-Max-Age", "120");
            }
        } catch (Exception e) {
            log.error("设置CROS信息异常", e);
        }
        return true;
    }
}
