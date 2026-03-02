package com.followba.store.dao.convert;

import com.followba.store.dao.dto.TradePaymentDTO;
import com.followba.store.dao.po.TradePayment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TradePaymentConvert {

    TradePaymentConvert INSTANCE = Mappers.getMapper(TradePaymentConvert.class);

    TradePaymentDTO toDTO(TradePayment po);

    TradePayment toPO(TradePaymentDTO dto);
}
