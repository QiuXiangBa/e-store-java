package com.followba.store.admin.vo.in;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductCategoryPropertyListIn {

    @NotNull(message = "类目 ID 不能为空")
    private Long categoryId;

    private Integer propertyType;
}
