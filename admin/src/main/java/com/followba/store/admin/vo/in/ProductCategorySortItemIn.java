package com.followba.store.admin.vo.in;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductCategorySortItemIn {

    @NotNull(message = "分类ID不能为空")
    private Long id;

    @NotNull(message = "排序不能为空")
    @Min(value = 0, message = "排序不能小于0")
    private Integer sort;
}

