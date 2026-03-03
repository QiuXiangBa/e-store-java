package com.followba.store.product.dto;

import lombok.Data;

@Data
public class OrderCloseDTO {

    private Long orderId;

    private String reason;
}
