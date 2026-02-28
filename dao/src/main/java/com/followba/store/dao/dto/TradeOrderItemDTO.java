package com.followba.store.dao.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TradeOrderItemDTO {

    private Long id;

    private Long orderId;

    private Long userId;

    private Long spuId;

    private Long skuId;

    private String spuName;

    private String skuPicUrl;

    private String skuProperties;

    private Integer price;

    private Integer quantity;

    private Integer lineAmount;

    private Date createTime;

    private Date updateTime;
}
