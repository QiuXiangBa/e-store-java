package com.followba.store.dao.convert;

import com.followba.store.dao.dto.ProductBrandDTO;
import com.followba.store.dao.po.ProductBrand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductBrandConvert {
    ProductBrandConvert INSTANCE = Mappers.getMapper(ProductBrandConvert.class);

    ProductBrandDTO toDTO(ProductBrand po);
    List<ProductBrandDTO> toDTO(List<ProductBrand> po);

    ProductBrand toPO(ProductBrandDTO dto);
}
