package com.followba.store.dao.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProductCategoryDTO {

    private Long id;

    private Long parentId;

    private String name;

    private String picUrl;

    private String bigPicUrl;

    private Integer sort;

    private Integer status;

    private String creator;

    private Date createTime;

    private String updater;

    private Date updateTime;

    private Boolean deleted;

    private Long tenantId;
}
