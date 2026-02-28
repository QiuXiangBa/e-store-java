package com.followba.store.product.dto;

import lombok.Data;

@Data
public class OrderPageQueryDTO {

    private Integer pageNum;

    private Integer pageSize;

    private Integer status;
}
