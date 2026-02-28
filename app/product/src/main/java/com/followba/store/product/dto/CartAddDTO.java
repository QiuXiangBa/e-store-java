package com.followba.store.product.dto;

import lombok.Data;

@Data
public class CartAddDTO {

    private Long skuId;

    private Integer quantity;
}
