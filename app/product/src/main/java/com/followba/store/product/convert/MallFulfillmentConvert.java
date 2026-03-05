package com.followba.store.product.convert;

import com.followba.store.product.dto.OrderFulfillmentDetailDTO;
import com.followba.store.product.dto.OrderFulfillmentNodeDTO;
import com.followba.store.product.dto.OrderReceiveDTO;
import com.followba.store.product.vo.in.OrderReceiveIn;
import com.followba.store.product.vo.out.OrderFulfillmentDetailOut;
import com.followba.store.product.vo.out.OrderFulfillmentNodeOut;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MallFulfillmentConvert {

    MallFulfillmentConvert INSTANCE = Mappers.getMapper(MallFulfillmentConvert.class);

    OrderReceiveDTO toOrderReceiveDTO(OrderReceiveIn in);

    OrderFulfillmentNodeOut toOrderFulfillmentNodeOut(OrderFulfillmentNodeDTO dto);

    List<OrderFulfillmentNodeOut> toOrderFulfillmentNodeOutList(List<OrderFulfillmentNodeDTO> dtoList);

    OrderFulfillmentDetailOut toOrderFulfillmentDetailOut(OrderFulfillmentDetailDTO dto);
}
