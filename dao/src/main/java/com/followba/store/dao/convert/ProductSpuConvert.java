package com.followba.store.dao.convert;

import com.followba.store.dao.dto.ProductSpuDTO;
import com.followba.store.dao.po.ProductSpu;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductSpuConvert {
    ProductSpuConvert INSTANCE = Mappers.getMapper(ProductSpuConvert.class);

    ProductSpuDTO toDTO(ProductSpu po);

    List<ProductSpuDTO> toDTO(List<ProductSpu> po);

    ProductSpu toPO(ProductSpuDTO dto);
}
