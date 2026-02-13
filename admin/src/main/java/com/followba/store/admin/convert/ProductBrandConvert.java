package com.followba.store.admin.convert;

import com.followba.store.admin.vo.in.ProductBrandCreateIn;
import com.followba.store.admin.vo.in.ProductBrandUpdateIn;
import com.followba.store.admin.vo.out.ProductBrandRespVO;
import com.followba.store.admin.vo.out.ProductBrandSimpleRespVO;
import com.followba.store.common.resp.PageResp;
import com.followba.store.dao.dto.PageDTO;
import com.followba.store.dao.dto.ProductBrandDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductBrandConvert {
    ProductBrandConvert INSTANCE = Mappers.getMapper(ProductBrandConvert.class);

    @Mapping(target = "status", expression = "java(in.getStatus().getCode())")
    ProductBrandDTO toDTO(ProductBrandCreateIn in);

    @Mapping(target = "status", expression = "java(in.getStatus().getCode())")
    ProductBrandDTO toDTO(ProductBrandUpdateIn in);

    ProductBrandRespVO toVO(ProductBrandDTO dto);

    List<ProductBrandRespVO> toVO(List<ProductBrandDTO> dto);

    PageResp<ProductBrandRespVO> toVO(PageDTO<ProductBrandDTO> dto);

    ProductBrandSimpleRespVO toSimpleVO(ProductBrandDTO dto);

    List<ProductBrandSimpleRespVO> toSimpleVO(List<ProductBrandDTO> dto);

    PageResp<ProductBrandSimpleRespVO> toSimpleVO(PageDTO<ProductBrandDTO> dto);
}
