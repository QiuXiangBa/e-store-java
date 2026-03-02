package com.followba.store.payment.dto;

import lombok.Data;

/**
 * 创建支付意图结果 DTO / Payment create intent result DTO.
 */
@Data
public class PaymentCreateIntentDTO {

    private String clientSecret;

    private String paymentIntentId;
}
