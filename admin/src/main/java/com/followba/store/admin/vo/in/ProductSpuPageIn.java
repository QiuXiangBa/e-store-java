package com.followba.store.admin.vo.in;

import com.followba.store.common.resp.PageReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductSpuPageIn extends PageReq {

    private String name;

    /** tab 类型（0-出售中，1-仓库中，2-已售罄，3-警戒库存，4-回收站） / Tab type. */
    private Integer tabType;

    private Long categoryId;

    private Long brandId;
}
