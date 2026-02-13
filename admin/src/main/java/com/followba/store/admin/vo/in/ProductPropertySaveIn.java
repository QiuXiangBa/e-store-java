package com.followba.store.admin.vo.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductPropertySaveIn {

    private Long id;

    @NotBlank(message = "规格名称不能为空")
    private String name;

    @NotNull(message = "状态不能为空")
    private Byte status;

    private String remark;
}
