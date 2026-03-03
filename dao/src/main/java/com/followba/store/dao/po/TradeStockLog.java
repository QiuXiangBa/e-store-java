package com.followba.store.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 库存变更流水 / Trade stock change log.
 */
@Data
@TableName(value = "trade_stock_log")
public class TradeStockLog {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "biz_type")
    private Integer bizType;

    @TableField(value = "biz_no")
    private String bizNo;

    @TableField(value = "order_id")
    private Long orderId;

    @TableField(value = "sku_id")
    private Long skuId;

    @TableField(value = "change_qty")
    private Integer changeQty;

    @TableField(value = "change_reason")
    private String changeReason;

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
