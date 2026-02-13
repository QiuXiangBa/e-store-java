package com.followba.store.common.resp;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * 通用分页请求参数
 */
@Data
public class PageReq {

    @Min(value = 1, message = "pageNum 必须大于等于 1")
    private Integer pageNum = 1;

    @Min(value = 1, message = "pageSize 必须大于等于 1")
    @Max(value = 200, message = "pageSize 最大 200")
    private Integer pageSize = 20;
}
