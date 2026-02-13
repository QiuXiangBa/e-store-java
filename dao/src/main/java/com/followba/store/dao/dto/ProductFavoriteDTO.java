package com.followba.store.dao.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProductFavoriteDTO {

    private Long id;

    private Long userId;

    private Long spuId;

    private String creator;

    private Date createTime;

    private String updater;

    private Date updateTime;

    private Boolean deleted;

    private Long tenantId;
}
