package com.followba.store.admin.vo.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductPropertyValueSaveIn {

    private Long id;

    @NotNull(message = "规格属性 id 不能为空")
    private Long propertyId;

    @NotBlank(message = "规格值名称不能为空")
    private String name;

    @NotNull(message = "状态不能为空")
    private Byte status;

    private String remark;
}
