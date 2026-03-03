package com.followba.store.product.convert;

import com.followba.store.product.dto.OrderCancelDTO;
import com.followba.store.product.dto.OrderCloseDTO;
import com.followba.store.product.dto.OrderCreateDTO;
import com.followba.store.product.dto.OrderCreateResultDTO;
import com.followba.store.product.dto.OrderDetailDTO;
import com.followba.store.product.dto.OrderItemDTO;
import com.followba.store.product.dto.OrderPageItemDTO;
import com.followba.store.product.dto.OrderPageQueryDTO;
import com.followba.store.product.dto.OrderPaySuccessDTO;
import com.followba.store.product.dto.OrderSimpleItemDTO;
import com.followba.store.product.dto.OrderStatusDTO;
import com.followba.store.product.vo.in.OrderCancelIn;
import com.followba.store.product.vo.in.OrderCloseIn;
import com.followba.store.product.vo.in.OrderCreateIn;
import com.followba.store.product.vo.in.OrderPageIn;
import com.followba.store.product.vo.in.OrderPaySuccessIn;
import com.followba.store.product.vo.out.OrderCreateOut;
import com.followba.store.product.vo.out.OrderDetailOut;
import com.followba.store.product.vo.out.OrderItemOut;
import com.followba.store.product.vo.out.OrderPageItemOut;
import com.followba.store.product.vo.out.OrderSimpleItemOut;
import com.followba.store.product.vo.out.OrderStatusOut;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MallOrderConvert {

    MallOrderConvert INSTANCE = Mappers.getMapper(MallOrderConvert.class);

    OrderCreateDTO toOrderCreateDTO(OrderCreateIn in);

    OrderPageQueryDTO toOrderPageQueryDTO(OrderPageIn in);

    OrderCancelDTO toOrderCancelDTO(OrderCancelIn in);

    OrderCloseDTO toOrderCloseDTO(OrderCloseIn in);

    OrderPaySuccessDTO toOrderPaySuccessDTO(OrderPaySuccessIn in);

    OrderCreateOut toOrderCreateOut(OrderCreateResultDTO dto);

    OrderSimpleItemOut toOrderSimpleItemOut(OrderSimpleItemDTO dto);

    List<OrderPageItemOut> toOrderPageItemOutList(List<OrderPageItemDTO> list);

    OrderItemOut toOrderItemOut(OrderItemDTO dto);

    List<OrderItemOut> toOrderItemOutList(List<OrderItemDTO> list);

    OrderDetailOut toOrderDetailOut(OrderDetailDTO dto);

    OrderStatusOut toOrderStatusOut(OrderStatusDTO dto);
}
