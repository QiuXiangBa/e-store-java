package com.followba.store.product.dto;

import lombok.Data;

@Data
public class OrderSimpleItemDTO {

    private Long skuId;

    private String spuName;

    private String skuPicUrl;

    private Integer quantity;
}
