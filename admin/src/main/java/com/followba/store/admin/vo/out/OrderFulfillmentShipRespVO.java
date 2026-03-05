package com.followba.store.admin.vo.out;

import lombok.Data;

@Data
public class OrderFulfillmentShipRespVO {

    private Long fulfillmentId;

    private Integer status;

    private String statusText;
}
