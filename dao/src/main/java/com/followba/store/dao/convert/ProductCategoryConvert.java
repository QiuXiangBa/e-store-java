package com.followba.store.dao.convert;

import com.followba.store.dao.dto.ProductCategoryDTO;
import com.followba.store.dao.po.ProductCategory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductCategoryConvert {
    ProductCategoryConvert INSTANCE = Mappers.getMapper(ProductCategoryConvert.class);

    ProductCategoryDTO toDTO(ProductCategory po);

    List<ProductCategoryDTO> toDTO(List<ProductCategory> po);

    ProductCategory toPO(ProductCategoryDTO dto);

    List<ProductCategory> toPO(List<ProductCategoryDTO> dto);
}
