package com.followba.store.auth.vo.in;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class AuthLoginIn {

    @NotBlank(message = "account 不能为空")
    private String account;

    @NotBlank(message = "password 不能为空")
    private String password;

    @Valid
    private List<AuthGuestCartItemIn> guestCartItems;
}
