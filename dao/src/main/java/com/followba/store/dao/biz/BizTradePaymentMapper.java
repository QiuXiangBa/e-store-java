package com.followba.store.dao.biz;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.followba.store.dao.convert.TradePaymentConvert;
import com.followba.store.dao.dto.TradePaymentDTO;
import com.followba.store.dao.enums.PaymentStatusEnum;
import com.followba.store.dao.mapper.TradePaymentMapper;
import com.followba.store.dao.po.TradePayment;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 支付单业务 Mapper / Payment biz mapper.
 */
@Component
public class BizTradePaymentMapper {

    @Resource
    private TradePaymentMapper mapper;

    public void insert(TradePaymentDTO dto) {
        TradePayment po = TradePaymentConvert.INSTANCE.toPO(dto);
        mapper.insert(po);
        dto.setId(po.getId());
    }

    public TradePaymentDTO selectByCheckoutOrderId(Long checkoutOrderId) {
        LambdaQueryWrapper<TradePayment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TradePayment::getCheckoutOrderId, checkoutOrderId);
        wrapper.eq(TradePayment::getDeleted, false);
        TradePayment po = mapper.selectOne(wrapper);
        return po == null ? null : TradePaymentConvert.INSTANCE.toDTO(po);
    }

    public TradePaymentDTO selectByStripePaymentIntentId(String stripePaymentIntentId) {
        LambdaQueryWrapper<TradePayment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TradePayment::getStripePaymentIntentId, stripePaymentIntentId);
        wrapper.eq(TradePayment::getDeleted, false);
        TradePayment po = mapper.selectOne(wrapper);
        return po == null ? null : TradePaymentConvert.INSTANCE.toDTO(po);
    }

    public void updateStatusAndPaidAt(Long id, PaymentStatusEnum status, Date paidAt) {
        TradePayment po = new TradePayment();
        po.setId(id);
        po.setStatus(status.getCode());
        po.setPaidAt(paidAt);
        mapper.updateById(po);
    }

    public void updateStatus(Long id, PaymentStatusEnum status) {
        TradePayment po = new TradePayment();
        po.setId(id);
        po.setStatus(status.getCode());
        mapper.updateById(po);
    }

    /**
     * 更新支付意图信息并重置为待支付状态 / Refresh payment intent and reset to pending.
     */
    public void updateForNewIntent(Long id, String stripePaymentIntentId, Integer amount, String currency) {
        TradePayment po = new TradePayment();
        po.setId(id);
        po.setStripePaymentIntentId(stripePaymentIntentId);
        po.setAmount(amount);
        po.setCurrency(currency);
        po.setStatus(PaymentStatusEnum.PENDING.getCode());
        po.setPaidAt(null);
        mapper.updateById(po);
    }
}
