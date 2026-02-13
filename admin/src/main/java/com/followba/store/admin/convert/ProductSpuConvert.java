package com.followba.store.admin.convert;

import com.followba.store.admin.vo.in.ProductSpuSaveIn;
import com.followba.store.admin.vo.out.ProductSpuRespVO;
import com.followba.store.admin.vo.out.ProductSpuSimpleRespVO;
import com.followba.store.common.resp.PageResp;
import com.followba.store.dao.dto.PageDTO;
import com.followba.store.dao.dto.ProductSpuDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductSpuConvert {
    ProductSpuConvert INSTANCE = Mappers.getMapper(ProductSpuConvert.class);

    @Mapping(target = "specType", expression = "java(Boolean.TRUE.equals(in.getSpecType()) ? com.followba.store.dao.enums.ProductSpuSpecTypeEnum.MULTI.getCode() : com.followba.store.dao.enums.ProductSpuSpecTypeEnum.SINGLE.getCode())")
    @Mapping(target = "recommendHot", expression = "java(Boolean.TRUE.equals(in.getRecommendHot()))")
    @Mapping(target = "recommendBenefit", expression = "java(Boolean.TRUE.equals(in.getRecommendBenefit()))")
    @Mapping(target = "recommendBest", expression = "java(Boolean.TRUE.equals(in.getRecommendBest()))")
    @Mapping(target = "recommendNew", expression = "java(Boolean.TRUE.equals(in.getRecommendNew()))")
    @Mapping(target = "recommendGood", expression = "java(Boolean.TRUE.equals(in.getRecommendGood()))")
    @Mapping(target = "subCommissionType", expression = "java(Boolean.TRUE.equals(in.getSubCommissionType()) ? com.followba.store.dao.enums.BinaryFlagEnum.ON.getCode() : com.followba.store.dao.enums.BinaryFlagEnum.OFF.getCode())")
    ProductSpuDTO toDTO(ProductSpuSaveIn in);

    @Mapping(target = "specType", expression = "java(dto.getSpecType() != null && dto.getSpecType().equals(com.followba.store.dao.enums.ProductSpuSpecTypeEnum.MULTI.getCode()))")
    @Mapping(target = "subCommissionType", expression = "java(dto.getSubCommissionType() != null && dto.getSubCommissionType().equals(com.followba.store.dao.enums.BinaryFlagEnum.ON.getCode()))")
    @Mapping(target = "status", source = "status")
    ProductSpuRespVO toVO(ProductSpuDTO dto);

    ProductSpuSimpleRespVO toSimpleVO(ProductSpuDTO dto);

    List<ProductSpuRespVO> toVO(List<ProductSpuDTO> dto);

    List<ProductSpuSimpleRespVO> toSimpleVO(List<ProductSpuDTO> dto);

    PageResp<ProductSpuRespVO> toVO(PageDTO<ProductSpuDTO> dto);
}
