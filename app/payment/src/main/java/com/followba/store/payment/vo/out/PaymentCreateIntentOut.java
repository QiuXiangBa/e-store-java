package com.followba.store.payment.vo.out;

import lombok.Data;

/**
 * 创建支付意图出参 / Payment create intent output.
 */
@Data
public class PaymentCreateIntentOut {

    private String clientSecret;

    private String paymentIntentId;
}
