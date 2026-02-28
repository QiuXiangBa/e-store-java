package com.followba.store.product.dto;

import lombok.Data;

@Data
public class OrderCreateResultDTO {

    private Long orderId;

    private String orderNo;

    private Integer status;

    private Integer itemCount;

    private Integer totalAmount;

    private Integer payAmount;
}
