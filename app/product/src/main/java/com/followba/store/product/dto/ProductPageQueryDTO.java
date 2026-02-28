package com.followba.store.product.dto;

import lombok.Data;

@Data
public class ProductPageQueryDTO {

    private Integer pageNum;

    private Integer pageSize;

    private String keyword;

    private Long categoryId;
}
