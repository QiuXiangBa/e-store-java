package com.followba.store.auth.controller;

import com.followba.store.auth.convert.MallAuthConvert;
import com.followba.store.auth.dto.AuthLoginResultDTO;
import com.followba.store.auth.dto.AuthUserDTO;
import com.followba.store.auth.service.MallAuthService;
import com.followba.store.auth.vo.in.AuthLoginIn;
import com.followba.store.auth.vo.in.AuthRegisterIn;
import com.followba.store.auth.vo.out.AuthLoginOut;
import com.followba.store.auth.vo.out.AuthUserOut;
import com.followba.store.auth.vo.out.CommonBooleanOut;
import com.followba.store.common.resp.Out;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mall/auth")
@Validated
public class MallAuthController {

    @Resource
    private MallAuthService mallAuthService;

    @PostMapping("/register")
    public Out<AuthLoginOut> register(@Valid @RequestBody AuthRegisterIn in) {
        AuthLoginResultDTO resultDTO = mallAuthService.register(MallAuthConvert.INSTANCE.toAuthRegisterDTO(in));
        return Out.success(MallAuthConvert.INSTANCE.toAuthLoginOut(resultDTO));
    }

    @PostMapping("/login")
    public Out<AuthLoginOut> login(@Valid @RequestBody AuthLoginIn in) {
        AuthLoginResultDTO resultDTO = mallAuthService.login(MallAuthConvert.INSTANCE.toAuthLoginDTO(in));
        return Out.success(MallAuthConvert.INSTANCE.toAuthLoginOut(resultDTO));
    }

    @PostMapping("/logout")
    public Out<CommonBooleanOut> logout() {
        mallAuthService.logout();
        return Out.success(CommonBooleanOut.ok());
    }

    @GetMapping("/me")
    public Out<AuthUserOut> me() {
        AuthUserDTO userDTO = mallAuthService.me();
        return Out.success(MallAuthConvert.INSTANCE.toAuthUserOut(userDTO));
    }
}
