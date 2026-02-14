package com.followba.store.dao.convert;

import com.followba.store.dao.dto.ProductSpuPropertyDTO;
import com.followba.store.dao.po.ProductSpuProperty;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductSpuPropertyConvert {
    ProductSpuPropertyConvert INSTANCE = Mappers.getMapper(ProductSpuPropertyConvert.class);

    ProductSpuPropertyDTO toDTO(ProductSpuProperty po);

    List<ProductSpuPropertyDTO> toDTO(List<ProductSpuProperty> po);

    ProductSpuProperty toPO(ProductSpuPropertyDTO dto);

    List<ProductSpuProperty> toPO(List<ProductSpuPropertyDTO> dto);
}
