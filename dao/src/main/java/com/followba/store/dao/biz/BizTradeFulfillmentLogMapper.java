package com.followba.store.dao.biz;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.followba.store.dao.convert.TradeFulfillmentLogConvert;
import com.followba.store.dao.dto.TradeFulfillmentLogDTO;
import com.followba.store.dao.mapper.TradeFulfillmentLogMapper;
import com.followba.store.dao.po.TradeFulfillmentLog;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 履约日志业务 Mapper / Fulfillment operate log biz mapper.
 */
@Component
public class BizTradeFulfillmentLogMapper {

    @Resource
    private TradeFulfillmentLogMapper mapper;

    public void insert(TradeFulfillmentLogDTO dto) {
        mapper.insert(TradeFulfillmentLogConvert.INSTANCE.toPO(dto));
    }

    public List<TradeFulfillmentLogDTO> selectListByFulfillmentId(Long fulfillmentId) {
        LambdaQueryWrapper<TradeFulfillmentLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TradeFulfillmentLog::getFulfillmentId, fulfillmentId);
        wrapper.orderByDesc(TradeFulfillmentLog::getNodeTime)
                .orderByDesc(TradeFulfillmentLog::getId);
        return TradeFulfillmentLogConvert.INSTANCE.toDTO(mapper.selectList(wrapper));
    }
}
