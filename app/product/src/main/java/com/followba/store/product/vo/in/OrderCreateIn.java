package com.followba.store.product.vo.in;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderCreateIn {

    private Long checkoutOrderId;

    private String remark;

    @NotBlank(message = "requestId 不能为空")
    private String requestId;
}
