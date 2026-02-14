package com.followba.store.admin.vo.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductSpuDisplayPropertyIn {

    @NotNull(message = "展示属性 ID 不能为空")
    private Long propertyId;

    @NotBlank(message = "展示属性值不能为空")
    private String valueText;

    private Integer sort;
}
