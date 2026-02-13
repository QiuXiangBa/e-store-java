package com.followba.store.admin.convert;

import com.followba.store.admin.vo.in.SystemAuthCreateUserIn;
import com.followba.store.admin.vo.out.SystemAuthLoginOut;
import com.followba.store.admin.vo.out.SystemAuthUserOut;
import com.followba.store.dao.dto.SystemOauth2AccessTokenDTO;
import com.followba.store.dao.dto.SystemOauth2RefreshTokenDTO;
import com.followba.store.dao.dto.SystemUsersDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Date;

@Mapper
public interface AuthConvert {

    AuthConvert INSTANCE = Mappers.getMapper(AuthConvert.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", expression = "java(user.getId())")
    @Mapping(target = "userType", expression = "java(com.followba.store.dao.constant.AuthConstants.USER_TYPE_ADMIN)")
    @Mapping(target = "userInfo", expression = "java(user.getUsername())")
    @Mapping(target = "accessToken", source = "accessToken")
    @Mapping(target = "refreshToken", source = "refreshToken")
    @Mapping(target = "clientId", expression = "java(com.followba.store.dao.constant.AuthConstants.CLIENT_ID_ADMIN)")
    @Mapping(target = "scopes", expression = "java(com.followba.store.dao.constant.AuthConstants.DEFAULT_SCOPES)")
    @Mapping(target = "expiresTime", source = "expiresTime")
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updater", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "deleted", expression = "java(com.followba.store.dao.constant.AuthConstants.DEFAULT_NOT_DELETED)")
    @Mapping(target = "tenantId", expression = "java((long) com.followba.store.dao.constant.AuthConstants.DEFAULT_TENANT_ID)")
    SystemOauth2AccessTokenDTO toAccessTokenDTO(SystemUsersDTO user, String accessToken, String refreshToken, Date expiresTime);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", expression = "java(user.getId())")
    @Mapping(target = "refreshToken", source = "refreshToken")
    @Mapping(target = "userType", expression = "java(com.followba.store.dao.constant.AuthConstants.USER_TYPE_ADMIN)")
    @Mapping(target = "clientId", expression = "java(com.followba.store.dao.constant.AuthConstants.CLIENT_ID_ADMIN)")
    @Mapping(target = "scopes", expression = "java(com.followba.store.dao.constant.AuthConstants.DEFAULT_SCOPES)")
    @Mapping(target = "expiresTime", source = "expiresTime")
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updater", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "deleted", expression = "java(com.followba.store.dao.constant.AuthConstants.DEFAULT_NOT_DELETED)")
    @Mapping(target = "tenantId", expression = "java((long) com.followba.store.dao.constant.AuthConstants.DEFAULT_TENANT_ID)")
    SystemOauth2RefreshTokenDTO toRefreshTokenDTO(SystemUsersDTO user, String refreshToken, Date expiresTime);

    @Mapping(target = "expiresTime", expression = "java(dto.getExpiresTime() == null ? null : dto.getExpiresTime().getTime())")
    SystemAuthLoginOut toLoginOut(SystemOauth2AccessTokenDTO dto);

    SystemAuthUserOut toUserOut(SystemUsersDTO dto);

    @Mapping(target = "loginIp", source = "loginIp")
    @Mapping(target = "loginDate", source = "loginDate")
    SystemUsersDTO toLoginSuccessUserDTO(SystemUsersDTO user, String loginIp, Date loginDate);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nickname", expression = "java(org.apache.commons.lang3.StringUtils.isBlank(in.getNickname()) ? in.getUsername() : in.getNickname())")
    @Mapping(target = "status", expression = "java(com.followba.store.dao.constant.AuthConstants.USER_STATUS_ENABLED)")
    @Mapping(target = "deleted", expression = "java(com.followba.store.dao.constant.AuthConstants.DEFAULT_NOT_DELETED)")
    @Mapping(target = "tenantId", expression = "java((long) com.followba.store.dao.constant.AuthConstants.DEFAULT_TENANT_ID)")
    @Mapping(target = "loginIp", ignore = true)
    @Mapping(target = "loginDate", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updater", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "password", ignore = true)
    SystemUsersDTO toCreateUserDTO(SystemAuthCreateUserIn in);
}
