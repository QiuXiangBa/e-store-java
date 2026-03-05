package com.followba.store.dao.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TradeFulfillmentLogDTO {

    private Long id;

    private Long fulfillmentId;

    private Long orderId;

    private Integer fromStatus;

    private Integer toStatus;

    private Integer operateType;

    private Integer operatorType;

    private Long operatorId;

    private String nodeDesc;

    private Date nodeTime;

    private Date createTime;

    private Date updateTime;
}
