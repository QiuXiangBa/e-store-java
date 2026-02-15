package com.followba.store.dao.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProductPropertyValueDTO {

    private Long id;

    private Long propertyId;

    private String name;

    private Integer status;

    private String creator;

    private Date createTime;

    private String updater;

    private Date updateTime;

    private Boolean deleted;

    private Long tenantId;

    private String remark;

    private String picUrl;
}
