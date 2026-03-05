package com.followba.store.admin.dto;

import lombok.Data;

@Data
public class OrderFulfillmentShipResultDTO {

    private Long fulfillmentId;

    private Integer status;

    private String statusText;
}
