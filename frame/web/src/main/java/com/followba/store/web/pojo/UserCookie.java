/*
 * UserCookie.java
 * Copyright 2021 Qunhe Tech, all rights reserved.
 * Qunhe PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.followba.store.web.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserCookie implements Serializable {
    private static final long serialVersionUID = 7923004043175894282L;

    private String domain;

    private String name;

    private String value;

    private int maxAge = -1;

    private String path;

    private boolean httpOnly;

    /**
     * cookie same site  : true 时。
     * 设置　SameSite=None
     */
    private boolean cookieSameSite;
}
