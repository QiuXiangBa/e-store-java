package com.followba.store.auth.vo.in;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class AuthRegisterIn {

    @NotBlank(message = "username 不能为空")
    private String username;

    @NotBlank(message = "password 不能为空")
    private String password;

    @NotBlank(message = "nickname 不能为空")
    private String nickname;

    private String mobile;

    private String email;

    @Valid
    private List<AuthGuestCartItemIn> guestCartItems;
}
