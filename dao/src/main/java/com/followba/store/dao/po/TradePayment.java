package com.followba.store.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 支付单 / Trade payment.
 */
@Data
@TableName(value = "trade_payment")
public class TradePayment {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "checkout_order_id")
    private Long checkoutOrderId;

    @TableField(value = "stripe_payment_intent_id")
    private String stripePaymentIntentId;

    @TableField(value = "amount")
    private Integer amount;

    @TableField(value = "currency")
    private String currency;

    @TableField(value = "status")
    private Integer status;

    @TableField(value = "paid_at")
    private Date paidAt;

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
