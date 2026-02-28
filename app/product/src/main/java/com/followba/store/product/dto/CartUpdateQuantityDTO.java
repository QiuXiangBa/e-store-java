package com.followba.store.product.dto;

import lombok.Data;

@Data
public class CartUpdateQuantityDTO {

    private Long cartId;

    private Integer quantity;
}
