package com.followba.store.dao.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TradePaymentDTO {

    private Long id;

    private Long checkoutOrderId;

    private String stripePaymentIntentId;

    private Integer amount;

    private String currency;

    private Integer status;

    private Date paidAt;

    private String creator;

    private Date createTime;

    private String updater;

    private Date updateTime;

    private Boolean deleted;

    private Long tenantId;
}
