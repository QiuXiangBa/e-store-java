package com.followba.store.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 购物车 / Trade cart.
 */
@Data
@TableName(value = "trade_cart")
public class TradeCart {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "spu_id")
    private Long spuId;

    @TableField(value = "sku_id")
    private Long skuId;

    @TableField(value = "quantity")
    private Integer quantity;

    @TableField(value = "selected")
    private Integer selected;

    @TableField(value = "sku_price")
    private Integer skuPrice;

    @TableField(value = "spu_name")
    private String spuName;

    @TableField(value = "sku_pic_url")
    private String skuPicUrl;

    @TableField(value = "sku_properties")
    private String skuProperties;

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
