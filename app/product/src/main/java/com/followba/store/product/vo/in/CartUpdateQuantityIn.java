package com.followba.store.product.vo.in;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartUpdateQuantityIn {

    @NotNull
    private Long cartId;

    @NotNull
    @Min(1)
    private Integer quantity;
}
