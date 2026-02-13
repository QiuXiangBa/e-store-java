package com.followba.store.admin.convert;

import com.followba.store.admin.vo.in.ProductCategorySaveIn;
import com.followba.store.admin.vo.out.ProductCategoryRespVO;
import com.followba.store.dao.dto.ProductCategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductCategoryConvert {
    ProductCategoryConvert INSTANCE = Mappers.getMapper(ProductCategoryConvert.class);

    @Mapping(target = "status", expression = "java(in.getStatus().getCode())")
    ProductCategoryDTO toDTO(ProductCategorySaveIn in);

    ProductCategoryRespVO toVO(ProductCategoryDTO dto);

    List<ProductCategoryRespVO> toVO(List<ProductCategoryDTO> dto);
}
