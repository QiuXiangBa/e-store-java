package com.followba.store.admin.vo.in;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SystemAuthCreateUserIn {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    private String nickname;
}
