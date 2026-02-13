package com.followba.store.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 商品品牌
 */
@Data
@TableName(value = "product_brand")
public class ProductBrand {
    /**
     * 品牌编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 品牌名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 品牌图片
     */
    @TableField(value = "pic_url")
    private String picUrl;

    /**
     * 品牌排序
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 品牌描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 状态
     */
    @TableField(value = "`status`")
    private Integer status;

    @TableField(value = "creator")
    private String creator;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "updater")
    private String updater;

    @TableField(value = "update_time")
    private Date updateTime;

    @TableField(value = "deleted")
    private Boolean deleted;

    @TableField(value = "tenant_id")
    private Long tenantId;
}