package com.followba.store.product.vo.out;

import lombok.Data;

import java.util.List;

@Data
public class ProductAppSkuOut {

    private Long skuId;

    private Integer price;

    private Integer marketPrice;

    private Integer stock;

    private String picUrl;

    private List<String> properties;
}
