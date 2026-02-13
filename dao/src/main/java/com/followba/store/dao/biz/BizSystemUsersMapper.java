package com.followba.store.dao.biz;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.followba.store.dao.constant.AuthConstants;
import com.followba.store.dao.convert.SystemUsersConvert;
import com.followba.store.dao.dto.SystemUsersDTO;
import com.followba.store.dao.mapper.SystemUsersMapper;
import com.followba.store.dao.po.SystemUsers;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class BizSystemUsersMapper {

    @Resource
    private SystemUsersMapper mapper;

    public SystemUsersDTO selectById(Long id) {
        return SystemUsersConvert.INSTANCE.toDTO(mapper.selectById(id));
    }

    public SystemUsersDTO selectByUsername(String username) {
        LambdaQueryWrapper<SystemUsers> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SystemUsers::getUsername, username)
                .eq(SystemUsers::getDeleted, AuthConstants.DEFAULT_NOT_DELETED)
                .last("LIMIT 1");
        return SystemUsersConvert.INSTANCE.toDTO(mapper.selectOne(wrapper));
    }

    public void updateById(SystemUsersDTO dto) {
        mapper.updateById(SystemUsersConvert.INSTANCE.toPO(dto));
    }

    public void insert(SystemUsersDTO dto) {
        SystemUsers po = SystemUsersConvert.INSTANCE.toPO(dto);
        mapper.insert(po);
        dto.setId(po.getId());
    }
}
