package com.followba.store.admin.vo.in;

import com.followba.store.common.resp.PageReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductPropertyValuePageIn extends PageReq {

    private Long propertyId;

    private String name;

    private Byte status;
}
