package com.followba.store.auth.dto;

import lombok.Data;

@Data
public class AuthLoginResultDTO {

    private String accessToken;

    private String refreshToken;

    private Long expiresTime;

    private AuthUserDTO user;
}
