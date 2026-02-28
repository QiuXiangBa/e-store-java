package com.followba.store.auth.dto;

import lombok.Data;

@Data
public class AuthUserDTO {

    private Long userId;

    private String username;

    private String nickname;

    private String mobile;

    private String email;

    private String avatar;
}
