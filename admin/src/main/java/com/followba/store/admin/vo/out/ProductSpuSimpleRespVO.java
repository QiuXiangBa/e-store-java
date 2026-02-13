package com.followba.store.admin.vo.out;

import lombok.Data;

@Data
public class ProductSpuSimpleRespVO {

    private Long id;

    private String name;

    private Integer price;

    private Integer stock;

    private Integer sort;
}
