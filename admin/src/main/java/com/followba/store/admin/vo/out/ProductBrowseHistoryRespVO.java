package com.followba.store.admin.vo.out;

import lombok.Data;

import java.util.Date;

@Data
public class ProductBrowseHistoryRespVO {

    private Long id;

    private Long userId;

    private Long spuId;

    private Boolean userDeleted;

    private Date createTime;
}
