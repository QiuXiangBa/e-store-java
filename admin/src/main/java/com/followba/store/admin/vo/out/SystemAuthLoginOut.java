package com.followba.store.admin.vo.out;

import lombok.Data;

@Data
public class SystemAuthLoginOut {

    private Long id;

    private String accessToken;

    private String refreshToken;

    private Long userId;

    private Short userType;

    private String clientId;

    private Long expiresTime;
}
