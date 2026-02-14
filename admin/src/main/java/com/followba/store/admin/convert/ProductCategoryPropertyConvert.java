package com.followba.store.admin.convert;

import com.followba.store.admin.vo.in.ProductCategoryPropertySaveIn;
import com.followba.store.admin.vo.out.ProductCategoryPropertyRespVO;
import com.followba.store.dao.dto.ProductCategoryPropertyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductCategoryPropertyConvert {
    ProductCategoryPropertyConvert INSTANCE = Mappers.getMapper(ProductCategoryPropertyConvert.class);

    ProductCategoryPropertyDTO toDTO(ProductCategoryPropertySaveIn.Item item);

    List<ProductCategoryPropertyDTO> toDTO(List<ProductCategoryPropertySaveIn.Item> items);

    ProductCategoryPropertyRespVO toVO(ProductCategoryPropertyDTO dto);

    List<ProductCategoryPropertyRespVO> toVO(List<ProductCategoryPropertyDTO> dtoList);
}
