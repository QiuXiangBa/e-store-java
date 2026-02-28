package com.followba.store.auth.dto;

import lombok.Data;

import java.util.List;

@Data
public class AuthRegisterDTO {

    private String username;

    private String password;

    private String nickname;

    private String mobile;

    private String email;

    private List<AuthGuestCartItemDTO> guestCartItems;
}
