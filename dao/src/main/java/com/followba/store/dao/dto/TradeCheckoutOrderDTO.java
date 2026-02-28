package com.followba.store.dao.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TradeCheckoutOrderDTO {

    private Long id;

    private String orderNo;

    private Long userId;

    private Integer status;

    private Integer itemCount;

    private Integer totalAmount;

    private Integer payAmount;

    private String remark;

    private String creator;

    private Date createTime;

    private String updater;

    private Date updateTime;

    private Boolean deleted;

    private Long tenantId;
}
