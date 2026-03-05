package com.followba.store.dao.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TradeFulfillmentDTO {

    private Long id;

    private Long orderId;

    private String orderNo;

    private Long userId;

    private Integer status;

    private String logisticsCompanyCode;

    private String logisticsCompanyName;

    private String trackingNo;

    private String latestNode;

    private Date shippedTime;

    private Date signedTime;

    private Date closedTime;

    private Integer version;

    private Date createTime;

    private Date updateTime;
}
