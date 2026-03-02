package com.followba.store.payment.service;

import com.followba.store.common.exception.BizException;
import com.followba.store.dao.biz.BizTradeCheckoutOrderMapper;
import com.followba.store.dao.biz.BizTradePaymentMapper;
import com.followba.store.dao.dto.TradeCheckoutOrderDTO;
import com.followba.store.dao.enums.TradeCheckoutOrderStatusEnum;
import com.followba.store.payment.config.StripeProperties;
import com.followba.store.payment.service.impl.MallPaymentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @BeforeEach
    void setUp() {
        when(stripeProperties.getCurrency()).thenReturn("usd");
    }

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
        TradeCheckoutOrderDTO order = new TradeCheckoutOrderDTO();
        order.setId(ORDER_ID);
        order.setUserId(USER_ID);
        order.setStatus(TradeCheckoutOrderStatusEnum.PAID.getCode());
        order.setPayAmount(1000);
        when(bizTradeCheckoutOrderMapper.selectById(ORDER_ID)).thenReturn(order);

        try (MockedStatic<com.followba.store.common.context.RequestContext> ctx =
                     Mockito.mockStatic(com.followba.store.common.context.RequestContext.class)) {
            ctx.when(com.followba.store.common.context.RequestContext::getUserId).thenReturn(String.valueOf(USER_ID));
            assertThrows(BizException.class, () -> mallPaymentService.createPaymentIntent(ORDER_ID));
        }
        verify(bizTradePaymentMapper, never()).insert(any());
    }
}
