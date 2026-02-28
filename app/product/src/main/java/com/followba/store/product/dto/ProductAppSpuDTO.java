package com.followba.store.product.dto;

import lombok.Data;

@Data
public class ProductAppSpuDTO {

    private Long spuId;

    private String name;

    private String picUrl;

    private Integer price;

    private Integer marketPrice;

    private Integer salesCount;
}
