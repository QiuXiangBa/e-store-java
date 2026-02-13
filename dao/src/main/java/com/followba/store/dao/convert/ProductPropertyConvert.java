package com.followba.store.dao.convert;

import com.followba.store.dao.dto.ProductPropertyDTO;
import com.followba.store.dao.po.ProductProperty;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductPropertyConvert {
    ProductPropertyConvert INSTANCE = Mappers.getMapper(ProductPropertyConvert.class);

    ProductPropertyDTO toDTO(ProductProperty po);

    List<ProductPropertyDTO> toDTO(List<ProductProperty> po);

    ProductProperty toPO(ProductPropertyDTO dto);
}
