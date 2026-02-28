package com.followba.store.product.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductAppSkuDTO {

    private Long skuId;

    private Integer price;

    private Integer marketPrice;

    private Integer stock;

    private String picUrl;

    private List<String> properties;
}
