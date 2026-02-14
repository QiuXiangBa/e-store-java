package com.followba.store.admin.vo.in;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ProductCategoryPropertySaveIn {

    @NotNull(message = "类目 ID 不能为空")
    private Long categoryId;

    @Valid
    @NotEmpty(message = "类目属性绑定列表不能为空")
    private List<Item> items;

    @Data
    public static class Item {

        @NotNull(message = "属性 ID 不能为空")
        private Long propertyId;

        @NotNull(message = "启用状态不能为空")
        private Boolean enabled;

        @NotNull(message = "必填状态不能为空")
        private Boolean required;

        @NotNull(message = "排序不能为空")
        private Integer sort;
    }
}
