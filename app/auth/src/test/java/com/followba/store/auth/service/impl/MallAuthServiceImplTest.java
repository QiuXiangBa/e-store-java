package com.followba.store.auth.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.followba.store.auth.dto.AuthLoginDTO;
import com.followba.store.auth.dto.AuthLoginResultDTO;
import com.followba.store.auth.dto.AuthRegisterDTO;
import com.followba.store.auth.dto.AuthUserDTO;
import com.followba.store.common.context.IToolRequest;
import com.followba.store.common.context.RequestContext;
import com.followba.store.dao.biz.BizMemberUsersMapper;
import com.followba.store.dao.biz.BizSystemOauth2AccessTokenMapper;
import com.followba.store.dao.biz.BizSystemOauth2RefreshTokenMapper;
import com.followba.store.dao.constant.AuthConstants;
import com.followba.store.dao.dto.MemberUsersDTO;
import com.followba.store.dao.dto.SystemOauth2AccessTokenDTO;
import com.followba.store.dao.dto.SystemOauth2RefreshTokenDTO;
import com.followba.store.product.service.MallCartService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MallAuthServiceImplTest {

    @InjectMocks
    private MallAuthServiceImpl mallAuthService;

    @Mock
    private BizMemberUsersMapper bizMemberUsersMapper;

    @Mock
    private BizSystemOauth2AccessTokenMapper bizSystemOauth2AccessTokenMapper;

    @Mock
    private BizSystemOauth2RefreshTokenMapper bizSystemOauth2RefreshTokenMapper;

    @Mock
    private MallCartService mallCartService;

    @AfterEach
    void tearDown() {
        RequestContext.unbindRequest();
    }

    @Test
    void register_shouldUseMemberUsersTable() {
        AuthRegisterDTO dto = new AuthRegisterDTO();
        dto.setUsername("memberA");
        dto.setPassword("pass123");
        dto.setNickname("member-nick");
        dto.setMobile("13800001111");
        dto.setEmail("member@test.com");

        when(bizMemberUsersMapper.selectByUsername(dto.getUsername())).thenReturn(null);
        when(bizMemberUsersMapper.selectByMobile(dto.getMobile())).thenReturn(null);
        when(bizMemberUsersMapper.selectByEmail(dto.getEmail())).thenReturn(null);
        doAnswer(invocation -> {
            MemberUsersDTO memberUsersDTO = invocation.getArgument(0);
            memberUsersDTO.setId(1001L);
            return null;
        }).when(bizMemberUsersMapper).insert(any(MemberUsersDTO.class));

        AuthLoginResultDTO resultDTO = mallAuthService.register(dto);

        ArgumentCaptor<MemberUsersDTO> userCaptor = ArgumentCaptor.forClass(MemberUsersDTO.class);
        verify(bizMemberUsersMapper).insert(userCaptor.capture());
        MemberUsersDTO insertedDTO = userCaptor.getValue();
        Assertions.assertEquals("memberA", insertedDTO.getUsername());
        Assertions.assertTrue(BCrypt.checkpw("pass123", insertedDTO.getPassword()));
        Assertions.assertEquals(AuthConstants.USER_STATUS_ENABLED, insertedDTO.getStatus());

        verify(bizSystemOauth2AccessTokenMapper).insert(any(SystemOauth2AccessTokenDTO.class));
        verify(bizSystemOauth2RefreshTokenMapper).insert(any(SystemOauth2RefreshTokenDTO.class));
        Assertions.assertNotNull(resultDTO.getAccessToken());
        Assertions.assertEquals(1001L, resultDTO.getUser().getUserId());
    }

    @Test
    void login_shouldReadMemberUsersTable() {
        AuthLoginDTO dto = new AuthLoginDTO();
        dto.setAccount("memberA");
        dto.setPassword("pass123");

        MemberUsersDTO memberUsersDTO = new MemberUsersDTO();
        memberUsersDTO.setId(2002L);
        memberUsersDTO.setUsername("memberA");
        memberUsersDTO.setPassword(BCrypt.hashpw("pass123", BCrypt.gensalt()));
        memberUsersDTO.setStatus(AuthConstants.USER_STATUS_ENABLED);
        memberUsersDTO.setNickname("Member B");

        when(bizMemberUsersMapper.selectByUsername("memberA")).thenReturn(memberUsersDTO);

        AuthLoginResultDTO resultDTO = mallAuthService.login(dto);

        verify(bizMemberUsersMapper).selectByUsername("memberA");
        verify(bizSystemOauth2AccessTokenMapper).insert(any(SystemOauth2AccessTokenDTO.class));
        verify(bizSystemOauth2RefreshTokenMapper).insert(any(SystemOauth2RefreshTokenDTO.class));
        Assertions.assertNotNull(resultDTO.getAccessToken());
        Assertions.assertEquals(2002L, resultDTO.getUser().getUserId());
    }

    @Test
    void me_shouldReadMemberUsersByRequestUserId() {
        RequestContext.bindRequest(IToolRequest.build("token", "3003", "127.0.0.1", "/", "JUnit", ""));
        MemberUsersDTO memberUsersDTO = new MemberUsersDTO();
        memberUsersDTO.setId(3003L);
        memberUsersDTO.setUsername("memberC");
        memberUsersDTO.setNickname("Member C");

        when(bizMemberUsersMapper.selectById(eq(3003L))).thenReturn(memberUsersDTO);

        AuthUserDTO userDTO = mallAuthService.me();

        verify(bizMemberUsersMapper).selectById(3003L);
        Assertions.assertEquals(3003L, userDTO.getUserId());
        Assertions.assertEquals("memberC", userDTO.getUsername());
    }
}
