package com.followba.store.dao.biz;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.followba.store.dao.constant.AuthConstants;
import com.followba.store.dao.convert.SystemOauth2AccessTokenConvert;
import com.followba.store.dao.dto.SystemOauth2AccessTokenDTO;
import com.followba.store.dao.mapper.SystemOauth2AccessTokenMapper;
import com.followba.store.dao.po.SystemOauth2AccessToken;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class BizSystemOauth2AccessTokenMapper {

    @Resource
    private SystemOauth2AccessTokenMapper mapper;

    public void insert(SystemOauth2AccessTokenDTO dto) {
        mapper.insert(SystemOauth2AccessTokenConvert.INSTANCE.toPO(dto));
    }

    public SystemOauth2AccessTokenDTO selectByAccessToken(String accessToken) {
        LambdaQueryWrapper<SystemOauth2AccessToken> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SystemOauth2AccessToken::getAccessToken, accessToken)
                .eq(SystemOauth2AccessToken::getDeleted, AuthConstants.DEFAULT_NOT_DELETED)
                .last("LIMIT 1");
        return SystemOauth2AccessTokenConvert.INSTANCE.toDTO(mapper.selectOne(wrapper));
    }

    public SystemOauth2AccessTokenDTO selectByRefreshToken(String refreshToken) {
        LambdaQueryWrapper<SystemOauth2AccessToken> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SystemOauth2AccessToken::getRefreshToken, refreshToken)
                .eq(SystemOauth2AccessToken::getDeleted, AuthConstants.DEFAULT_NOT_DELETED)
                .last("LIMIT 1");
        return SystemOauth2AccessTokenConvert.INSTANCE.toDTO(mapper.selectOne(wrapper));
    }

    public void deleteByAccessToken(String accessToken) {
        LambdaQueryWrapper<SystemOauth2AccessToken> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SystemOauth2AccessToken::getAccessToken, accessToken);
        mapper.delete(wrapper);
    }

    public void deleteByRefreshToken(String refreshToken) {
        LambdaQueryWrapper<SystemOauth2AccessToken> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SystemOauth2AccessToken::getRefreshToken, refreshToken);
        mapper.delete(wrapper);
    }
}
