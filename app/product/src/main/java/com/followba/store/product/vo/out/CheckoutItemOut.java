package com.followba.store.product.vo.out;

import lombok.Data;

@Data
public class CheckoutItemOut {

    private Long skuId;

    private Long spuId;

    private String spuName;

    private String skuPicUrl;

    private String skuProperties;

    private Integer price;

    private Integer quantity;

    private Integer lineAmount;
}
