package com.followba.store.dao.convert;

import com.followba.store.dao.dto.ProductPropertyValueDTO;
import com.followba.store.dao.po.ProductPropertyValue;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductPropertyValueConvert {
    ProductPropertyValueConvert INSTANCE = Mappers.getMapper(ProductPropertyValueConvert.class);

    ProductPropertyValueDTO toDTO(ProductPropertyValue po);

    List<ProductPropertyValueDTO> toDTO(List<ProductPropertyValue> po);

    ProductPropertyValue toPO(ProductPropertyValueDTO dto);
}
