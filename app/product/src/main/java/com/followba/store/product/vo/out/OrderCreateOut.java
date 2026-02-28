package com.followba.store.product.vo.out;

import lombok.Data;

@Data
public class OrderCreateOut {

    private Long orderId;

    private String orderNo;

    private Integer status;

    private Integer itemCount;

    private Integer totalAmount;

    private Integer payAmount;
}
