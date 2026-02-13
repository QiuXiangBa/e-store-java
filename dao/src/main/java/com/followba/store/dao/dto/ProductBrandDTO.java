package com.followba.store.dao.dto;

import lombok.Data;

import java.util.Date;

/**
 * 商品品牌
 */
@Data
public class ProductBrandDTO {
    /**
     * 品牌编号
     */
    private Long id;

    /**
     * 品牌名称
     */
    private String name;

    /**
     * 品牌图片
     */
    private String picUrl;

    /**
     * 品牌排序
     */
    private Integer sort;

    /**
     * 品牌描述
     */
    private String description;

    /**
     * 状态
     */
    private Integer status;

    private String creator;

    private Date createTime;

    private String updater;

    private Date updateTime;

    private Long tenantId;
}