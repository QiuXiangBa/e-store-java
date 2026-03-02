package com.followba.store.payment.vo.in;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 创建支付意图入参 / Payment create intent input.
 */
@Data
public class PaymentCreateIntentIn {

    @NotNull(message = "结算单ID不能为空")
    private Long checkoutOrderId;
}
