package com.followba.store.dao.biz;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.followba.store.dao.convert.TradeOrderItemConvert;
import com.followba.store.dao.dto.TradeOrderItemDTO;
import com.followba.store.dao.mapper.TradeOrderItemMapper;
import com.followba.store.dao.po.TradeOrderItem;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class BizTradeOrderItemMapper {

    @Resource
    private TradeOrderItemMapper mapper;

    public void insertBatch(List<TradeOrderItemDTO> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        mapper.batchInsert(list.stream().map(TradeOrderItemConvert.INSTANCE::toPO).toList());
    }

    public List<TradeOrderItemDTO> selectListByOrderId(Long orderId) {
        LambdaQueryWrapper<TradeOrderItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TradeOrderItem::getOrderId, orderId);
        wrapper.orderByAsc(TradeOrderItem::getId);
        return TradeOrderItemConvert.INSTANCE.toDTO(mapper.selectList(wrapper));
    }

    public List<TradeOrderItemDTO> selectListByOrderIds(Set<Long> orderIds) {
        if (orderIds == null || orderIds.isEmpty()) {
            return List.of();
        }
        LambdaQueryWrapper<TradeOrderItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(TradeOrderItem::getOrderId, orderIds);
        wrapper.orderByAsc(TradeOrderItem::getOrderId).orderByAsc(TradeOrderItem::getId);
        return TradeOrderItemConvert.INSTANCE.toDTO(mapper.selectList(wrapper));
    }
}
