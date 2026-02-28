package com.followba.store.product.vo.out;

import lombok.Data;

import java.util.List;

@Data
public class CheckoutCreateOut {

    private Long checkoutOrderId;

    private String orderNo;

    private Integer itemCount;

    private Integer totalAmount;

    private Integer payAmount;

    private List<CheckoutItemOut> items;
}
