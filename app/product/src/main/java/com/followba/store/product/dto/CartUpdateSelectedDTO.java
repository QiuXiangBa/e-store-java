package com.followba.store.product.dto;

import lombok.Data;

@Data
public class CartUpdateSelectedDTO {

    private Long cartId;

    private Boolean selected;
}
