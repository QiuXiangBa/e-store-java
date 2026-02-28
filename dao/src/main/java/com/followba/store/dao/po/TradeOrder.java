package com.followba.store.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 订单主表 / Trade order header.
 */
@Data
@TableName(value = "trade_order")
public class TradeOrder {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "order_no")
    private String orderNo;

    @TableField(value = "request_id")
    private String requestId;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "checkout_order_id")
    private Long checkoutOrderId;

    @TableField(value = "status")
    private Integer status;

    @TableField(value = "item_count")
    private Integer itemCount;

    @TableField(value = "total_amount")
    private Integer totalAmount;

    @TableField(value = "pay_amount")
    private Integer payAmount;

    @TableField(value = "remark")
    private String remark;

    @TableField(value = "cancel_reason")
    private String cancelReason;

    @TableField(value = "paid_time")
    private Date paidTime;

    @TableField(value = "cancel_time")
    private Date cancelTime;

    @TableField(value = "close_time")
    private Date closeTime;

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
