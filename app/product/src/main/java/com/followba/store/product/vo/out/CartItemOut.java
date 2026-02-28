package com.followba.store.product.vo.out;

import lombok.Data;

@Data
public class CartItemOut {

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
