package com.followba.store.admin.vo.in;

import lombok.Data;

@Data
public class ProductCategoryListIn {

    private String name;

    private Byte status;

    private Long parentId;
}
