package com.followba.store.product.dto;

import lombok.Data;

@Data
public class OrderCancelDTO {

    private Long orderId;

    private String reason;
}
