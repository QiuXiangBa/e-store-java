package com.followba.store.payment.service.impl;

import com.followba.store.common.context.RequestContext;
import com.followba.store.common.exception.BizException;
import com.followba.store.dao.biz.BizTradeCheckoutOrderMapper;
import com.followba.store.dao.biz.BizTradePaymentMapper;
import com.followba.store.dao.constant.PaymentConstants;
import com.followba.store.dao.dto.TradeCheckoutOrderDTO;
import com.followba.store.dao.dto.TradePaymentDTO;
import com.followba.store.dao.enums.PaymentStatusEnum;
import com.followba.store.dao.enums.TradeCheckoutOrderStatusEnum;
import com.followba.store.payment.config.StripeProperties;
import com.followba.store.payment.dto.PaymentCreateIntentDTO;
import com.followba.store.payment.service.MallPaymentService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

/**
 * 支付服务实现 / Mall payment service implementation.
 */
@Service
public class MallPaymentServiceImpl implements MallPaymentService {

    @Resource
    private BizTradeCheckoutOrderMapper bizTradeCheckoutOrderMapper;

    @Resource
    private BizTradePaymentMapper bizTradePaymentMapper;

    @Resource
    private StripeProperties stripeProperties;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PaymentCreateIntentDTO createPaymentIntent(Long checkoutOrderId) {
        Long userId = getCurrentUserId();
        TradeCheckoutOrderDTO order = bizTradeCheckoutOrderMapper.selectById(checkoutOrderId);
        if (order == null) {
            throw new BizException(PaymentConstants.ORDER_NOT_EXISTS);
        }
        if (!Objects.equals(order.getUserId(), userId)) {
            throw new BizException(PaymentConstants.ORDER_NOT_BELONG_USER);
        }
        if (TradeCheckoutOrderStatusEnum.PAID.getCode().equals(order.getStatus())) {
            throw new BizException(PaymentConstants.ORDER_ALREADY_PAID);
        }
        if (!TradeCheckoutOrderStatusEnum.CREATED.getCode().equals(order.getStatus())
                && !TradeCheckoutOrderStatusEnum.PENDING_PAY.getCode().equals(order.getStatus())) {
            throw new BizException(PaymentConstants.ORDER_STATUS_INVALID);
        }

        // 幂等：已有 PENDING 支付单则从 Stripe 拉取 clientSecret 返回
        TradePaymentDTO existing = bizTradePaymentMapper.selectByCheckoutOrderId(checkoutOrderId);
        if (existing != null && PaymentStatusEnum.PENDING.getCode().equals(existing.getStatus())) {
            try {
                PaymentIntent retrieved = PaymentIntent.retrieve(existing.getStripePaymentIntentId());
                PaymentCreateIntentDTO dto = new PaymentCreateIntentDTO();
                dto.setClientSecret(retrieved.getClientSecret());
                dto.setPaymentIntentId(existing.getStripePaymentIntentId());
                return dto;
            } catch (Exception ignored) {
                // 拉取失败则新建
            }
        }

        String currency = stripeProperties.getCurrency() != null ? stripeProperties.getCurrency() : "usd";
        int amount = order.getPayAmount() != null ? order.getPayAmount() : 0;
        if (amount < 50) {
            amount = 50; // Stripe 最小金额 $0.50
        }

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount((long) amount)
                .setCurrency(currency)
                .putMetadata("checkout_order_id", String.valueOf(checkoutOrderId))
                .build();

        PaymentIntent intent = null;
        try {
            intent = PaymentIntent.create(params);
        } catch (Exception e) {
            throw new BizException(PaymentConstants.ORDER_STATUS_INVALID);
        }

        TradePaymentDTO paymentDTO = new TradePaymentDTO();
        paymentDTO.setCheckoutOrderId(checkoutOrderId);
        paymentDTO.setStripePaymentIntentId(intent.getId());
        paymentDTO.setAmount(amount);
        paymentDTO.setCurrency(currency);
        paymentDTO.setStatus(PaymentStatusEnum.PENDING.getCode());
        paymentDTO.setDeleted(false);
        paymentDTO.setTenantId(0L);
        bizTradePaymentMapper.insert(paymentDTO);

        bizTradeCheckoutOrderMapper.updateStatusById(checkoutOrderId, TradeCheckoutOrderStatusEnum.PENDING_PAY.getCode());

        PaymentCreateIntentDTO dto = new PaymentCreateIntentDTO();
        dto.setClientSecret(intent.getClientSecret());
        dto.setPaymentIntentId(intent.getId());
        return dto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleWebhook(String payload, String signature) {
        String secret = stripeProperties.getWebhookSecret();
        if (secret == null || secret.isBlank()) {
            throw new BizException(PaymentConstants.ORDER_STATUS_INVALID);
        }
        Event event;
        try {
            event = Webhook.constructEvent(payload, signature, secret);
        } catch (SignatureVerificationException e) {
            throw new BizException(PaymentConstants.ORDER_STATUS_INVALID);
        }

        String type = event.getType();
        StripeObject obj = event.getDataObjectDeserializer().getObject().orElse(null);
        if (obj == null) {
            return;
        }

        if ("payment_intent.succeeded".equals(type)) {
            handlePaymentIntentSucceeded((PaymentIntent) obj);
        } else if ("payment_intent.payment_failed".equals(type)) {
            handlePaymentIntentFailed((PaymentIntent) obj);
        } else if ("payment_intent.canceled".equals(type)) {
            handlePaymentIntentCanceled((PaymentIntent) obj);
        }
    }

    private void handlePaymentIntentSucceeded(PaymentIntent intent) {
        String piId = intent.getId();
        TradePaymentDTO payment = bizTradePaymentMapper.selectByStripePaymentIntentId(piId);
        if (payment == null) {
            return;
        }
        if (PaymentStatusEnum.isTerminal(payment.getStatus())) {
            return; // 幂等
        }
        bizTradePaymentMapper.updateStatusAndPaidAt(payment.getId(), PaymentStatusEnum.SUCCEEDED, new Date());
        bizTradeCheckoutOrderMapper.updateStatusById(payment.getCheckoutOrderId(), TradeCheckoutOrderStatusEnum.PAID.getCode());
    }

    private void handlePaymentIntentFailed(PaymentIntent intent) {
        String piId = intent.getId();
        TradePaymentDTO payment = bizTradePaymentMapper.selectByStripePaymentIntentId(piId);
        if (payment == null) {
            return;
        }
        if (PaymentStatusEnum.isTerminal(payment.getStatus())) {
            return;
        }
        bizTradePaymentMapper.updateStatus(payment.getId(), PaymentStatusEnum.FAILED);
        bizTradeCheckoutOrderMapper.updateStatusById(payment.getCheckoutOrderId(), TradeCheckoutOrderStatusEnum.PAY_FAILED.getCode());
    }

    private void handlePaymentIntentCanceled(PaymentIntent intent) {
        String piId = intent.getId();
        TradePaymentDTO payment = bizTradePaymentMapper.selectByStripePaymentIntentId(piId);
        if (payment == null) {
            return;
        }
        if (PaymentStatusEnum.isTerminal(payment.getStatus())) {
            return;
        }
        bizTradePaymentMapper.updateStatus(payment.getId(), PaymentStatusEnum.CANCELED);
        bizTradeCheckoutOrderMapper.updateStatusById(payment.getCheckoutOrderId(), TradeCheckoutOrderStatusEnum.CLOSED.getCode());
    }

    private Long getCurrentUserId() {
        String userId = RequestContext.getUserId();
        if (userId == null || userId.isBlank() || "visitor".equalsIgnoreCase(userId)) {
            throw new BizException(com.followba.store.dao.constant.ProductConstants.CART_USER_NOT_LOGIN);
        }
        try {
            return Long.valueOf(userId);
        } catch (NumberFormatException e) {
            throw new BizException(com.followba.store.dao.constant.ProductConstants.CART_USER_NOT_LOGIN);
        }
    }
}
