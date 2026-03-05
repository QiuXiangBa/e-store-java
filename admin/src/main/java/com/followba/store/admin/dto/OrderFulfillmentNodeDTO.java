package com.followba.store.admin.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OrderFulfillmentNodeDTO {

    private Integer fromStatus;

    private Integer toStatus;

    private Integer operateType;

    private Integer operatorType;

    private Long operatorId;

    private String nodeDesc;

    private Date nodeTime;
}
