package com.followba.store.dao.biz;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.followba.store.dao.convert.TradeFulfillmentConvert;
import com.followba.store.dao.dto.TradeFulfillmentDTO;
import com.followba.store.dao.mapper.TradeFulfillmentMapper;
import com.followba.store.dao.po.TradeFulfillment;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

/**
 * 履约主表业务 Mapper / Fulfillment header biz mapper.
 */
@Component
public class BizTradeFulfillmentMapper {

    @Resource
    private TradeFulfillmentMapper mapper;

    public void insert(TradeFulfillmentDTO dto) {
        TradeFulfillment po = TradeFulfillmentConvert.INSTANCE.toPO(dto);
        mapper.insert(po);
        dto.setId(po.getId());
    }

    public TradeFulfillmentDTO selectByOrderId(Long orderId) {
        LambdaQueryWrapper<TradeFulfillment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TradeFulfillment::getOrderId, orderId);
        wrapper.last("limit 1");
        return TradeFulfillmentConvert.INSTANCE.toDTO(mapper.selectOne(wrapper));
    }

    public TradeFulfillmentDTO selectById(Long id) {
        return TradeFulfillmentConvert.INSTANCE.toDTO(mapper.selectById(id));
    }

    public void updateById(TradeFulfillmentDTO dto) {
        mapper.updateById(TradeFulfillmentConvert.INSTANCE.toPO(dto));
    }

    public int updateStatusByOrderIdAndFromStatus(Long orderId,
                                                   Integer fromStatus,
                                                   Integer toStatus,
                                                   String latestNode,
                                                   Date signedTime,
                                                   Date closedTime) {
        LambdaUpdateWrapper<TradeFulfillment> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(TradeFulfillment::getOrderId, orderId);
        wrapper.eq(TradeFulfillment::getStatus, fromStatus);
        wrapper.set(TradeFulfillment::getStatus, toStatus);
        wrapper.set(latestNode != null, TradeFulfillment::getLatestNode, latestNode);
        wrapper.set(signedTime != null, TradeFulfillment::getSignedTime, signedTime);
        wrapper.set(closedTime != null, TradeFulfillment::getClosedTime, closedTime);
        return mapper.update(null, wrapper);
    }

    public int updateStatusByOrderIdAndFromStatuses(Long orderId,
                                                     Set<Integer> fromStatuses,
                                                     Integer toStatus,
                                                     String latestNode,
                                                     Date signedTime,
                                                     Date closedTime) {
        LambdaUpdateWrapper<TradeFulfillment> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(TradeFulfillment::getOrderId, orderId);
        wrapper.in(TradeFulfillment::getStatus, fromStatuses);
        wrapper.set(TradeFulfillment::getStatus, toStatus);
        wrapper.set(latestNode != null, TradeFulfillment::getLatestNode, latestNode);
        wrapper.set(signedTime != null, TradeFulfillment::getSignedTime, signedTime);
        wrapper.set(closedTime != null, TradeFulfillment::getClosedTime, closedTime);
        return mapper.update(null, wrapper);
    }

    public int updateShipByOrderIdAndFromStatus(Long orderId,
                                                 Integer fromStatus,
                                                 Integer toStatus,
                                                 String logisticsCompanyCode,
                                                 String logisticsCompanyName,
                                                 String trackingNo,
                                                 String latestNode,
                                                 Date shippedTime) {
        LambdaUpdateWrapper<TradeFulfillment> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(TradeFulfillment::getOrderId, orderId);
        wrapper.eq(TradeFulfillment::getStatus, fromStatus);
        wrapper.set(TradeFulfillment::getStatus, toStatus);
        wrapper.set(TradeFulfillment::getLogisticsCompanyCode, logisticsCompanyCode);
        wrapper.set(TradeFulfillment::getLogisticsCompanyName, logisticsCompanyName);
        wrapper.set(TradeFulfillment::getTrackingNo, trackingNo);
        wrapper.set(TradeFulfillment::getLatestNode, latestNode);
        wrapper.set(TradeFulfillment::getShippedTime, shippedTime);
        return mapper.update(null, wrapper);
    }
}
