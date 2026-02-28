package com.followba.store.auth.dto;

import lombok.Data;

import java.util.List;

@Data
public class AuthLoginDTO {

    private String account;

    private String password;

    private List<AuthGuestCartItemDTO> guestCartItems;
}
