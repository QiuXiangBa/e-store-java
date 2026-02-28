package com.followba.store.auth.vo.in;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddressSetDefaultIn {

    @NotNull(message = "id 不能为空")
    private Long id;
}
