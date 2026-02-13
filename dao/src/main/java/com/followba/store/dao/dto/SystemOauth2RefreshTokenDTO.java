package com.followba.store.dao.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SystemOauth2RefreshTokenDTO {

    private Long id;

    private Long userId;

    private String refreshToken;

    private Short userType;

    private String clientId;

    private String scopes;

    private Date expiresTime;

    private String creator;

    private Date createTime;

    private String updater;

    private Date updateTime;

    private Short deleted;

    private Long tenantId;
}
