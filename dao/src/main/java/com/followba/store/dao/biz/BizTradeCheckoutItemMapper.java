package com.followba.store.dao.biz;

import com.followba.store.dao.convert.TradeCheckoutItemConvert;
import com.followba.store.dao.dto.TradeCheckoutItemDTO;
import com.followba.store.dao.mapper.TradeCheckoutItemMapper;
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
}
