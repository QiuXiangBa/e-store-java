package com.followba.store.product.dto;

import lombok.Data;

@Data
public class OrderPaySuccessDTO {

    private String orderNo;

    private String payTxnNo;

    private Integer paidAmount;
}
