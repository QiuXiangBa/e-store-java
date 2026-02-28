package com.followba.store.dao.biz;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.followba.store.dao.convert.TradeCartConvert;
import com.followba.store.dao.dto.TradeCartDTO;
import com.followba.store.dao.mapper.TradeCartMapper;
import com.followba.store.dao.po.TradeCart;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class BizTradeCartMapper {

    @Resource
    private TradeCartMapper mapper;

    public void insert(TradeCartDTO dto) {
        TradeCart po = TradeCartConvert.INSTANCE.toPO(dto);
        mapper.insert(po);
        dto.setId(po.getId());
    }

    public void updateById(TradeCartDTO dto) {
        mapper.updateById(TradeCartConvert.INSTANCE.toPO(dto));
    }

    public TradeCartDTO selectById(Long id) {
        return TradeCartConvert.INSTANCE.toDTO(mapper.selectById(id));
    }

    public TradeCartDTO selectByUserIdAndSkuId(Long userId, Long skuId) {
        LambdaQueryWrapper<TradeCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TradeCart::getUserId, userId);
        wrapper.eq(TradeCart::getSkuId, skuId);
        wrapper.last("limit 1");
        return TradeCartConvert.INSTANCE.toDTO(mapper.selectOne(wrapper));
    }

    public List<TradeCartDTO> selectListByUserId(Long userId) {
        LambdaQueryWrapper<TradeCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TradeCart::getUserId, userId);
        wrapper.orderByDesc(TradeCart::getId);
        return TradeCartConvert.INSTANCE.toDTO(mapper.selectList(wrapper));
    }

    public List<TradeCartDTO> selectListByUserIdAndSelected(Long userId, Integer selected) {
        LambdaQueryWrapper<TradeCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TradeCart::getUserId, userId);
        wrapper.eq(TradeCart::getSelected, selected);
        wrapper.orderByDesc(TradeCart::getId);
        return TradeCartConvert.INSTANCE.toDTO(mapper.selectList(wrapper));
    }

    public void deleteById(Long id) {
        mapper.deleteById(id);
    }

    public void deleteByIds(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        mapper.deleteBatchIds(ids);
    }
}
