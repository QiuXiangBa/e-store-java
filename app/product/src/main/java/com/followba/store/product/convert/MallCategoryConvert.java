package com.followba.store.product.convert;

import com.followba.store.product.dto.ProductAppCategoryDTO;
import com.followba.store.product.vo.out.ProductAppCategoryOut;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 商城分类转换器 / Mall category converter.
 */
@Mapper
public interface MallCategoryConvert {

    MallCategoryConvert INSTANCE = Mappers.getMapper(MallCategoryConvert.class);

    ProductAppCategoryOut toOut(ProductAppCategoryDTO dto);

    List<ProductAppCategoryOut> toOutList(List<ProductAppCategoryDTO> dtoList);
}

