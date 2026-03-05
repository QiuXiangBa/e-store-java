package com.followba.store.admin.dto;

import lombok.Data;

@Data
public class OrderFulfillmentShipDTO {

    private Long orderId;

    private String logisticsCompanyCode;

    private String logisticsCompanyName;

    private String trackingNo;

    private String nodeDesc;
}
