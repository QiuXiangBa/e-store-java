package com.followba.store.payment.service;

import com.followba.store.payment.dto.PaymentCreateIntentDTO;

/**
 * 支付服务 / Mall payment service.
 */
public interface MallPaymentService {

    /**
     * 创建支付意图 / Create payment intent for checkout order.
     */
    PaymentCreateIntentDTO createPaymentIntent(Long checkoutOrderId);

    /**
     * 处理 Webhook 回调 / Handle Stripe webhook.
     */
    void handleWebhook(String payload, String signature);
}
