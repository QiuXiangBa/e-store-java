package com.followba.store.product.service;

import com.followba.store.common.resp.PageResp;
import com.followba.store.product.dto.OrderCancelDTO;
import com.followba.store.product.dto.OrderCreateDTO;
import com.followba.store.product.dto.OrderCreateResultDTO;
import com.followba.store.product.dto.OrderDetailDTO;
import com.followba.store.product.dto.OrderPageItemDTO;
import com.followba.store.product.dto.OrderPageQueryDTO;
import com.followba.store.product.dto.OrderPaySuccessDTO;
import com.followba.store.product.dto.OrderStatusDTO;

public interface MallOrderService {

    OrderCreateResultDTO create(OrderCreateDTO dto);

    PageResp<OrderPageItemDTO> page(OrderPageQueryDTO dto);

    OrderDetailDTO detail(Long id);

    OrderStatusDTO status(Long id);

    void cancel(OrderCancelDTO dto);

    void paySuccess(OrderPaySuccessDTO dto);
}
