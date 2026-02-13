package com.followba.store.dao.biz;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.followba.store.dao.constant.AuthConstants;
import com.followba.store.dao.convert.SystemOauth2RefreshTokenConvert;
import com.followba.store.dao.dto.SystemOauth2RefreshTokenDTO;
import com.followba.store.dao.mapper.SystemOauth2RefreshTokenMapper;
import com.followba.store.dao.po.SystemOauth2RefreshToken;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class BizSystemOauth2RefreshTokenMapper {

    @Resource
    private SystemOauth2RefreshTokenMapper mapper;

    public void insert(SystemOauth2RefreshTokenDTO dto) {
        mapper.insert(SystemOauth2RefreshTokenConvert.INSTANCE.toPO(dto));
    }

    public SystemOauth2RefreshTokenDTO selectByRefreshToken(String refreshToken) {
        LambdaQueryWrapper<SystemOauth2RefreshToken> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SystemOauth2RefreshToken::getRefreshToken, refreshToken)
                .eq(SystemOauth2RefreshToken::getDeleted, AuthConstants.DEFAULT_NOT_DELETED)
                .last("LIMIT 1");
        return SystemOauth2RefreshTokenConvert.INSTANCE.toDTO(mapper.selectOne(wrapper));
    }

    public void deleteByRefreshToken(String refreshToken) {
        LambdaQueryWrapper<SystemOauth2RefreshToken> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SystemOauth2RefreshToken::getRefreshToken, refreshToken);
        mapper.delete(wrapper);
    }
}
