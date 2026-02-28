package com.followba.store.product.vo.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderPaySuccessIn {

    @NotBlank(message = "orderNo 不能为空")
    private String orderNo;

    private String payTxnNo;

    @NotNull(message = "paidAmount 不能为空")
    private Integer paidAmount;
}
