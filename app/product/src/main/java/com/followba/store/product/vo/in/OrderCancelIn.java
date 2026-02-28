package com.followba.store.product.vo.in;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderCancelIn {

    @NotNull(message = "orderId 不能为空")
    private Long orderId;

    private String reason;
}
