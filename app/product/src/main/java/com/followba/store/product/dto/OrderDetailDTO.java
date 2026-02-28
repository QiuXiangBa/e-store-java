package com.followba.store.product.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDetailDTO {

    private Long orderId;

    private String orderNo;

    private Integer status;

    private String statusText;

    private Integer itemCount;

    private Integer totalAmount;

    private Integer payAmount;

    private String remark;

    private String cancelReason;

    private Date createTime;

    private Date paidTime;

    private Date cancelTime;

    private Date updateTime;

    private Boolean canCancel;

    private Boolean canPay;

    private List<OrderItemDTO> items;
}
