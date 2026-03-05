package com.followba.store.product.service.impl;

import com.followba.store.common.context.IToolRequest;
import com.followba.store.common.context.RequestContext;
import com.followba.store.dao.biz.BizProductSkuMapper;
import com.followba.store.dao.biz.BizTradeCheckoutItemMapper;
import com.followba.store.dao.biz.BizTradeCheckoutOrderMapper;
import com.followba.store.dao.biz.BizTradeOrderItemMapper;
import com.followba.store.dao.biz.BizTradeOrderMapper;
import com.followba.store.dao.biz.BizTradeOrderOperateLogMapper;
import com.followba.store.dao.biz.BizTradeStockLogMapper;
import com.followba.store.dao.dto.TradeCheckoutItemDTO;
import com.followba.store.dao.dto.TradeCheckoutOrderDTO;
import com.followba.store.dao.dto.TradeOrderDTO;
import com.followba.store.dao.dto.TradeOrderItemDTO;
import com.followba.store.dao.dto.TradeStockLogDTO;
import com.followba.store.dao.enums.TradeCheckoutOrderStatusEnum;
import com.followba.store.dao.enums.TradeOrderStatusEnum;
import com.followba.store.dao.enums.TradeStockBizTypeEnum;
import com.followba.store.product.dto.OrderCancelDTO;
import com.followba.store.product.dto.OrderCreateDTO;
import com.followba.store.product.dto.OrderCreateResultDTO;
import com.followba.store.product.dto.OrderPaySuccessDTO;
import com.followba.store.product.service.MallFulfillmentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MallOrderServiceImplTest {

    @Mock
    private BizTradeCheckoutOrderMapper bizTradeCheckoutOrderMapper;

    @Mock
    private BizTradeCheckoutItemMapper bizTradeCheckoutItemMapper;

    @Mock
    private BizTradeOrderMapper bizTradeOrderMapper;

    @Mock
    private BizTradeOrderItemMapper bizTradeOrderItemMapper;

    @Mock
    private BizTradeOrderOperateLogMapper bizTradeOrderOperateLogMapper;

    @Mock
    private BizProductSkuMapper bizProductSkuMapper;

    @Mock
    private BizTradeStockLogMapper bizTradeStockLogMapper;

    @Mock
    private MallFulfillmentService mallFulfillmentService;

    @InjectMocks
    private MallOrderServiceImpl mallOrderService;

    @AfterEach
    void tearDown() {
        RequestContext.unbindRequest();
    }

    @Test
    void create_shouldDeductStockAndWriteStockLog() {
        bindUser(100L);
        OrderCreateDTO dto = new OrderCreateDTO();
        dto.setCheckoutOrderId(11L);
        dto.setRequestId("req-001");
        dto.setRemark("remark");

        TradeCheckoutOrderDTO checkoutOrderDTO = new TradeCheckoutOrderDTO();
        checkoutOrderDTO.setId(11L);
        checkoutOrderDTO.setUserId(100L);
        checkoutOrderDTO.setStatus(TradeCheckoutOrderStatusEnum.CREATED.getCode());
        checkoutOrderDTO.setItemCount(1);
        checkoutOrderDTO.setTotalAmount(200);
        checkoutOrderDTO.setPayAmount(200);

        TradeCheckoutItemDTO itemDTO = new TradeCheckoutItemDTO();
        itemDTO.setSkuId(9L);
        itemDTO.setQuantity(2);
        itemDTO.setPrice(100);
        itemDTO.setLineAmount(200);
        itemDTO.setSpuId(1L);
        itemDTO.setSpuName("spu");

        when(bizTradeOrderMapper.selectByUserIdAndRequestId(100L, "req-001")).thenReturn(null);
        when(bizTradeCheckoutOrderMapper.selectById(11L)).thenReturn(checkoutOrderDTO);
        when(bizTradeOrderMapper.selectByCheckoutOrderIdAndUserId(11L, 100L)).thenReturn(null);
        when(bizTradeCheckoutItemMapper.selectListByCheckoutOrderId(11L)).thenReturn(List.of(itemDTO));
        when(bizProductSkuMapper.batchReduceStock(anyList())).thenReturn(1);
        doAnswer(invocation -> {
            TradeOrderDTO order = invocation.getArgument(0);
            order.setId(99L);
            return null;
        }).when(bizTradeOrderMapper).insert(any(TradeOrderDTO.class));

        OrderCreateResultDTO resultDTO = mallOrderService.create(dto);

        Assertions.assertEquals(99L, resultDTO.getOrderId());

        ArgumentCaptor<List<TradeStockLogDTO>> logCaptor = ArgumentCaptor.forClass(List.class);
        verify(bizTradeStockLogMapper).insertBatch(logCaptor.capture());
        List<TradeStockLogDTO> stockLogs = logCaptor.getValue();
        Assertions.assertEquals(1, stockLogs.size());
        Assertions.assertEquals(TradeStockBizTypeEnum.ORDER_CREATE_DEDUCT.getCode(), stockLogs.get(0).getBizType());
        Assertions.assertEquals(-2, stockLogs.get(0).getChangeQty());
    }

    @Test
    void cancel_shouldRestoreStockAndWriteStockLog() {
        bindUser(200L);
        OrderCancelDTO dto = new OrderCancelDTO();
        dto.setOrderId(1000L);
        dto.setReason("user cancel");

        TradeOrderDTO orderDTO = new TradeOrderDTO();
        orderDTO.setId(1000L);
        orderDTO.setOrderNo("OD123");
        orderDTO.setUserId(200L);
        orderDTO.setStatus(TradeOrderStatusEnum.CREATED.getCode());

        TradeOrderItemDTO item1 = new TradeOrderItemDTO();
        item1.setOrderId(1000L);
        item1.setSkuId(9L);
        item1.setQuantity(1);

        TradeOrderItemDTO item2 = new TradeOrderItemDTO();
        item2.setOrderId(1000L);
        item2.setSkuId(9L);
        item2.setQuantity(2);

        when(bizTradeOrderMapper.selectById(1000L)).thenReturn(orderDTO);
        when(bizTradeOrderMapper.updateStatusByIdAndFromStatus(eq(1000L), eq(TradeOrderStatusEnum.CREATED.getCode()), eq(TradeOrderStatusEnum.CANCELLED.getCode()), any(), any(), any()))
                .thenReturn(1);
        when(bizTradeOrderItemMapper.selectListByOrderId(1000L)).thenReturn(List.of(item1, item2));
        when(bizTradeStockLogMapper.selectListByBizAndSkuIds(eq(TradeStockBizTypeEnum.ORDER_CANCEL_RESTORE.getCode()), eq("OD123"), anySet()))
                .thenReturn(List.of());
        when(bizProductSkuMapper.batchIncreaseStock(anyList())).thenReturn(1);
        doNothing().when(mallFulfillmentService).closeForOrder(any(TradeOrderDTO.class));

        mallOrderService.cancel(dto);

        verify(bizTradeStockLogMapper).selectListByBizAndSkuIds(
                eq(TradeStockBizTypeEnum.ORDER_CANCEL_RESTORE.getCode()),
                eq("OD123"),
                eq(Set.of(9L))
        );

        ArgumentCaptor<List<TradeStockLogDTO>> logCaptor = ArgumentCaptor.forClass(List.class);
        verify(bizTradeStockLogMapper).insertBatch(logCaptor.capture());
        List<TradeStockLogDTO> stockLogs = logCaptor.getValue();
        Assertions.assertEquals(1, stockLogs.size());
        Assertions.assertEquals(TradeStockBizTypeEnum.ORDER_CANCEL_RESTORE.getCode(), stockLogs.get(0).getBizType());
        Assertions.assertEquals(3, stockLogs.get(0).getChangeQty());
    }

    @Test
    void paySuccess_shouldCreateFulfillmentForPaidOrder() {
        OrderPaySuccessDTO dto = new OrderPaySuccessDTO();
        dto.setOrderNo("OD-PAY-001");
        dto.setPayTxnNo("PAY-TXN-001");

        TradeOrderDTO orderDTO = new TradeOrderDTO();
        orderDTO.setId(1001L);
        orderDTO.setOrderNo("OD-PAY-001");
        orderDTO.setUserId(99L);
        orderDTO.setStatus(TradeOrderStatusEnum.CREATED.getCode());

        when(bizTradeOrderMapper.selectByOrderNo("OD-PAY-001")).thenReturn(orderDTO);
        doNothing().when(mallFulfillmentService).createForPaidOrder(any(TradeOrderDTO.class));

        mallOrderService.paySuccess(dto);

        verify(mallFulfillmentService).createForPaidOrder(any(TradeOrderDTO.class));
    }

    private void bindUser(Long userId) {
        RequestContext.bindRequest(IToolRequest.build("token", String.valueOf(userId), "127.0.0.1", "/", "JUnit", ""));
    }
}
