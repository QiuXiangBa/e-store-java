package com.followba.store.dao.convert;

import com.followba.store.dao.dto.TradeStockLogDTO;
import com.followba.store.dao.po.TradeStockLog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TradeStockLogConvert {

    TradeStockLogConvert INSTANCE = Mappers.getMapper(TradeStockLogConvert.class);

    TradeStockLogDTO toDTO(TradeStockLog po);

    List<TradeStockLogDTO> toDTO(List<TradeStockLog> poList);

    TradeStockLog toPO(TradeStockLogDTO dto);
}
