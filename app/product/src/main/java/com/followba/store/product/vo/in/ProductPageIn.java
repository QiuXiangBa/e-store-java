package com.followba.store.product.vo.in;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class ProductPageIn {

    @Min(1)
    private Integer pageNum = 1;

    @Min(1)
    @Max(200)
    private Integer pageSize = 20;

    private String keyword;

    private Long categoryId;
}
