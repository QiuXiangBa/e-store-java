package com.followba.store.admin.vo.in;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SystemFilePresignedDownloadUrlIn {

    @NotBlank(message = "对象地址不能为空")
    private String objectUrl;
}
