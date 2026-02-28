package com.followba.store.dao.convert;

import com.followba.store.dao.dto.TradeOrderDTO;
import com.followba.store.dao.po.TradeOrder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TradeOrderConvert {

    TradeOrderConvert INSTANCE = Mappers.getMapper(TradeOrderConvert.class);

    TradeOrderDTO toDTO(TradeOrder po);

    List<TradeOrderDTO> toDTO(List<TradeOrder> list);

    TradeOrder toPO(TradeOrderDTO dto);
}
