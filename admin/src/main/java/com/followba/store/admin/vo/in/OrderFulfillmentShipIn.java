package com.followba.store.admin.vo.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderFulfillmentShipIn {

    @NotNull(message = "orderId 不能为空")
    private Long orderId;

    @NotBlank(message = "logisticsCompanyCode 不能为空")
    private String logisticsCompanyCode;

    @NotBlank(message = "logisticsCompanyName 不能为空")
    private String logisticsCompanyName;

    @NotBlank(message = "trackingNo 不能为空")
    private String trackingNo;

    @NotBlank(message = "nodeDesc 不能为空")
    private String nodeDesc;
}
