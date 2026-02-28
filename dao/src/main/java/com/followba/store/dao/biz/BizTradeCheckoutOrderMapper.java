package com.followba.store.dao.biz;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.followba.store.dao.convert.TradeCheckoutOrderConvert;
import com.followba.store.dao.dto.TradeCheckoutOrderDTO;
import com.followba.store.dao.mapper.TradeCheckoutOrderMapper;
import com.followba.store.dao.po.TradeCheckoutOrder;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class BizTradeCheckoutOrderMapper {

    @Resource
    private TradeCheckoutOrderMapper mapper;

    public void insert(TradeCheckoutOrderDTO dto) {
        TradeCheckoutOrder po = TradeCheckoutOrderConvert.INSTANCE.toPO(dto);
        mapper.insert(po);
        dto.setId(po.getId());
    }

    public TradeCheckoutOrderDTO selectById(Long id) {
        return TradeCheckoutOrderConvert.INSTANCE.toDTO(mapper.selectById(id));
    }

    public TradeCheckoutOrderDTO selectLatestByUserId(Long userId) {
        LambdaQueryWrapper<TradeCheckoutOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TradeCheckoutOrder::getUserId, userId);
        wrapper.orderByDesc(TradeCheckoutOrder::getId);
        wrapper.last("limit 1");
        return TradeCheckoutOrderConvert.INSTANCE.toDTO(mapper.selectOne(wrapper));
    }
}
