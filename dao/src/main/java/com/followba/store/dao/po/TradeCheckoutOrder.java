package com.followba.store.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 结算单头 / Trade checkout order.
 */
@Data
@TableName(value = "trade_checkout_order")
public class TradeCheckoutOrder {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "order_no")
    private String orderNo;

    @TableField(value = "user_id")
    private Long userId;

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
