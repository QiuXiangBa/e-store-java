package com.followba.store.admin.vo.in;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductCommentUpdateVisibleIn {

    @NotNull(message = "id 不能为空")
    private Long id;

    @NotNull(message = "visible 不能为空")
    private Boolean visible;
}
