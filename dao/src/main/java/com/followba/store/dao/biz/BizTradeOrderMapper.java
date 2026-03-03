package com.followba.store.dao.biz;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.followba.store.dao.convert.TradeOrderConvert;
import com.followba.store.dao.dto.PageDTO;
import com.followba.store.dao.dto.TradeOrderDTO;
import com.followba.store.dao.mapper.TradeOrderMapper;
import com.followba.store.dao.po.TradeOrder;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class BizTradeOrderMapper {

    @Resource
    private TradeOrderMapper mapper;

    public void insert(TradeOrderDTO dto) {
        TradeOrder po = TradeOrderConvert.INSTANCE.toPO(dto);
        mapper.insert(po);
        dto.setId(po.getId());
    }

    public TradeOrderDTO selectById(Long id) {
        return TradeOrderConvert.INSTANCE.toDTO(mapper.selectById(id));
    }

    public TradeOrderDTO selectByOrderNo(String orderNo) {
        LambdaQueryWrapper<TradeOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TradeOrder::getOrderNo, orderNo);
        wrapper.last("limit 1");
        return TradeOrderConvert.INSTANCE.toDTO(mapper.selectOne(wrapper));
    }

    public TradeOrderDTO selectByUserIdAndRequestId(Long userId, String requestId) {
        LambdaQueryWrapper<TradeOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TradeOrder::getUserId, userId);
        wrapper.eq(TradeOrder::getRequestId, requestId);
        wrapper.last("limit 1");
        return TradeOrderConvert.INSTANCE.toDTO(mapper.selectOne(wrapper));
    }

    public TradeOrderDTO selectByCheckoutOrderIdAndUserId(Long checkoutOrderId, Long userId) {
        LambdaQueryWrapper<TradeOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TradeOrder::getCheckoutOrderId, checkoutOrderId);
        wrapper.eq(TradeOrder::getUserId, userId);
        wrapper.last("limit 1");
        return TradeOrderConvert.INSTANCE.toDTO(mapper.selectOne(wrapper));
    }

    public void updateById(TradeOrderDTO dto) {
        mapper.updateById(TradeOrderConvert.INSTANCE.toPO(dto));
    }

    public int updateStatusByIdAndFromStatus(Long id,
                                             Integer fromStatus,
                                             Integer toStatus) {
        return updateStatusByIdAndFromStatus(id, fromStatus, toStatus, null, null, null);
    }

    public int updateStatusByIdAndFromStatus(Long id,
                                             Integer fromStatus,
                                             Integer toStatus,
                                             String cancelReason,
                                             java.util.Date cancelTime,
                                             java.util.Date closeTime) {
        LambdaUpdateWrapper<TradeOrder> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(TradeOrder::getId, id);
        wrapper.eq(TradeOrder::getStatus, fromStatus);
        wrapper.set(TradeOrder::getStatus, toStatus);
        wrapper.set(cancelReason != null, TradeOrder::getCancelReason, cancelReason);
        wrapper.set(cancelTime != null, TradeOrder::getCancelTime, cancelTime);
        wrapper.set(closeTime != null, TradeOrder::getCloseTime, closeTime);
        return mapper.update(null, wrapper);
    }

    public PageDTO<TradeOrderDTO> selectPageByUser(Long userId, Integer pageNum, Integer pageSize, Integer status) {
        Page<TradeOrder> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<TradeOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TradeOrder::getUserId, userId);
        wrapper.eq(status != null, TradeOrder::getStatus, status);
        wrapper.orderByDesc(TradeOrder::getId);
        Page<TradeOrder> iPage = mapper.selectPage(page, wrapper);
        return PageDTO.of(iPage.getTotal(), TradeOrderConvert.INSTANCE.toDTO(iPage.getRecords()));
    }
}
