package com.followba.store.product.vo.out;

import lombok.Data;

import java.util.Date;

@Data
public class OrderPageItemOut {

    private Long orderId;

    private String orderNo;

    private Integer status;

    private String statusText;

    private Integer payAmount;

    private Integer itemCount;

    private Date createTime;

    private OrderSimpleItemOut firstItem;
}
