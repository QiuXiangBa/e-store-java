package com.followba.store.dao.biz;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.followba.store.dao.convert.TradeStockLogConvert;
import com.followba.store.dao.dto.TradeStockLogDTO;
import com.followba.store.dao.mapper.TradeStockLogMapper;
import com.followba.store.dao.po.TradeStockLog;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class BizTradeStockLogMapper {

    @Resource
    private TradeStockLogMapper mapper;

    public void insertBatch(List<TradeStockLogDTO> dtoList) {
        if (dtoList == null || dtoList.isEmpty()) {
            return;
        }
        mapper.batchInsert(dtoList.stream().map(TradeStockLogConvert.INSTANCE::toPO).toList());
    }

    public List<TradeStockLogDTO> selectListByBizAndSkuIds(Integer bizType, String bizNo, Set<Long> skuIds) {
        if (skuIds == null || skuIds.isEmpty()) {
            return List.of();
        }
        LambdaQueryWrapper<TradeStockLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TradeStockLog::getBizType, bizType);
        wrapper.eq(TradeStockLog::getBizNo, bizNo);
        wrapper.in(TradeStockLog::getSkuId, skuIds);
        return TradeStockLogConvert.INSTANCE.toDTO(mapper.selectList(wrapper));
    }
}
