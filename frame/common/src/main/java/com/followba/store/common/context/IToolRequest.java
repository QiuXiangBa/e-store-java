/*
 * ZeusRequest.java
 * Copyright 2021 Qunhe Tech, all rights reserved.
 * Qunhe PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.followba.store.common.context;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 祥霸
 * @since 1.0.6
 */
@Data
public class IToolRequest implements Serializable {

    public String token;

    public String userId;

    public String ip;

    public String currentUrl;

    public String userAgent;

    public String referUrl;

    public Map<String, String> map = new ConcurrentHashMap<>();

    /**
     * 将公共参数封装成ZeusRequest
     */
    public static IToolRequest build(String token, String userId, String ip, String currentUrl, String userAgent, String referUrl) {
        IToolRequest zeusRequest = new IToolRequest();
        zeusRequest.setToken(token);
        zeusRequest.setUserId(userId);
        zeusRequest.setIp(ip);
        zeusRequest.setCurrentUrl(currentUrl);
        zeusRequest.setUserAgent(userAgent);
        zeusRequest.setReferUrl(referUrl);
        return zeusRequest;
    }
}
