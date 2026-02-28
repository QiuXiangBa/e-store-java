package com.followba.store.product.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OrderStatusDTO {

    private Long orderId;

    private Integer status;

    private String statusText;

    private Date updateTime;
}
