package com.followba.store.admin.vo.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class OrderFulfillmentLogisticsNodeIn {

    @NotNull(message = "orderId 不能为空")
    private Long orderId;

    @NotNull(message = "toStatus 不能为空")
    private Integer toStatus;

    @NotBlank(message = "nodeDesc 不能为空")
    private String nodeDesc;

    private Date nodeTime;
}
