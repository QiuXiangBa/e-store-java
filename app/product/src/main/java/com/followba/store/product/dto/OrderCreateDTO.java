package com.followba.store.product.dto;

import lombok.Data;

@Data
public class OrderCreateDTO {

    private Long checkoutOrderId;

    private String remark;

    private String requestId;
}
