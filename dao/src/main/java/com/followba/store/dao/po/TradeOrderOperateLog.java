package com.followba.store.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 订单操作日志 / Trade order operate log.
 */
@Data
@TableName(value = "trade_order_operate_log")
public class TradeOrderOperateLog {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "order_id")
    private Long orderId;

    @TableField(value = "from_status")
    private Integer fromStatus;

    @TableField(value = "to_status")
    private Integer toStatus;

    @TableField(value = "operate_type")
    private Integer operateType;

    @TableField(value = "operator_type")
    private Integer operatorType;

    @TableField(value = "operator_id")
    private Long operatorId;

    @TableField(value = "reason")
    private String reason;

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
