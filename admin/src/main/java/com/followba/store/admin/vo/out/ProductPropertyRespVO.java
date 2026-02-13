package com.followba.store.admin.vo.out;

import lombok.Data;

import java.util.Date;

@Data
public class ProductPropertyRespVO {

    private Long id;

    private String name;

    private Byte status;

    private String remark;

    private Date createTime;
}
