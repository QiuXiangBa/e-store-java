package com.followba.store.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 订单明细快照 / Trade order item snapshot.
 */
@Data
@TableName(value = "trade_order_item")
public class TradeOrderItem {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "order_id")
    private Long orderId;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "spu_id")
    private Long spuId;

    @TableField(value = "sku_id")
    private Long skuId;

    @TableField(value = "spu_name")
    private String spuName;

    @TableField(value = "sku_pic_url")
    private String skuPicUrl;

    @TableField(value = "sku_properties")
    private String skuProperties;

    @TableField(value = "price")
    private Integer price;

    @TableField(value = "quantity")
    private Integer quantity;

    @TableField(value = "line_amount")
    private Integer lineAmount;

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
