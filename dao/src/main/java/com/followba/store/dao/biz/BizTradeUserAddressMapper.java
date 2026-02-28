package com.followba.store.dao.biz;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.followba.store.dao.constant.AuthConstants;
import com.followba.store.dao.convert.TradeUserAddressConvert;
import com.followba.store.dao.dto.TradeUserAddressDTO;
import com.followba.store.dao.enums.TradeUserAddressDefaultEnum;
import com.followba.store.dao.mapper.TradeUserAddressMapper;
import com.followba.store.dao.po.TradeUserAddress;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BizTradeUserAddressMapper {

    @Resource
    private TradeUserAddressMapper mapper;

    public void insert(TradeUserAddressDTO dto) {
        TradeUserAddress po = TradeUserAddressConvert.INSTANCE.toPO(dto);
        mapper.insert(po);
        dto.setId(po.getId());
    }

    public void updateById(TradeUserAddressDTO dto) {
        mapper.updateById(TradeUserAddressConvert.INSTANCE.toPO(dto));
    }

    public TradeUserAddressDTO selectById(Long id) {
        return TradeUserAddressConvert.INSTANCE.toDTO(mapper.selectById(id));
    }

    public List<TradeUserAddressDTO> selectListByUserId(Long userId) {
        LambdaQueryWrapper<TradeUserAddress> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TradeUserAddress::getUserId, userId)
                .eq(TradeUserAddress::getDeleted, AuthConstants.DEFAULT_NOT_DELETED)
                .orderByDesc(TradeUserAddress::getIsDefault)
                .orderByDesc(TradeUserAddress::getId);
        return TradeUserAddressConvert.INSTANCE.toDTO(mapper.selectList(wrapper));
    }

    public TradeUserAddressDTO selectDefaultByUserId(Long userId) {
        LambdaQueryWrapper<TradeUserAddress> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TradeUserAddress::getUserId, userId)
                .eq(TradeUserAddress::getDeleted, AuthConstants.DEFAULT_NOT_DELETED)
                .eq(TradeUserAddress::getIsDefault, TradeUserAddressDefaultEnum.DEFAULT.getCode())
                .last("limit 1");
        return TradeUserAddressConvert.INSTANCE.toDTO(mapper.selectOne(wrapper));
    }

    public long countByUserId(Long userId) {
        LambdaQueryWrapper<TradeUserAddress> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TradeUserAddress::getUserId, userId)
                .eq(TradeUserAddress::getDeleted, AuthConstants.DEFAULT_NOT_DELETED);
        return mapper.selectCount(wrapper);
    }

    public void clearDefaultByUserId(Long userId) {
        LambdaUpdateWrapper<TradeUserAddress> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(TradeUserAddress::getUserId, userId)
                .eq(TradeUserAddress::getDeleted, AuthConstants.DEFAULT_NOT_DELETED)
                .set(TradeUserAddress::getIsDefault, TradeUserAddressDefaultEnum.NOT_DEFAULT.getCode());
        mapper.update(null, wrapper);
    }

    public void setDefaultById(Long id) {
        LambdaUpdateWrapper<TradeUserAddress> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(TradeUserAddress::getId, id)
                .eq(TradeUserAddress::getDeleted, AuthConstants.DEFAULT_NOT_DELETED)
                .set(TradeUserAddress::getIsDefault, TradeUserAddressDefaultEnum.DEFAULT.getCode());
        mapper.update(null, wrapper);
    }

    public void deleteById(Long id) {
        mapper.deleteById(id);
    }
}
