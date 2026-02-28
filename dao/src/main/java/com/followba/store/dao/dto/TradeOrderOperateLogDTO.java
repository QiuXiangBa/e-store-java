package com.followba.store.dao.dto;

import lombok.Data;

@Data
public class TradeOrderOperateLogDTO {

    private Long id;

    private Long orderId;

    private Integer fromStatus;

    private Integer toStatus;

    private Integer operateType;

    private Integer operatorType;

    private Long operatorId;

    private String reason;
}
