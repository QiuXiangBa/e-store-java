package com.followba.store.dao.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProductBrowseHistoryDTO {

    private Long id;

    private Long spuId;

    private Long userId;

    private Boolean userDeleted;

    private String creator;

    private Date createTime;

    private String updater;

    private Date updateTime;

    private Boolean deleted;

    private Long tenantId;
}
