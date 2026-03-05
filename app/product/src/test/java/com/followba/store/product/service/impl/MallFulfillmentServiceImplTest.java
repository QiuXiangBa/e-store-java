package com.followba.store.product.service.impl;

import com.followba.store.common.context.IToolRequest;
import com.followba.store.common.context.RequestContext;
import com.followba.store.dao.biz.BizTradeFulfillmentLogMapper;
import com.followba.store.dao.biz.BizTradeFulfillmentMapper;
import com.followba.store.dao.biz.BizTradeOrderMapper;
import com.followba.store.dao.biz.BizTradeOrderOperateLogMapper;
import com.followba.store.dao.dto.TradeFulfillmentDTO;
import com.followba.store.dao.dto.TradeOrderDTO;
import com.followba.store.dao.enums.TradeFulfillmentStatusEnum;
import com.followba.store.dao.enums.TradeOrderStatusEnum;
import com.followba.store.product.dto.OrderReceiveDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MallFulfillmentServiceImplTest {

    @Mock
    private BizTradeOrderMapper bizTradeOrderMapper;

    @Mock
    private BizTradeOrderOperateLogMapper bizTradeOrderOperateLogMapper;

    @Mock
    private BizTradeFulfillmentMapper bizTradeFulfillmentMapper;

    @Mock
    private BizTradeFulfillmentLogMapper bizTradeFulfillmentLogMapper;

    @InjectMocks
    private MallFulfillmentServiceImpl mallFulfillmentService;

    @AfterEach
    void tearDown() {
        RequestContext.unbindRequest();
    }

    @Test
    void receive_shouldUpdateFulfillmentAndOrderStatus() {
        bindUser(100L);

        OrderReceiveDTO receiveDTO = new OrderReceiveDTO();
        receiveDTO.setOrderId(1000L);

        TradeOrderDTO orderDTO = new TradeOrderDTO();
        orderDTO.setId(1000L);
        orderDTO.setUserId(100L);
        orderDTO.setStatus(TradeOrderStatusEnum.PAID.getCode());

        TradeFulfillmentDTO fulfillmentDTO = new TradeFulfillmentDTO();
        fulfillmentDTO.setId(900L);
        fulfillmentDTO.setOrderId(1000L);
        fulfillmentDTO.setStatus(TradeFulfillmentStatusEnum.SHIPPED.getCode());

        when(bizTradeOrderMapper.selectById(1000L)).thenReturn(orderDTO);
        when(bizTradeFulfillmentMapper.selectByOrderId(1000L)).thenReturn(fulfillmentDTO);
        when(bizTradeFulfillmentMapper.updateStatusByOrderIdAndFromStatuses(
                eq(1000L), anySet(), eq(TradeFulfillmentStatusEnum.SIGNED.getCode()), any(), any(), isNull()
        )).thenReturn(1);
        when(bizTradeOrderMapper.updateStatusByIdAndFromStatus(
                1000L,
                TradeOrderStatusEnum.PAID.getCode(),
                TradeOrderStatusEnum.COMPLETED.getCode())
        ).thenReturn(1);

        mallFulfillmentService.receive(receiveDTO);

        verify(bizTradeFulfillmentLogMapper).insert(any());
        verify(bizTradeOrderOperateLogMapper).insert(any());
    }

    @Test
    void createForPaidOrder_shouldInsertFulfillment() {
        TradeOrderDTO orderDTO = new TradeOrderDTO();
        orderDTO.setId(2000L);
        orderDTO.setOrderNo("OD2000");
        orderDTO.setUserId(300L);
        orderDTO.setStatus(TradeOrderStatusEnum.PAID.getCode());

        when(bizTradeFulfillmentMapper.selectByOrderId(2000L)).thenReturn(null);
        doAnswer(invocation -> {
            TradeFulfillmentDTO dto = invocation.getArgument(0);
            dto.setId(700L);
            return null;
        }).when(bizTradeFulfillmentMapper).insert(any(TradeFulfillmentDTO.class));

        mallFulfillmentService.createForPaidOrder(orderDTO);

        verify(bizTradeFulfillmentMapper).insert(any(TradeFulfillmentDTO.class));
        verify(bizTradeFulfillmentLogMapper).insert(any());
        Assertions.assertTrue(true);
    }

    private void bindUser(Long userId) {
        RequestContext.bindRequest(IToolRequest.build("token", String.valueOf(userId), "127.0.0.1", "/", "JUnit", ""));
    }
}
