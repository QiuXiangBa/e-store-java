package com.followba.store.admin.vo.in;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductSpuUpdateStatusIn {

    @NotNull(message = "id 不能为空")
    private Long id;

    @NotNull(message = "status 不能为空")
    private Byte status;
}
