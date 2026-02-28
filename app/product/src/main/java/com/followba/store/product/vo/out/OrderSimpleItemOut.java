package com.followba.store.product.vo.out;

import lombok.Data;

@Data
public class OrderSimpleItemOut {

    private Long skuId;

    private String spuName;

    private String skuPicUrl;

    private Integer quantity;
}
