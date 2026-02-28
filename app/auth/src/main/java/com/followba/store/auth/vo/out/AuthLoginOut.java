package com.followba.store.auth.vo.out;

import lombok.Data;

@Data
public class AuthLoginOut {

    private String accessToken;

    private String refreshToken;

    private Long expiresTime;

    private AuthUserOut user;
}
