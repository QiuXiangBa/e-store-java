package com.followba.store.dao.convert;

import com.followba.store.dao.dto.TradeFulfillmentLogDTO;
import com.followba.store.dao.po.TradeFulfillmentLog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TradeFulfillmentLogConvert {

    TradeFulfillmentLogConvert INSTANCE = Mappers.getMapper(TradeFulfillmentLogConvert.class);

    TradeFulfillmentLogDTO toDTO(TradeFulfillmentLog po);

    List<TradeFulfillmentLogDTO> toDTO(List<TradeFulfillmentLog> poList);

    TradeFulfillmentLog toPO(TradeFulfillmentLogDTO dto);
}
