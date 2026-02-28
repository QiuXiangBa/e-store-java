package com.followba.store.product.vo.in;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartUpdateSelectedIn {

    @NotNull
    private Long cartId;

    @NotNull
    private Boolean selected;
}
