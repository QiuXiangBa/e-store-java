package com.followba.store.admin.controller;

import com.followba.store.admin.service.AuthService;
import com.followba.store.admin.vo.in.SystemAuthCreateUserIn;
import com.followba.store.admin.vo.in.SystemAuthLoginIn;
import com.followba.store.admin.vo.out.CommonBooleanRespVO;
import com.followba.store.admin.vo.out.CommonIdRespVO;
import com.followba.store.admin.vo.out.SystemAuthLoginOut;
import com.followba.store.admin.vo.out.SystemAuthPermissionInfoOut;
import com.followba.store.common.resp.Out;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/auth")
@Validated
public class AuthController {

    @Resource
    private AuthService authService;

    @PostMapping("/create-user")
    public Out<CommonIdRespVO> createUser(@Valid @RequestBody SystemAuthCreateUserIn in) {
        return Out.success(CommonIdRespVO.of(authService.createUser(in)));
    }

    @PostMapping("/login")
    public Out<SystemAuthLoginOut> login(@Valid @RequestBody SystemAuthLoginIn in) {
        return Out.success(authService.login(in));
    }

    @PostMapping("/logout")
    public Out<CommonBooleanRespVO> logout(@RequestHeader(value = "Authorization", required = false) String token) {
        authService.logout(token);
        return Out.success(CommonBooleanRespVO.ok());
    }

    @GetMapping("/get-permission-info")
    public Out<SystemAuthPermissionInfoOut> getPermissionInfo(@RequestHeader(value = "Authorization", required = false) String token) {
        return Out.success(authService.getPermissionInfo(token));
    }
}
