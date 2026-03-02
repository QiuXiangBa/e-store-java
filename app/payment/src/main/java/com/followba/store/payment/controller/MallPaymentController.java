package com.followba.store.payment.controller;

import com.followba.store.common.resp.Out;
import com.followba.store.payment.convert.MallPaymentConvert;
import com.followba.store.payment.dto.PaymentCreateIntentDTO;
import com.followba.store.payment.service.MallPaymentService;
import com.followba.store.payment.vo.in.PaymentCreateIntentIn;
import com.followba.store.payment.vo.out.PaymentCreateIntentOut;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支付控制器 / Mall payment controller.
 */
@RestController
@RequestMapping("/mall/payment")
@Validated
public class MallPaymentController {

    @Resource
    private MallPaymentService mallPaymentService;

    @PostMapping("/create-intent")
    public Out<PaymentCreateIntentOut> createIntent(@Valid @RequestBody PaymentCreateIntentIn in) {
        PaymentCreateIntentDTO dto = mallPaymentService.createPaymentIntent(in.getCheckoutOrderId());
        return Out.success(MallPaymentConvert.INSTANCE.toPaymentCreateIntentOut(dto));
    }

    /**
     * Stripe Webhook 回调，需排除鉴权 / Stripe webhook callback, excluded from auth.
     */
    @PostMapping("/webhook")
    public String webhook(@RequestBody String payload,
                         @RequestHeader(value = "Stripe-Signature", required = false) String signature) {
        mallPaymentService.handleWebhook(payload, signature);
        return "ok";
    }
}
