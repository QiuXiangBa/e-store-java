package com.followba.store.dao.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TradeOrderDTO {

    private Long id;

    private String orderNo;

    private String requestId;

    private Long userId;

    private Long checkoutOrderId;

    private Integer status;

    private Integer itemCount;

    private Integer totalAmount;

    private Integer payAmount;

    private String remark;

    private String cancelReason;

    private Date paidTime;

    private Date cancelTime;

    private Date closeTime;

    private Date createTime;

    private Date updateTime;
}
