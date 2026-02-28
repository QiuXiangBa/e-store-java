package com.followba.store.product.controller;

import com.followba.store.common.resp.Out;
import com.followba.store.product.convert.MallCheckoutConvert;
import com.followba.store.product.dto.CheckoutResultDTO;
import com.followba.store.product.service.MallCheckoutService;
import com.followba.store.product.vo.in.CheckoutCreateIn;
import com.followba.store.product.vo.out.CheckoutCreateOut;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mall/checkout")
@Validated
public class MallCheckoutController {

    @Resource
    private MallCheckoutService mallCheckoutService;

    @PostMapping("/create")
    public Out<CheckoutCreateOut> create(@Valid @RequestBody CheckoutCreateIn in) {
        CheckoutResultDTO resultDTO = mallCheckoutService.create(MallCheckoutConvert.INSTANCE.toCheckoutCreateDTO(in));
        return Out.success(MallCheckoutConvert.INSTANCE.toCheckoutCreateOut(resultDTO));
    }
}
