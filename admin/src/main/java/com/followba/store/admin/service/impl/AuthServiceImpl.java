package com.followba.store.admin.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.followba.store.admin.convert.AuthConvert;
import com.followba.store.admin.service.AuthService;
import com.followba.store.admin.vo.in.SystemAuthCreateUserIn;
import com.followba.store.admin.vo.in.SystemAuthLoginIn;
import com.followba.store.admin.vo.out.SystemAuthLoginOut;
import com.followba.store.admin.vo.out.SystemAuthPermissionInfoOut;
import com.followba.store.common.constent.CodeEnum;
import com.followba.store.common.context.RequestContext;
import com.followba.store.common.exception.BizException;
import com.followba.store.dao.biz.BizSystemOauth2AccessTokenMapper;
import com.followba.store.dao.biz.BizSystemOauth2RefreshTokenMapper;
import com.followba.store.dao.biz.BizSystemUsersMapper;
import com.followba.store.dao.constant.AuthConstants;
import com.followba.store.dao.dto.SystemOauth2AccessTokenDTO;
import com.followba.store.dao.dto.SystemOauth2RefreshTokenDTO;
import com.followba.store.dao.dto.SystemUsersDTO;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private BizSystemUsersMapper bizSystemUsersMapper;

    @Resource
    private BizSystemOauth2AccessTokenMapper bizSystemOauth2AccessTokenMapper;

    @Resource
    private BizSystemOauth2RefreshTokenMapper bizSystemOauth2RefreshTokenMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createUser(SystemAuthCreateUserIn in) {
        SystemUsersDTO exists = bizSystemUsersMapper.selectByUsername(in.getUsername());
        if (exists != null) {
            throw new BizException(AuthConstants.AUTH_USERNAME_EXISTS);
        }
        SystemUsersDTO createUser = AuthConvert.INSTANCE.toCreateUserDTO(in);
        createUser.setPassword(BCrypt.hashpw(in.getPassword(), BCrypt.gensalt()));
        bizSystemUsersMapper.insert(createUser);
        return createUser.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SystemAuthLoginOut login(SystemAuthLoginIn in) {
        SystemUsersDTO user = bizSystemUsersMapper.selectByUsername(in.getUsername());
        if (user == null) {
            throw new BizException(AuthConstants.AUTH_LOGIN_USER_NOT_EXISTS);
        }
        if (Short.valueOf(AuthConstants.USER_STATUS_DISABLED).equals(user.getStatus())) {
            throw new BizException(AuthConstants.AUTH_LOGIN_USER_DISABLED);
        }
        if (!isPasswordMatched(in.getPassword(), user.getPassword())) {
            throw new BizException(AuthConstants.AUTH_LOGIN_PASSWORD_INVALID);
        }

        Date now = new Date();
        String accessToken = generateToken();
        String refreshToken = generateToken();
        Date accessTokenExpireTime = new Date(now.getTime() + AuthConstants.ACCESS_TOKEN_EXPIRE_MILLIS);
        Date refreshTokenExpireTime = new Date(now.getTime() + AuthConstants.REFRESH_TOKEN_EXPIRE_MILLIS);

        SystemOauth2RefreshTokenDTO refreshTokenDTO =
                AuthConvert.INSTANCE.toRefreshTokenDTO(user, refreshToken, refreshTokenExpireTime);
        bizSystemOauth2RefreshTokenMapper.insert(refreshTokenDTO);

        SystemOauth2AccessTokenDTO accessTokenDTO =
                AuthConvert.INSTANCE.toAccessTokenDTO(user, accessToken, refreshToken, accessTokenExpireTime);
        bizSystemOauth2AccessTokenMapper.insert(accessTokenDTO);

        SystemUsersDTO updateUser = AuthConvert.INSTANCE.toLoginSuccessUserDTO(user, RequestContext.getRequestIp(), now);
        bizSystemUsersMapper.updateById(updateUser);
        return AuthConvert.INSTANCE.toLoginOut(accessTokenDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void logout(String token) {
        String accessToken = extractToken(token);
        if (StringUtils.isBlank(accessToken)) {
            return;
        }
        SystemOauth2AccessTokenDTO accessTokenDTO = bizSystemOauth2AccessTokenMapper.selectByAccessToken(accessToken);
        if (accessTokenDTO == null) {
            return;
        }
        bizSystemOauth2AccessTokenMapper.deleteByAccessToken(accessToken);
        if (StringUtils.isNotBlank(accessTokenDTO.getRefreshToken())) {
            bizSystemOauth2RefreshTokenMapper.deleteByRefreshToken(accessTokenDTO.getRefreshToken());
            bizSystemOauth2AccessTokenMapper.deleteByRefreshToken(accessTokenDTO.getRefreshToken());
        }
    }

    @Override
    public SystemAuthPermissionInfoOut getPermissionInfo(String token) {
        String userId = getUserIdByToken(token);
        if (StringUtils.isBlank(userId)) {
            throw new BizException(CodeEnum.NO_LOGIN);
        }
        SystemUsersDTO user = bizSystemUsersMapper.selectById(Long.valueOf(userId));
        if (user == null) {
            throw new BizException(AuthConstants.AUTH_LOGIN_USER_NOT_EXISTS);
        }

        SystemAuthPermissionInfoOut out = new SystemAuthPermissionInfoOut();
        out.setUser(AuthConvert.INSTANCE.toUserOut(user));
        out.setRoles(List.of(AuthConstants.ROLE_ADMIN));
        out.setPermissions(List.of(AuthConstants.PERMISSION_ALL));
        return out;
    }

    @Override
    public String getUserIdByToken(String token) {
        String accessToken = extractToken(token);
        if (StringUtils.isBlank(accessToken)) {
            return "";
        }
        SystemOauth2AccessTokenDTO accessTokenDTO = bizSystemOauth2AccessTokenMapper.selectByAccessToken(accessToken);
        if (accessTokenDTO == null || accessTokenDTO.getExpiresTime() == null) {
            return "";
        }
        if (accessTokenDTO.getExpiresTime().before(new Date())) {
            return "";
        }
        return String.valueOf(accessTokenDTO.getUserId());
    }

    private String extractToken(String token) {
        if (StringUtils.isBlank(token)) {
            return "";
        }
        String value = token.trim();
        if (value.regionMatches(true, 0, AuthConstants.TOKEN_PREFIX_BEARER, 0, AuthConstants.TOKEN_PREFIX_BEARER.length())) {
            return value.substring(AuthConstants.TOKEN_PREFIX_BEARER.length()).trim();
        }
        return value;
    }

    private boolean isPasswordMatched(String rawPassword, String encodedPassword) {
        if (StringUtils.isBlank(rawPassword) || StringUtils.isBlank(encodedPassword)) {
            return false;
        }
        if (encodedPassword.startsWith(AuthConstants.BCRYPT_PREFIX_2A)
                || encodedPassword.startsWith(AuthConstants.BCRYPT_PREFIX_2B)
                || encodedPassword.startsWith(AuthConstants.BCRYPT_PREFIX_2Y)) {
            return BCrypt.checkpw(rawPassword, encodedPassword);
        }
        return StringUtils.equals(rawPassword, encodedPassword);
    }

    private String generateToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
