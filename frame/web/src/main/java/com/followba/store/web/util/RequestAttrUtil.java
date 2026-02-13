/*
 * RequestAttrUtil.java
 * Copyright 2021 Qunhe Tech, all rights reserved.
 * Qunhe PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.followba.store.web.util;


import com.followba.store.web.constent.DistCommonConstant;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Objects;

/**
 * @author 祥霸
 * @since 1.0.0
 */
public class RequestAttrUtil {

    public static final String CURRENT_LOGIN_USER_ID = "distCurrentLoginUserId";
    public static final String CURRENT_LOGIN_TOKEN = "distCurrentLoginToken";

    public static void setUserIdFromRequest(String userId) {
        setAttribute(CURRENT_LOGIN_USER_ID, userId);
    }

    public static String getUserIdFromRequest() {
        Object obj = getAttribute(CURRENT_LOGIN_USER_ID);

        if(Objects.isNull(obj)) {
            return null;
        }

        return String.valueOf(obj);
    }

    public static void setTokenFromRequest(String token) {
        setAttribute(CURRENT_LOGIN_TOKEN, token);
    }

    public static String getTokenFromRequest() {
        Object obj = getAttribute(CURRENT_LOGIN_TOKEN);

        if(Objects.isNull(obj)) {
            return null;
        }

        return String.valueOf(obj);
    }

    public static Object getAttribute(String name) {
        HttpServletRequest request = DistCommonConstant.requestTL.get();
        if (Objects.isNull(request)) {
            return null;
        }
        return request.getAttribute(name);
    }

    public static void setAttribute(String name, Object value) {
        HttpServletRequest request = DistCommonConstant.requestTL.get();
        if (request == null) {
            return;
        }
        request.setAttribute(name, value);
    }
}
