package com.followba.store.admin.service;

import com.followba.store.admin.vo.in.SystemAuthCreateUserIn;
import com.followba.store.admin.vo.in.SystemAuthLoginIn;
import com.followba.store.admin.vo.out.SystemAuthLoginOut;
import com.followba.store.admin.vo.out.SystemAuthPermissionInfoOut;
import com.followba.store.web.service.WebLoginService;

public interface AuthService extends WebLoginService {

    Long createUser(SystemAuthCreateUserIn in);

    SystemAuthLoginOut login(SystemAuthLoginIn in);

    void logout(String token);

    SystemAuthPermissionInfoOut getPermissionInfo(String token);
}
