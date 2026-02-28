package com.followba.store.dao.convert;

import com.followba.store.dao.dto.TradeCheckoutOrderDTO;
import com.followba.store.dao.po.TradeCheckoutOrder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TradeCheckoutOrderConvert {

    TradeCheckoutOrderConvert INSTANCE = Mappers.getMapper(TradeCheckoutOrderConvert.class);

    TradeCheckoutOrderDTO toDTO(TradeCheckoutOrder po);

    List<TradeCheckoutOrderDTO> toDTO(List<TradeCheckoutOrder> po);

    TradeCheckoutOrder toPO(TradeCheckoutOrderDTO dto);
}
