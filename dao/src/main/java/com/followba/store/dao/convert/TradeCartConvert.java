package com.followba.store.dao.convert;

import com.followba.store.dao.dto.TradeCartDTO;
import com.followba.store.dao.po.TradeCart;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TradeCartConvert {

    TradeCartConvert INSTANCE = Mappers.getMapper(TradeCartConvert.class);

    TradeCartDTO toDTO(TradeCart po);

    List<TradeCartDTO> toDTO(List<TradeCart> po);

    TradeCart toPO(TradeCartDTO dto);
}
