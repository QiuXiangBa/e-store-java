package com.followba.store.product.dto;

import lombok.Data;

@Data
public class CheckoutItemDTO {

    private Long skuId;

    private Long spuId;

    private String spuName;

    private String skuPicUrl;

    private String skuProperties;

    private Integer price;

    private Integer quantity;

    private Integer lineAmount;
}
