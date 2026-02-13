package com.followba.store.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 商品分类
 */
@Data
@TableName(value = "product_category")
public class ProductCategory {
    /**
     * 分类编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父分类编号
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 分类名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 移动端分类图
     */
    @TableField(value = "pic_url")
    private String picUrl;

    /**
     * PC 端分类图
     */
    @TableField(value = "big_pic_url")
    private String bigPicUrl;

    /**
     * 分类排序
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 开启状态
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