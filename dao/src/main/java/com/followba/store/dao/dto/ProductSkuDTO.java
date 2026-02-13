package com.followba.store.dao.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProductSkuDTO {

    private Long id;

    private Long spuId;

    private String properties;

    private Integer price;

    private Integer marketPrice;

    private Integer costPrice;

    private String barCode;

    private String picUrl;

    private Integer stock;

    private Double weight;

    private Double volume;

    private Integer subCommissionFirstPrice;

    private Integer subCommissionSecondPrice;

    private Integer salesCount;

    private String creator;

    private Date createTime;

    private String updater;

    private Date updateTime;

    private Byte deleted;

    private Long tenantId;
}
