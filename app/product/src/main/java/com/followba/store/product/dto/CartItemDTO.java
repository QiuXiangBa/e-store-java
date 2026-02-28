package com.followba.store.product.dto;

import lombok.Data;

@Data
public class CartItemDTO {

    private Long cartId;

    private Long spuId;

    private Long skuId;

    private String spuName;

    private String skuPicUrl;

    private String skuProperties;

    private Integer quantity;

    private Integer selected;

    private Integer skuPrice;

    private Integer lineAmount;
}
