package com.followba.store.auth.vo.out;

import lombok.Data;

@Data
public class AuthUserOut {

    private Long userId;

    private String username;

    private String nickname;

    private String mobile;

    private String email;

    private String avatar;
}
