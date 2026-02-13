package com.followba.store.admin.vo.in;

import com.followba.store.common.resp.PageReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductCommentPageIn extends PageReq {

    private Long spuId;

    private Long userId;

    private Boolean visible;
}
