package com.followba.store.admin.controller;

import com.followba.store.admin.convert.OrderFulfillmentConvert;
import com.followba.store.admin.dto.OrderFulfillmentDetailDTO;
import com.followba.store.admin.dto.OrderFulfillmentShipResultDTO;
import com.followba.store.admin.service.OrderFulfillmentService;
import com.followba.store.admin.vo.in.OrderFulfillmentLogisticsNodeIn;
import com.followba.store.admin.vo.in.OrderFulfillmentShipIn;
import com.followba.store.admin.vo.out.CommonBooleanRespVO;
import com.followba.store.admin.vo.out.OrderFulfillmentDetailRespVO;
import com.followba.store.admin.vo.out.OrderFulfillmentShipRespVO;
import com.followba.store.common.resp.Out;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理端履约控制器 / Admin fulfillment controller.
 */
@RestController
@RequestMapping("/order/fulfillment")
@Validated
public class OrderFulfillmentController {

    @Resource
    private OrderFulfillmentService orderFulfillmentService;

    @PostMapping("/ship")
    public Out<OrderFulfillmentShipRespVO> ship(@Valid @RequestBody OrderFulfillmentShipIn in) {
        OrderFulfillmentShipResultDTO resultDTO = orderFulfillmentService.ship(
                OrderFulfillmentConvert.INSTANCE.toOrderFulfillmentShipDTO(in)
        );
        return Out.success(OrderFulfillmentConvert.INSTANCE.toOrderFulfillmentShipRespVO(resultDTO));
    }

    @PostMapping("/logistics-node")
    public Out<CommonBooleanRespVO> appendLogisticsNode(@Valid @RequestBody OrderFulfillmentLogisticsNodeIn in) {
        orderFulfillmentService.appendLogisticsNode(OrderFulfillmentConvert.INSTANCE.toOrderFulfillmentLogisticsNodeDTO(in));
        return Out.success(CommonBooleanRespVO.ok());
    }

    @GetMapping("/detail")
    public Out<OrderFulfillmentDetailRespVO> detail(@RequestParam("orderId") Long orderId) {
        OrderFulfillmentDetailDTO detailDTO = orderFulfillmentService.detail(orderId);
        return Out.success(OrderFulfillmentConvert.INSTANCE.toOrderFulfillmentDetailRespVO(detailDTO));
    }
}
