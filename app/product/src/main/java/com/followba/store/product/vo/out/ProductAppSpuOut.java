package com.followba.store.product.vo.out;

import lombok.Data;

@Data
public class ProductAppSpuOut {

    private Long spuId;

    private String name;

    private String picUrl;

    private Integer price;

    private Integer marketPrice;

    private Integer salesCount;
}
