package com.followba.store.product.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OrderPageItemDTO {

    private Long orderId;

    private String orderNo;

    private Integer status;

    private String statusText;

    private Integer payAmount;

    private Integer itemCount;

    private Date createTime;

    private OrderSimpleItemDTO firstItem;
}
