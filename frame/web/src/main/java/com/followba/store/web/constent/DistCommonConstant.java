/*
 * DistCommonConstant.java
 * Copyright 2021 Qunhe Tech, all rights reserved.
 * Qunhe PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.followba.store.web.constent;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author 祥霸
 * @since 1.0.0
 */
public class DistCommonConstant {
    /**
     * 保存request的threadLocal
     */
    public static final ThreadLocal<HttpServletRequest> requestTL = new ThreadLocal<>();
    /**
     * 保存response的threadLocal
     */
    public static final ThreadLocal<HttpServletResponse> responseTL = new ThreadLocal<HttpServletResponse>();
    /**
     * https 协议判断
     */
    public static final ThreadLocal<Boolean> httpsProtocolTL = ThreadLocal.withInitial(() -> Boolean.FALSE);
}
