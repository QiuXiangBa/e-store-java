package com.followba.store.admin.vo.in;

import com.followba.store.common.resp.PageReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductBrowseHistoryPageIn extends PageReq {

    private Long userId;

    private Long spuId;

    private Boolean userDeleted;
}
