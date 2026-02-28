package com.followba.store.product.dto;

import lombok.Data;

@Data
public class CartMergeItemDTO {

    private Long skuId;

    private Integer quantity;
}
