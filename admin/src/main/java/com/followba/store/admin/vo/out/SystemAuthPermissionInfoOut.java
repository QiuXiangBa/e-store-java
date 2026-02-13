package com.followba.store.admin.vo.out;

import lombok.Data;

import java.util.List;

@Data
public class SystemAuthPermissionInfoOut {

    private SystemAuthUserOut user;

    private List<String> roles;

    private List<String> permissions;
}
