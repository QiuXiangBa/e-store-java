package com.followba.store.dao.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProductPropertyDTO {

    private Long id;

    private String name;

    private Integer status;

    private String creator;

    private Date createTime;

    private String updater;

    private Date updateTime;

    private Boolean deleted;

    private Long tenantId;

    private String remark;
}
