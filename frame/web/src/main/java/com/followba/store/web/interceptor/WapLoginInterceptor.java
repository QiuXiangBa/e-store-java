/*
 * WapLoginInterceptor.java
 * Copyright 2021 Qunhe Tech, all rights reserved.
 * Qunhe PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.followba.store.web.interceptor;

import cn.hutool.core.util.StrUtil;
import com.followba.store.web.config.WebProperties;
import com.followba.store.web.template.WebLoginTemplate;
import com.followba.store.web.util.CookieUtil;
import com.followba.store.web.util.RequestAttrUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 祥霸
 * @since 1.0.0
 */
@Log4j2
public class WapLoginInterceptor extends TrackableInterceptor {

    @Resource
    private WebLoginTemplate webLoginComponent;

    @Resource
    private WebProperties webProperties;


    @Override
    public boolean doPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!isAfterInterceptor(request, PreLoginInterceptor.class)) {
            log.error("必须先配置以下拦截器 {} on {}", PreLoginInterceptor.class.getName(), request.getRequestURI());
        }

        // 尝试请求头获取token
        String token = request.getHeader(webProperties.getHeadTokenName());

        if(StringUtils.isBlank(token)){
            // 尝试cookie获取token
            token = CookieUtil.getCookie(request, webProperties.getHeadTokenName());
        }

//        log.info("head token: {}", token);

        if(StrUtil.isNotBlank(token)) {
            String userId = webLoginComponent.getUserIdByToken(token);
            if(StrUtil.isNotBlank(userId)) {
                RequestAttrUtil.setUserIdFromRequest(userId);
                RequestAttrUtil.setTokenFromRequest(token);
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView){

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){

    }


}

