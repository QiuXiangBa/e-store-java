package com.followba.store.product.vo.out;

import lombok.Data;

import java.util.Date;

@Data
public class OrderStatusOut {

    private Long orderId;

    private Integer status;

    private String statusText;

    private Date updateTime;
}
