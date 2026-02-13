package com.followba.store.admin.vo.in;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductBrandUpdateIn extends ProductBrandBaseIn {

    @NotNull(message = "id 不能为空")
    private Long id;
}
