package com.followba.store.product.dto;

import lombok.Data;

import java.util.List;

@Data
public class CheckoutResultDTO {

    private Long checkoutOrderId;

    private String orderNo;

    private Integer itemCount;

    private Integer totalAmount;

    private Integer payAmount;

    private List<CheckoutItemDTO> items;
}
