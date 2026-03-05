package com.followba.store.product.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OrderFulfillmentNodeDTO {

    private Integer fromStatus;

    private Integer toStatus;

    private Integer operateType;

    private String nodeDesc;

    private Date nodeTime;
}
