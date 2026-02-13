package com.followba.store.admin.vo.out;

import lombok.Data;

import java.util.Date;

@Data
public class ProductFavoriteRespVO {

    private Long id;

    private Long userId;

    private Long spuId;

    private Date createTime;
}
