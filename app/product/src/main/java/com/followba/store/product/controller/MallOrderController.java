package com.followba.store.product.controller;

import com.followba.store.common.resp.Out;
import com.followba.store.common.resp.PageResp;
import com.followba.store.product.convert.MallOrderConvert;
import com.followba.store.product.dto.OrderCreateResultDTO;
import com.followba.store.product.dto.OrderDetailDTO;
import com.followba.store.product.dto.OrderPageItemDTO;
import com.followba.store.product.dto.OrderPageQueryDTO;
import com.followba.store.product.dto.OrderStatusDTO;
import com.followba.store.product.service.MallOrderService;
import com.followba.store.product.vo.in.OrderCancelIn;
import com.followba.store.product.vo.in.OrderCloseIn;
import com.followba.store.product.vo.in.OrderCreateIn;
import com.followba.store.product.vo.in.OrderPageIn;
import com.followba.store.product.vo.in.OrderPaySuccessIn;
import com.followba.store.product.vo.out.CommonBooleanOut;
import com.followba.store.product.vo.out.OrderCreateOut;
import com.followba.store.product.vo.out.OrderDetailOut;
import com.followba.store.product.vo.out.OrderPageItemOut;
import com.followba.store.product.vo.out.OrderStatusOut;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mall/order")
@Validated
public class MallOrderController {

    @Resource
    private MallOrderService mallOrderService;

    @PostMapping("/create")
    public Out<OrderCreateOut> create(@Valid @RequestBody OrderCreateIn in) {
        OrderCreateResultDTO resultDTO = mallOrderService.create(MallOrderConvert.INSTANCE.toOrderCreateDTO(in));
        return Out.success(MallOrderConvert.INSTANCE.toOrderCreateOut(resultDTO));
    }

    @GetMapping("/page")
    public Out<PageResp<OrderPageItemOut>> page(@Valid OrderPageIn in) {
        OrderPageQueryDTO queryDTO = MallOrderConvert.INSTANCE.toOrderPageQueryDTO(in);
        PageResp<OrderPageItemDTO> pageDTO = mallOrderService.page(queryDTO);
        return Out.success(PageResp.of(pageDTO.getTotal(), MallOrderConvert.INSTANCE.toOrderPageItemOutList(pageDTO.getList())));
    }

    @GetMapping("/detail")
    public Out<OrderDetailOut> detail(@RequestParam("id") Long id) {
        OrderDetailDTO detailDTO = mallOrderService.detail(id);
        return Out.success(MallOrderConvert.INSTANCE.toOrderDetailOut(detailDTO));
    }

    @GetMapping("/status")
    public Out<OrderStatusOut> status(@RequestParam("id") Long id) {
        OrderStatusDTO statusDTO = mallOrderService.status(id);
        return Out.success(MallOrderConvert.INSTANCE.toOrderStatusOut(statusDTO));
    }

    @PostMapping("/cancel")
    public Out<CommonBooleanOut> cancel(@Valid @RequestBody OrderCancelIn in) {
        mallOrderService.cancel(MallOrderConvert.INSTANCE.toOrderCancelDTO(in));
        return Out.success(CommonBooleanOut.ok());
    }

    @PostMapping("/close")
    public Out<CommonBooleanOut> close(@Valid @RequestBody OrderCloseIn in) {
        mallOrderService.close(MallOrderConvert.INSTANCE.toOrderCloseDTO(in));
        return Out.success(CommonBooleanOut.ok());
    }

    @PostMapping("/pay-success")
    public Out<CommonBooleanOut> paySuccess(@Valid @RequestBody OrderPaySuccessIn in) {
        mallOrderService.paySuccess(MallOrderConvert.INSTANCE.toOrderPaySuccessDTO(in));
        return Out.success(CommonBooleanOut.ok());
    }
}
