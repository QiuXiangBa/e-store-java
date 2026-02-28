package com.followba.store.dao.convert;

import com.followba.store.dao.dto.TradeUserAddressDTO;
import com.followba.store.dao.po.TradeUserAddress;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TradeUserAddressConvert {

    TradeUserAddressConvert INSTANCE = Mappers.getMapper(TradeUserAddressConvert.class);

    TradeUserAddressDTO toDTO(TradeUserAddress po);

    List<TradeUserAddressDTO> toDTO(List<TradeUserAddress> poList);

    TradeUserAddress toPO(TradeUserAddressDTO dto);
}
