package com.followba.store.dao.biz;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.followba.store.dao.constant.AuthConstants;
import com.followba.store.dao.convert.MemberUsersConvert;
import com.followba.store.dao.dto.MemberUsersDTO;
import com.followba.store.dao.mapper.MemberUsersMapper;
import com.followba.store.dao.po.MemberUsers;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class BizMemberUsersMapper {

    @Resource
    private MemberUsersMapper mapper;

    public MemberUsersDTO selectById(Long id) {
        return MemberUsersConvert.INSTANCE.toDTO(mapper.selectById(id));
    }

    public MemberUsersDTO selectByUsername(String username) {
        LambdaQueryWrapper<MemberUsers> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MemberUsers::getUsername, username)
                .eq(MemberUsers::getDeleted, AuthConstants.DEFAULT_NOT_DELETED)
                .last("LIMIT 1");
        return MemberUsersConvert.INSTANCE.toDTO(mapper.selectOne(wrapper));
    }

    public MemberUsersDTO selectByMobile(String mobile) {
        LambdaQueryWrapper<MemberUsers> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MemberUsers::getMobile, mobile)
                .eq(MemberUsers::getDeleted, AuthConstants.DEFAULT_NOT_DELETED)
                .last("LIMIT 1");
        return MemberUsersConvert.INSTANCE.toDTO(mapper.selectOne(wrapper));
    }

    public MemberUsersDTO selectByEmail(String email) {
        LambdaQueryWrapper<MemberUsers> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MemberUsers::getEmail, email)
                .eq(MemberUsers::getDeleted, AuthConstants.DEFAULT_NOT_DELETED)
                .last("LIMIT 1");
        return MemberUsersConvert.INSTANCE.toDTO(mapper.selectOne(wrapper));
    }

    public void insert(MemberUsersDTO dto) {
        MemberUsers po = MemberUsersConvert.INSTANCE.toPO(dto);
        mapper.insert(po);
        dto.setId(po.getId());
    }
}
