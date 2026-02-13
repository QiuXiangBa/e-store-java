package com.followba.store.admin.vo.in;

import com.followba.store.common.resp.PageReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductSpuPageIn extends PageReq {

    private String name;

    private Byte status;

    private Long categoryId;

    private Integer brandId;
}
