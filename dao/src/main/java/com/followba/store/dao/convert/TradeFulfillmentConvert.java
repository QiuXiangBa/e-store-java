package com.followba.store.dao.convert;

import com.followba.store.dao.dto.TradeFulfillmentDTO;
import com.followba.store.dao.po.TradeFulfillment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TradeFulfillmentConvert {

    TradeFulfillmentConvert INSTANCE = Mappers.getMapper(TradeFulfillmentConvert.class);

    TradeFulfillmentDTO toDTO(TradeFulfillment po);

    List<TradeFulfillmentDTO> toDTO(List<TradeFulfillment> poList);

    TradeFulfillment toPO(TradeFulfillmentDTO dto);
}
