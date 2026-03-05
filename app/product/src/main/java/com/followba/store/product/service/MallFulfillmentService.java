package com.followba.store.product.service;

import com.followba.store.dao.dto.TradeOrderDTO;
import com.followba.store.product.dto.OrderFulfillmentDetailDTO;
import com.followba.store.product.dto.OrderReceiveDTO;

public interface MallFulfillmentService {

    OrderFulfillmentDetailDTO detail(Long orderId);

    void receive(OrderReceiveDTO dto);

    void createForPaidOrder(TradeOrderDTO orderDTO);

    void closeForOrder(TradeOrderDTO orderDTO);
}
