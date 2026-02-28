package com.followba.store.auth.service;

import com.followba.store.auth.dto.AuthLoginDTO;
import com.followba.store.auth.dto.AuthLoginResultDTO;
import com.followba.store.auth.dto.AuthRegisterDTO;
import com.followba.store.auth.dto.AuthUserDTO;

public interface MallAuthService {

    AuthLoginResultDTO register(AuthRegisterDTO dto);

    AuthLoginResultDTO login(AuthLoginDTO dto);

    void logout();

    AuthUserDTO me();
}
