package com.followba.store.dao.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TradeStockLogDTO {

    private Long id;

    private Integer bizType;

    private String bizNo;

    private Long orderId;

    private Long skuId;

    private Integer changeQty;

    private String changeReason;

    private Date createTime;

    private Date updateTime;
}
