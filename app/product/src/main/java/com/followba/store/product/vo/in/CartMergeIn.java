package com.followba.store.product.vo.in;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CartMergeIn {

    @Valid
    @NotNull(message = "items 不能为空")
    private List<CartMergeItemIn> items;
}
