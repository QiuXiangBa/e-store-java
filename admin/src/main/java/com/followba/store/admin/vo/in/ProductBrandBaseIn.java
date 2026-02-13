package com.followba.store.admin.vo.in;

import com.followba.store.dao.enums.StatusEnums;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductBrandBaseIn {

    @NotBlank(message = "品牌名称不能为空")
    private String name;

    @NotBlank(message = "品牌图片不能为空")
    private String picUrl;

    @NotNull(message = "排序不能为空")
    private Integer sort;

    private String description;

    @NotNull(message = "状态不能为空")
    private StatusEnums status;
}
