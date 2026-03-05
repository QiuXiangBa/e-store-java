package com.followba.store.admin.service;

import com.followba.store.admin.dto.OrderFulfillmentDetailDTO;
import com.followba.store.admin.dto.OrderFulfillmentLogisticsNodeDTO;
import com.followba.store.admin.dto.OrderFulfillmentShipDTO;
import com.followba.store.admin.dto.OrderFulfillmentShipResultDTO;

public interface OrderFulfillmentService {

    OrderFulfillmentShipResultDTO ship(OrderFulfillmentShipDTO dto);

    void appendLogisticsNode(OrderFulfillmentLogisticsNodeDTO dto);

    OrderFulfillmentDetailDTO detail(Long orderId);
}
