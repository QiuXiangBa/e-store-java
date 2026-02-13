package com.followba.store.admin.vo.out;

import lombok.Data;

@Data
public class ProductSkuRespVO {

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
}
