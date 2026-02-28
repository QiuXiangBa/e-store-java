package com.followba.store.dao.convert;

import com.followba.store.dao.dto.TradeCheckoutItemDTO;
import com.followba.store.dao.po.TradeCheckoutItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TradeCheckoutItemConvert {

    TradeCheckoutItemConvert INSTANCE = Mappers.getMapper(TradeCheckoutItemConvert.class);

    TradeCheckoutItemDTO toDTO(TradeCheckoutItem po);

    List<TradeCheckoutItemDTO> toDTO(List<TradeCheckoutItem> po);

    TradeCheckoutItem toPO(TradeCheckoutItemDTO dto);
}
