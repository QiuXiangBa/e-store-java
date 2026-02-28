package com.followba.store.dao.biz;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.followba.store.dao.convert.TradeCheckoutItemConvert;
import com.followba.store.dao.dto.TradeCheckoutItemDTO;
import com.followba.store.dao.mapper.TradeCheckoutItemMapper;
import com.followba.store.dao.po.TradeCheckoutItem;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BizTradeCheckoutItemMapper {

    @Resource
    private TradeCheckoutItemMapper mapper;

    public void insertBatch(List<TradeCheckoutItemDTO> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        mapper.batchInsert(list.stream().map(TradeCheckoutItemConvert.INSTANCE::toPO).toList());
    }

    public List<TradeCheckoutItemDTO> selectListByCheckoutOrderId(Long checkoutOrderId) {
        LambdaQueryWrapper<TradeCheckoutItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TradeCheckoutItem::getCheckoutOrderId, checkoutOrderId);
        wrapper.orderByAsc(TradeCheckoutItem::getId);
        return TradeCheckoutItemConvert.INSTANCE.toDTO(mapper.selectList(wrapper));
    }
}
