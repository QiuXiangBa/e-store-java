package com.followba.store.admin.vo.out;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderFulfillmentDetailRespVO {

    private Long fulfillmentId;

    private Long orderId;

    private String orderNo;

    private Integer orderStatus;

    private String orderStatusText;

    private Integer fulfillmentStatus;

    private String fulfillmentStatusText;

    private String logisticsCompanyCode;

    private String logisticsCompanyName;

    private String trackingNo;

    private String latestNode;

    private Date shippedTime;

    private Date signedTime;

    private Date closedTime;

    private List<OrderFulfillmentNodeRespVO> nodes;
}
