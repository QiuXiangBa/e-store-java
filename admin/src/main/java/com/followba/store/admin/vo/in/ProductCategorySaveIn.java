package com.followba.store.admin.vo.in;

import com.followba.store.dao.enums.StatusEnums;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductCategorySaveIn {

    private Long id;

    @NotNull(message = "父分类不能为空")
    private Long parentId;

    @NotBlank(message = "分类名称不能为空")
    private String name;

    @NotBlank(message = "分类图片不能为空")
    private String picUrl;

    private String bigPicUrl;

    @NotNull(message = "排序不能为空")
    private Integer sort;

    @NotNull(message = "状态不能为空")
    private StatusEnums status;
}
