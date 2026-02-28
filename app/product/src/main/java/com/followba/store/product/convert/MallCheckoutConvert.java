package com.followba.store.product.convert;

import com.followba.store.product.dto.CheckoutCreateDTO;
import com.followba.store.product.dto.CheckoutItemDTO;
import com.followba.store.product.dto.CheckoutResultDTO;
import com.followba.store.product.vo.in.CheckoutCreateIn;
import com.followba.store.product.vo.out.CheckoutCreateOut;
import com.followba.store.product.vo.out.CheckoutItemOut;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MallCheckoutConvert {

    MallCheckoutConvert INSTANCE = Mappers.getMapper(MallCheckoutConvert.class);

    CheckoutCreateDTO toCheckoutCreateDTO(CheckoutCreateIn in);

    CheckoutItemOut toCheckoutItemOut(CheckoutItemDTO dto);

    List<CheckoutItemOut> toCheckoutItemOutList(List<CheckoutItemDTO> dtoList);

    CheckoutCreateOut toCheckoutCreateOut(CheckoutResultDTO dto);
}
