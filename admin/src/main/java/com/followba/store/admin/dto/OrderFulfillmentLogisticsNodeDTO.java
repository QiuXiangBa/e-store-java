package com.followba.store.admin.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OrderFulfillmentLogisticsNodeDTO {

    private Long orderId;

    private Integer toStatus;

    private String nodeDesc;

    private Date nodeTime;
}
