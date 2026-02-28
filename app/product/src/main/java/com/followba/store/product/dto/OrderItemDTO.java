package com.followba.store.product.dto;

import lombok.Data;

@Data
public class OrderItemDTO {

    private Long spuId;

    private Long skuId;

    private String spuName;

    private String skuPicUrl;

    private String skuProperties;

    private Integer price;

    private Integer quantity;

    private Integer lineAmount;
}
