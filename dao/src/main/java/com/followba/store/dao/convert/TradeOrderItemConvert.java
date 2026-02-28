package com.followba.store.dao.convert;

import com.followba.store.dao.dto.TradeOrderItemDTO;
import com.followba.store.dao.po.TradeOrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TradeOrderItemConvert {

    TradeOrderItemConvert INSTANCE = Mappers.getMapper(TradeOrderItemConvert.class);

    TradeOrderItemDTO toDTO(TradeOrderItem po);

    List<TradeOrderItemDTO> toDTO(List<TradeOrderItem> list);

    TradeOrderItem toPO(TradeOrderItemDTO dto);
}
