/*
 * RequestContext.java
 * Copyright 2021 Qunhe Tech, all rights reserved.
 * Qunhe PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.followba.store.common.context;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author 祥霸
 * @since 1.0.6
 */
public class RequestContext {

    public static final String ZEUS_REQUEST = RequestContext.class.getName() + "_ZEUS_REQUEST_KEY";

    /**
     * 设置线程的变量
     *
     * @param request {@link IToolRequest}
     */
    public static void bindRequest(IToolRequest request) {
        if (request != null) {
            ThreadContext.put(ZEUS_REQUEST, request);
        }
    }

    /**
     * 取消线程中的变量
     *
     * @return request {@link IToolRequest}
     */
    public static IToolRequest unbindRequest() {
        return (IToolRequest) ThreadContext.remove(ZEUS_REQUEST);
    }

    /**
     * 获取当前请求，包括从Web端来的请求和dubbo来的请求
     *
     * @return request {@link IToolRequest}
     */
    public static IToolRequest getRequest() {
        return (IToolRequest) ThreadContext.get(ZEUS_REQUEST);
    }

    /**
     * 获取客户端的请求IP地址，非nginx的IP
     *
     * @return ip
     */
    public static String getRequestIp() {
        return getRequest() != null ? getRequest().getIp() : "";
    }

    /**
     * 输出当前请求的信息
     */
    public static String dumpRequest() {
        return dumpRequest(getRequest());
    }

    public static String getUserId() {
        IToolRequest request = getRequest();
        if (null == request) {
            return "visitor";
        }
        return request.getUserId() == null ? "visitor" : request.getUserId();
    }


    public static String getToken() {
        IToolRequest request = getRequest();
        if (null == request) {
            return "visitor";
        }
        return request.getToken() == null ? "visitor" : request.getToken();
    }

    public static String dumpRequest(IToolRequest request) {

        if (request == null) {
            return "null request";
        }

        ToStringBuilder builder = new ToStringBuilder(request, ToStringStyle.SHORT_PREFIX_STYLE);
        return builder.toString();
    }

}
