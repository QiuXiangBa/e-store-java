package com.followba.store.admin.vo.out;

import lombok.Data;

import java.util.Date;

@Data
public class ProductPropertyValueRespVO {

    private Long id;

    private Long propertyId;

    private String name;

    private Byte status;

    private String remark;

    private String picUrl;

    private Date createTime;
}
