package com.followba.store.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 订单履约主表 / Trade fulfillment header.
 */
@Data
@TableName(value = "trade_fulfillment")
public class TradeFulfillment {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "order_id")
    private Long orderId;

    @TableField(value = "order_no")
    private String orderNo;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "status")
    private Integer status;

    @TableField(value = "logistics_company_code")
    private String logisticsCompanyCode;

    @TableField(value = "logistics_company_name")
    private String logisticsCompanyName;

    @TableField(value = "tracking_no")
    private String trackingNo;

    @TableField(value = "latest_node")
    private String latestNode;

    @TableField(value = "shipped_time")
    private Date shippedTime;

    @TableField(value = "signed_time")
    private Date signedTime;

    @TableField(value = "closed_time")
    private Date closedTime;

    @TableField(value = "version")
    private Integer version;

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
