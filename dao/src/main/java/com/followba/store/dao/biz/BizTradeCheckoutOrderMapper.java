package com.followba.store.dao.biz;

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
}
