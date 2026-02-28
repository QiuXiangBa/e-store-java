package com.followba.store.dao.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TradeCheckoutItemDTO {

    private Long id;

    private Long checkoutOrderId;

    private Long userId;

    private Long spuId;

    private Long skuId;

    private String spuName;

    private String skuPicUrl;

    private String skuProperties;

    private Integer price;

    private Integer quantity;

    private Integer lineAmount;

    private String creator;

    private Date createTime;

    private String updater;

    private Date updateTime;

    private Boolean deleted;

    private Long tenantId;
}
