package com.followba.store.dao.biz;

import com.followba.store.dao.convert.TradeOrderOperateLogConvert;
import com.followba.store.dao.dto.TradeOrderOperateLogDTO;
import com.followba.store.dao.mapper.TradeOrderOperateLogMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class BizTradeOrderOperateLogMapper {

    @Resource
    private TradeOrderOperateLogMapper mapper;

    public void insert(TradeOrderOperateLogDTO dto) {
        mapper.insert(TradeOrderOperateLogConvert.INSTANCE.toPO(dto));
    }
}
