package com.followba.store.product.convert;

import com.followba.store.product.dto.CartAddDTO;
import com.followba.store.product.dto.CartItemDTO;
import com.followba.store.product.dto.CartUpdateQuantityDTO;
import com.followba.store.product.dto.CartUpdateSelectedDTO;
import com.followba.store.product.vo.in.CartAddIn;
import com.followba.store.product.vo.in.CartUpdateQuantityIn;
import com.followba.store.product.vo.in.CartUpdateSelectedIn;
import com.followba.store.product.vo.out.CartItemOut;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MallCartConvert {

    MallCartConvert INSTANCE = Mappers.getMapper(MallCartConvert.class);

    CartAddDTO toCartAddDTO(CartAddIn in);

    CartUpdateQuantityDTO toCartUpdateQuantityDTO(CartUpdateQuantityIn in);

    CartUpdateSelectedDTO toCartUpdateSelectedDTO(CartUpdateSelectedIn in);

    CartItemOut toCartItemOut(CartItemDTO dto);

    List<CartItemOut> toCartItemOutList(List<CartItemDTO> dtoList);
}
