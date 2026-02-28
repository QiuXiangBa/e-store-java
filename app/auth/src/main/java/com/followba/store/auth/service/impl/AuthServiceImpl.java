package com.followba.store.auth.service.impl;

import com.followba.store.auth.service.AuthService;
import com.followba.store.dao.biz.BizSystemOauth2AccessTokenMapper;
import com.followba.store.dao.constant.AuthConstants;
import com.followba.store.dao.dto.SystemOauth2AccessTokenDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private BizSystemOauth2AccessTokenMapper bizSystemOauth2AccessTokenMapper;

    @Override
    public String getUserIdByToken(String token) {
        String normalizedToken = normalizeToken(token);
        if (normalizedToken == null) {
            return null;
        }
        SystemOauth2AccessTokenDTO tokenDTO = bizSystemOauth2AccessTokenMapper.selectByAccessToken(normalizedToken);
        if (tokenDTO == null) {
            return null;
        }
        if (tokenDTO.getExpiresTime() == null || tokenDTO.getExpiresTime().before(new Date())) {
            return null;
        }
        return tokenDTO.getUserId() == null ? null : String.valueOf(tokenDTO.getUserId());
    }

    private String normalizeToken(String token) {
        if (token == null || token.isBlank()) {
            return null;
        }
        String normalizedToken = token.trim();
        if (normalizedToken.startsWith(AuthConstants.TOKEN_PREFIX_BEARER)) {
            normalizedToken = normalizedToken.substring(AuthConstants.TOKEN_PREFIX_BEARER.length()).trim();
        }
        if (normalizedToken.isBlank()) {
            return null;
        }
        return normalizedToken;
    }
}
