package com.followba.store.admin.convert;

import com.followba.store.admin.dto.OrderFulfillmentDetailDTO;
import com.followba.store.admin.dto.OrderFulfillmentLogisticsNodeDTO;
import com.followba.store.admin.dto.OrderFulfillmentNodeDTO;
import com.followba.store.admin.dto.OrderFulfillmentShipDTO;
import com.followba.store.admin.dto.OrderFulfillmentShipResultDTO;
import com.followba.store.admin.vo.in.OrderFulfillmentLogisticsNodeIn;
import com.followba.store.admin.vo.in.OrderFulfillmentShipIn;
import com.followba.store.admin.vo.out.OrderFulfillmentDetailRespVO;
import com.followba.store.admin.vo.out.OrderFulfillmentNodeRespVO;
import com.followba.store.admin.vo.out.OrderFulfillmentShipRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderFulfillmentConvert {

    OrderFulfillmentConvert INSTANCE = Mappers.getMapper(OrderFulfillmentConvert.class);

    OrderFulfillmentShipDTO toOrderFulfillmentShipDTO(OrderFulfillmentShipIn in);

    OrderFulfillmentLogisticsNodeDTO toOrderFulfillmentLogisticsNodeDTO(OrderFulfillmentLogisticsNodeIn in);

    OrderFulfillmentShipRespVO toOrderFulfillmentShipRespVO(OrderFulfillmentShipResultDTO dto);

    OrderFulfillmentNodeRespVO toOrderFulfillmentNodeRespVO(OrderFulfillmentNodeDTO dto);

    List<OrderFulfillmentNodeRespVO> toOrderFulfillmentNodeRespVOList(List<OrderFulfillmentNodeDTO> dtoList);

    OrderFulfillmentDetailRespVO toOrderFulfillmentDetailRespVO(OrderFulfillmentDetailDTO dto);
}
