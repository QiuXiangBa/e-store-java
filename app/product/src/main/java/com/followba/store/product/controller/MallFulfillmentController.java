package com.followba.store.product.controller;

import com.followba.store.common.resp.Out;
import com.followba.store.product.convert.MallFulfillmentConvert;
import com.followba.store.product.dto.OrderFulfillmentDetailDTO;
import com.followba.store.product.service.MallFulfillmentService;
import com.followba.store.product.vo.in.OrderReceiveIn;
import com.followba.store.product.vo.out.CommonBooleanOut;
import com.followba.store.product.vo.out.OrderFulfillmentDetailOut;
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
 * C 端履约控制器 / Mall fulfillment controller.
 */
@RestController
@RequestMapping("/mall/order")
@Validated
public class MallFulfillmentController {

    @Resource
    private MallFulfillmentService mallFulfillmentService;

    @GetMapping("/fulfillment/detail")
    public Out<OrderFulfillmentDetailOut> detail(@RequestParam("orderId") Long orderId) {
        OrderFulfillmentDetailDTO detailDTO = mallFulfillmentService.detail(orderId);
        return Out.success(MallFulfillmentConvert.INSTANCE.toOrderFulfillmentDetailOut(detailDTO));
    }

    @PostMapping("/receive")
    public Out<CommonBooleanOut> receive(@Valid @RequestBody OrderReceiveIn in) {
        mallFulfillmentService.receive(MallFulfillmentConvert.INSTANCE.toOrderReceiveDTO(in));
        return Out.success(CommonBooleanOut.ok());
    }
}
