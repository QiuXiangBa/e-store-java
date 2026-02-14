package com.followba.store.dao.convert;

import com.followba.store.dao.dto.ProductCategoryPropertyDTO;
import com.followba.store.dao.po.ProductCategoryProperty;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductCategoryPropertyConvert {
    ProductCategoryPropertyConvert INSTANCE = Mappers.getMapper(ProductCategoryPropertyConvert.class);

    ProductCategoryPropertyDTO toDTO(ProductCategoryProperty po);

    List<ProductCategoryPropertyDTO> toDTO(List<ProductCategoryProperty> po);

    ProductCategoryProperty toPO(ProductCategoryPropertyDTO dto);

    List<ProductCategoryProperty> toPO(List<ProductCategoryPropertyDTO> dto);
}
