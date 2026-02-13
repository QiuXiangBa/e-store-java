package com.followba.store.admin.vo.in;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class ProductCategorySortBatchUpdateIn {

    @Valid
    @NotEmpty(message = "排序列表不能为空")
    private List<ProductCategorySortItemIn> items;
}

