package com.followba.store.auth.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.followba.store.auth.dto.AuthGuestCartItemDTO;
import com.followba.store.auth.dto.AuthLoginDTO;
import com.followba.store.auth.dto.AuthLoginResultDTO;
import com.followba.store.auth.dto.AuthRegisterDTO;
import com.followba.store.auth.dto.AuthUserDTO;
import com.followba.store.auth.service.MallAuthService;
import com.followba.store.common.context.RequestContext;
import com.followba.store.common.exception.BizException;
import com.followba.store.dao.biz.BizSystemOauth2AccessTokenMapper;
import com.followba.store.dao.biz.BizSystemOauth2RefreshTokenMapper;
import com.followba.store.dao.biz.BizSystemUsersMapper;
import com.followba.store.dao.constant.AuthConstants;
import com.followba.store.dao.dto.SystemOauth2AccessTokenDTO;
import com.followba.store.dao.dto.SystemOauth2RefreshTokenDTO;
import com.followba.store.dao.dto.SystemUsersDTO;
import com.followba.store.product.dto.CartMergeItemDTO;
import com.followba.store.product.service.MallCartService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class MallAuthServiceImpl implements MallAuthService {

    @Resource
    private BizSystemUsersMapper bizSystemUsersMapper;

    @Resource
    private BizSystemOauth2AccessTokenMapper bizSystemOauth2AccessTokenMapper;

    @Resource
    private BizSystemOauth2RefreshTokenMapper bizSystemOauth2RefreshTokenMapper;

    @Resource
    private MallCartService mallCartService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AuthLoginResultDTO register(AuthRegisterDTO dto) {
        validateRegister(dto);
        SystemUsersDTO userDTO = new SystemUsersDTO();
        userDTO.setUsername(dto.getUsername());
        userDTO.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt()));
        userDTO.setNickname(dto.getNickname());
        userDTO.setMobile(emptyToBlank(dto.getMobile()));
        userDTO.setEmail(emptyToBlank(dto.getEmail()));
        userDTO.setStatus(AuthConstants.USER_STATUS_ENABLED);
        userDTO.setDeleted(AuthConstants.DEFAULT_NOT_DELETED);
        userDTO.setTenantId((long) AuthConstants.DEFAULT_TENANT_ID);
        bizSystemUsersMapper.insert(userDTO);
        AuthLoginResultDTO resultDTO = buildLoginResult(userDTO);
        mergeGuestCart(userDTO.getId(), dto.getGuestCartItems());
        return resultDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AuthLoginResultDTO login(AuthLoginDTO dto) {
        SystemUsersDTO userDTO = findUserByAccount(dto.getAccount());
        if (userDTO == null) {
            throw new BizException(AuthConstants.AUTH_LOGIN_USER_NOT_EXISTS);
        }
        if (!passwordMatched(dto.getPassword(), userDTO.getPassword())) {
            throw new BizException(AuthConstants.AUTH_LOGIN_PASSWORD_INVALID);
        }
        if (!Objects.equals(userDTO.getStatus(), AuthConstants.USER_STATUS_ENABLED)) {
            throw new BizException(AuthConstants.AUTH_LOGIN_USER_DISABLED);
        }
        AuthLoginResultDTO resultDTO = buildLoginResult(userDTO);
        mergeGuestCart(userDTO.getId(), dto.getGuestCartItems());
        return resultDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void logout() {
        String token = normalizeToken(RequestContext.getToken());
        if (token == null) {
            return;
        }
        SystemOauth2AccessTokenDTO accessTokenDTO = bizSystemOauth2AccessTokenMapper.selectByAccessToken(token);
        bizSystemOauth2AccessTokenMapper.deleteByAccessToken(token);
        if (accessTokenDTO != null && accessTokenDTO.getRefreshToken() != null) {
            bizSystemOauth2RefreshTokenMapper.deleteByRefreshToken(accessTokenDTO.getRefreshToken());
        }
    }

    @Override
    public AuthUserDTO me() {
        Long userId = getCurrentUserId();
        SystemUsersDTO userDTO = bizSystemUsersMapper.selectById(userId);
        if (userDTO == null) {
            throw new BizException(AuthConstants.AUTH_LOGIN_USER_NOT_EXISTS);
        }
        return toAuthUserDTO(userDTO);
    }

    private void validateRegister(AuthRegisterDTO dto) {
        if (bizSystemUsersMapper.selectByUsername(dto.getUsername()) != null) {
            throw new BizException(AuthConstants.AUTH_USERNAME_EXISTS);
        }
        if (dto.getMobile() != null && !dto.getMobile().isBlank() && bizSystemUsersMapper.selectByMobile(dto.getMobile()) != null) {
            throw new BizException(AuthConstants.AUTH_MOBILE_EXISTS);
        }
        if (dto.getEmail() != null && !dto.getEmail().isBlank() && bizSystemUsersMapper.selectByEmail(dto.getEmail()) != null) {
            throw new BizException(AuthConstants.AUTH_EMAIL_EXISTS);
        }
    }

    private SystemUsersDTO findUserByAccount(String account) {
        if (account == null || account.isBlank()) {
            return null;
        }
        SystemUsersDTO byUsername = bizSystemUsersMapper.selectByUsername(account);
        if (byUsername != null) {
            return byUsername;
        }
        SystemUsersDTO byMobile = bizSystemUsersMapper.selectByMobile(account);
        if (byMobile != null) {
            return byMobile;
        }
        return bizSystemUsersMapper.selectByEmail(account);
    }

    private AuthLoginResultDTO buildLoginResult(SystemUsersDTO userDTO) {
        Date now = new Date();
        String accessToken = randomToken();
        String refreshToken = randomToken();
        Date accessExpireTime = new Date(now.getTime() + AuthConstants.ACCESS_TOKEN_EXPIRE_MILLIS);
        Date refreshExpireTime = new Date(now.getTime() + AuthConstants.REFRESH_TOKEN_EXPIRE_MILLIS);

        SystemOauth2AccessTokenDTO accessTokenDTO = new SystemOauth2AccessTokenDTO();
        accessTokenDTO.setUserId(userDTO.getId());
        accessTokenDTO.setUserType(AuthConstants.USER_TYPE_MEMBER);
        accessTokenDTO.setUserInfo(userDTO.getUsername());
        accessTokenDTO.setAccessToken(accessToken);
        accessTokenDTO.setRefreshToken(refreshToken);
        accessTokenDTO.setClientId(AuthConstants.CLIENT_ID_MALL_PC);
        accessTokenDTO.setScopes(AuthConstants.DEFAULT_SCOPES);
        accessTokenDTO.setExpiresTime(accessExpireTime);
        accessTokenDTO.setDeleted(AuthConstants.DEFAULT_NOT_DELETED);
        accessTokenDTO.setTenantId((long) AuthConstants.DEFAULT_TENANT_ID);
        bizSystemOauth2AccessTokenMapper.insert(accessTokenDTO);

        SystemOauth2RefreshTokenDTO refreshTokenDTO = new SystemOauth2RefreshTokenDTO();
        refreshTokenDTO.setUserId(userDTO.getId());
        refreshTokenDTO.setRefreshToken(refreshToken);
        refreshTokenDTO.setUserType(AuthConstants.USER_TYPE_MEMBER);
        refreshTokenDTO.setClientId(AuthConstants.CLIENT_ID_MALL_PC);
        refreshTokenDTO.setScopes(AuthConstants.DEFAULT_SCOPES);
        refreshTokenDTO.setExpiresTime(refreshExpireTime);
        refreshTokenDTO.setDeleted(AuthConstants.DEFAULT_NOT_DELETED);
        refreshTokenDTO.setTenantId((long) AuthConstants.DEFAULT_TENANT_ID);
        bizSystemOauth2RefreshTokenMapper.insert(refreshTokenDTO);

        AuthLoginResultDTO resultDTO = new AuthLoginResultDTO();
        resultDTO.setAccessToken(accessToken);
        resultDTO.setRefreshToken(refreshToken);
        resultDTO.setExpiresTime(accessExpireTime.getTime());
        resultDTO.setUser(toAuthUserDTO(userDTO));
        return resultDTO;
    }

    private void mergeGuestCart(Long userId, List<AuthGuestCartItemDTO> itemDTOList) {
        if (itemDTOList == null || itemDTOList.isEmpty()) {
            return;
        }
        List<CartMergeItemDTO> mergeItemDTOList = itemDTOList.stream().map(itemDTO -> {
            CartMergeItemDTO dto = new CartMergeItemDTO();
            dto.setSkuId(itemDTO.getSkuId());
            dto.setQuantity(itemDTO.getQuantity());
            return dto;
        }).toList();
        mallCartService.mergeGuestItems(userId, mergeItemDTOList);
    }

    private AuthUserDTO toAuthUserDTO(SystemUsersDTO userDTO) {
        AuthUserDTO dto = new AuthUserDTO();
        dto.setUserId(userDTO.getId());
        dto.setUsername(userDTO.getUsername());
        dto.setNickname(userDTO.getNickname());
        dto.setMobile(userDTO.getMobile());
        dto.setEmail(userDTO.getEmail());
        dto.setAvatar(userDTO.getAvatar());
        return dto;
    }

    private boolean passwordMatched(String rawPassword, String encodedPassword) {
        if (rawPassword == null || encodedPassword == null) {
            return false;
        }
        if (isBCryptPassword(encodedPassword)) {
            return BCrypt.checkpw(rawPassword, encodedPassword);
        }
        return Objects.equals(rawPassword, encodedPassword);
    }

    private boolean isBCryptPassword(String encodedPassword) {
        return encodedPassword.startsWith(AuthConstants.BCRYPT_PREFIX_2A)
                || encodedPassword.startsWith(AuthConstants.BCRYPT_PREFIX_2B)
                || encodedPassword.startsWith(AuthConstants.BCRYPT_PREFIX_2Y);
    }

    private String randomToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private String normalizeToken(String token) {
        if (token == null || token.isBlank() || "visitor".equalsIgnoreCase(token)) {
            return null;
        }
        String normalized = token.trim();
        if (normalized.startsWith(AuthConstants.TOKEN_PREFIX_BEARER)) {
            normalized = normalized.substring(AuthConstants.TOKEN_PREFIX_BEARER.length()).trim();
        }
        return normalized.isBlank() ? null : normalized;
    }

    private String emptyToBlank(String value) {
        if (value == null) {
            return "";
        }
        return value.trim();
    }

    private Long getCurrentUserId() {
        String userId = RequestContext.getUserId();
        if (userId == null || userId.isBlank() || "visitor".equalsIgnoreCase(userId)) {
            throw new BizException(AuthConstants.AUTH_NOT_LOGIN);
        }
        try {
            return Long.valueOf(userId);
        } catch (NumberFormatException exception) {
            throw new BizException(AuthConstants.AUTH_NOT_LOGIN);
        }
    }
}
