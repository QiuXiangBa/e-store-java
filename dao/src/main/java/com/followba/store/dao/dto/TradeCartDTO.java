package com.followba.store.dao.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TradeCartDTO {

    private Long id;

    private Long userId;

    private Long spuId;

    private Long skuId;

    private Integer quantity;

    private Integer selected;

    private Integer skuPrice;

    private String spuName;

    private String skuPicUrl;

    private String skuProperties;

    private String creator;

    private Date createTime;

    private String updater;

    private Date updateTime;

    private Boolean deleted;

    private Long tenantId;
}
