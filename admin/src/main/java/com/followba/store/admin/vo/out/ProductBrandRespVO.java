package com.followba.store.admin.vo.out;

import lombok.Data;

import java.util.Date;

@Data
public class ProductBrandRespVO {

    private Long id;

    private String name;

    private String picUrl;

    private Integer sort;

    private String description;

    private Byte status;

    private Date createTime;
}
