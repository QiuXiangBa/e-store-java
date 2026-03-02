package com.followba.store.payment.service;

import com.followba.store.common.exception.BizException;
import com.followba.store.dao.biz.BizTradeCheckoutOrderMapper;
import com.followba.store.dao.biz.BizTradePaymentMapper;
import com.followba.store.dao.dto.TradeCheckoutOrderDTO;
import com.followba.store.dao.dto.TradePaymentDTO;
import com.followba.store.dao.enums.PaymentStatusEnum;
import com.followba.store.dao.enums.TradeCheckoutOrderStatusEnum;
import com.followba.store.payment.config.StripeProperties;
import com.followba.store.payment.dto.PaymentCreateIntentDTO;
import com.followba.store.payment.service.impl.MallPaymentServiceImpl;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * MallPaymentServiceImpl 单元测试 / MallPaymentServiceImpl unit test.
 */
@ExtendWith(MockitoExtension.class)
class MallPaymentServiceImplTest {

    @Mock
    private BizTradeCheckoutOrderMapper bizTradeCheckoutOrderMapper;

    @Mock
    private BizTradePaymentMapper bizTradePaymentMapper;

    @Mock
    private StripeProperties stripeProperties;

    @InjectMocks
    private MallPaymentServiceImpl mallPaymentService;

    private static final Long USER_ID = 1L;
    private static final Long ORDER_ID = 100L;

    @Test
    void createPaymentIntent_shouldThrowWhenOrderNotExists() {
        when(bizTradeCheckoutOrderMapper.selectById(ORDER_ID)).thenReturn(null);

        try (MockedStatic<com.followba.store.common.context.RequestContext> ctx =
                     Mockito.mockStatic(com.followba.store.common.context.RequestContext.class)) {
            ctx.when(com.followba.store.common.context.RequestContext::getUserId).thenReturn(String.valueOf(USER_ID));
            assertThrows(BizException.class, () -> mallPaymentService.createPaymentIntent(ORDER_ID));
        }
        verify(bizTradePaymentMapper, never()).insert(any());
    }

    @Test
    void createPaymentIntent_shouldThrowWhenOrderNotBelongToUser() {
        TradeCheckoutOrderDTO order = new TradeCheckoutOrderDTO();
        order.setId(ORDER_ID);
        order.setUserId(999L);
        order.setStatus(TradeCheckoutOrderStatusEnum.CREATED.getCode());
        order.setPayAmount(1000);
        when(bizTradeCheckoutOrderMapper.selectById(ORDER_ID)).thenReturn(order);

        try (MockedStatic<com.followba.store.common.context.RequestContext> ctx =
                     Mockito.mockStatic(com.followba.store.common.context.RequestContext.class)) {
            ctx.when(com.followba.store.common.context.RequestContext::getUserId).thenReturn(String.valueOf(USER_ID));
            assertThrows(BizException.class, () -> mallPaymentService.createPaymentIntent(ORDER_ID));
        }
        verify(bizTradePaymentMapper, never()).insert(any());
    }

    @Test
    void createPaymentIntent_shouldThrowWhenOrderAlreadyPaid() {
        TradeCheckoutOrderDTO order = buildOrder(TradeCheckoutOrderStatusEnum.PAID.getCode(), 1000);
        when(bizTradeCheckoutOrderMapper.selectById(ORDER_ID)).thenReturn(order);

        try (MockedStatic<com.followba.store.common.context.RequestContext> ctx =
                     Mockito.mockStatic(com.followba.store.common.context.RequestContext.class)) {
            ctx.when(com.followba.store.common.context.RequestContext::getUserId).thenReturn(String.valueOf(USER_ID));
            assertThrows(BizException.class, () -> mallPaymentService.createPaymentIntent(ORDER_ID));
        }
        verify(bizTradePaymentMapper, never()).insert(any());
    }

    @Test
    void createPaymentIntent_shouldReuseExistingPendingIntentWhenStripeRetrieveSuccess() {
        TradeCheckoutOrderDTO order = buildOrder(TradeCheckoutOrderStatusEnum.CREATED.getCode(), 1200);
        TradePaymentDTO payment = buildPayment(66L, PaymentStatusEnum.PENDING.getCode(), "pi_old");
        when(bizTradeCheckoutOrderMapper.selectById(ORDER_ID)).thenReturn(order);
        when(bizTradePaymentMapper.selectByCheckoutOrderId(ORDER_ID)).thenReturn(payment);

        PaymentIntent retrievedIntent = Mockito.mock(PaymentIntent.class);
        when(retrievedIntent.getClientSecret()).thenReturn("cs_old");
        try (MockedStatic<com.followba.store.common.context.RequestContext> ctx =
                     Mockito.mockStatic(com.followba.store.common.context.RequestContext.class);
             MockedStatic<PaymentIntent> intentStatic = Mockito.mockStatic(PaymentIntent.class)) {
            ctx.when(com.followba.store.common.context.RequestContext::getUserId).thenReturn(String.valueOf(USER_ID));
            intentStatic.when(() -> PaymentIntent.retrieve("pi_old")).thenReturn(retrievedIntent);

            PaymentCreateIntentDTO dto = mallPaymentService.createPaymentIntent(ORDER_ID);
            assertEquals("cs_old", dto.getClientSecret());
            assertEquals("pi_old", dto.getPaymentIntentId());
        }

        verify(bizTradePaymentMapper, never()).insert(any());
        verify(bizTradePaymentMapper, never()).updateForNewIntent(any(), any(), any(), any());
        verify(bizTradeCheckoutOrderMapper, never()).updateStatusById(any(), any());
    }

    @Test
    void createPaymentIntent_shouldRefreshExistingPaymentWhenStripeRetrieveFails() {
        TradeCheckoutOrderDTO order = buildOrder(TradeCheckoutOrderStatusEnum.CREATED.getCode(), 1200);
        TradePaymentDTO payment = buildPayment(66L, PaymentStatusEnum.PENDING.getCode(), "pi_old");
        when(bizTradeCheckoutOrderMapper.selectById(ORDER_ID)).thenReturn(order);
        when(bizTradePaymentMapper.selectByCheckoutOrderId(ORDER_ID)).thenReturn(payment);
        when(stripeProperties.getCurrency()).thenReturn("usd");

        PaymentIntent createdIntent = Mockito.mock(PaymentIntent.class);
        when(createdIntent.getId()).thenReturn("pi_new");
        when(createdIntent.getClientSecret()).thenReturn("cs_new");
        try (MockedStatic<com.followba.store.common.context.RequestContext> ctx =
                     Mockito.mockStatic(com.followba.store.common.context.RequestContext.class);
             MockedStatic<PaymentIntent> intentStatic = Mockito.mockStatic(PaymentIntent.class)) {
            ctx.when(com.followba.store.common.context.RequestContext::getUserId).thenReturn(String.valueOf(USER_ID));
            intentStatic.when(() -> PaymentIntent.retrieve("pi_old")).thenThrow(new RuntimeException("retrieve failed"));
            intentStatic.when(() -> PaymentIntent.create(any(PaymentIntentCreateParams.class))).thenReturn(createdIntent);

            PaymentCreateIntentDTO dto = mallPaymentService.createPaymentIntent(ORDER_ID);
            assertEquals("cs_new", dto.getClientSecret());
            assertEquals("pi_new", dto.getPaymentIntentId());
        }

        verify(bizTradePaymentMapper, never()).insert(any());
        verify(bizTradePaymentMapper).updateForNewIntent(eq(66L), eq("pi_new"), eq(1200), eq("usd"));
        verify(bizTradeCheckoutOrderMapper).updateStatusById(eq(ORDER_ID), eq(TradeCheckoutOrderStatusEnum.PENDING_PAY.getCode()));
    }

    @Test
    void createPaymentIntent_shouldRefreshFailedPaymentRecord() {
        TradeCheckoutOrderDTO order = buildOrder(TradeCheckoutOrderStatusEnum.PENDING_PAY.getCode(), 1500);
        TradePaymentDTO payment = buildPayment(77L, PaymentStatusEnum.FAILED.getCode(), "pi_failed");
        when(bizTradeCheckoutOrderMapper.selectById(ORDER_ID)).thenReturn(order);
        when(bizTradePaymentMapper.selectByCheckoutOrderId(ORDER_ID)).thenReturn(payment);
        when(stripeProperties.getCurrency()).thenReturn("usd");

        PaymentIntent createdIntent = Mockito.mock(PaymentIntent.class);
        when(createdIntent.getId()).thenReturn("pi_retry");
        when(createdIntent.getClientSecret()).thenReturn("cs_retry");
        try (MockedStatic<com.followba.store.common.context.RequestContext> ctx =
                     Mockito.mockStatic(com.followba.store.common.context.RequestContext.class);
             MockedStatic<PaymentIntent> intentStatic = Mockito.mockStatic(PaymentIntent.class)) {
            ctx.when(com.followba.store.common.context.RequestContext::getUserId).thenReturn(String.valueOf(USER_ID));
            intentStatic.when(() -> PaymentIntent.create(any(PaymentIntentCreateParams.class))).thenReturn(createdIntent);

            PaymentCreateIntentDTO dto = mallPaymentService.createPaymentIntent(ORDER_ID);
            assertEquals("cs_retry", dto.getClientSecret());
            assertEquals("pi_retry", dto.getPaymentIntentId());
        }

        verify(bizTradePaymentMapper, never()).insert(any());
        verify(bizTradePaymentMapper).updateForNewIntent(eq(77L), eq("pi_retry"), eq(1500), eq("usd"));
    }

    private TradeCheckoutOrderDTO buildOrder(Integer status, Integer payAmount) {
        TradeCheckoutOrderDTO order = new TradeCheckoutOrderDTO();
        order.setId(ORDER_ID);
        order.setUserId(USER_ID);
        order.setStatus(status);
        order.setPayAmount(payAmount);
        return order;
    }

    private TradePaymentDTO buildPayment(Long id, Integer status, String paymentIntentId) {
        TradePaymentDTO payment = new TradePaymentDTO();
        payment.setId(id);
        payment.setCheckoutOrderId(ORDER_ID);
        payment.setStatus(status);
        payment.setStripePaymentIntentId(paymentIntentId);
        return payment;
    }
}
