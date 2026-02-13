package com.followba.store.admin.convert;

import com.followba.store.admin.vo.in.ProductPropertyValueSaveIn;
import com.followba.store.admin.vo.out.ProductPropertyValueRespVO;
import com.followba.store.common.resp.PageResp;
import com.followba.store.dao.dto.PageDTO;
import com.followba.store.dao.dto.ProductPropertyValueDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductPropertyValueConvert {
    ProductPropertyValueConvert INSTANCE = Mappers.getMapper(ProductPropertyValueConvert.class);

    ProductPropertyValueDTO toDTO(ProductPropertyValueSaveIn in);

    ProductPropertyValueRespVO toVO(ProductPropertyValueDTO dto);

    List<ProductPropertyValueRespVO> toVO(List<ProductPropertyValueDTO> dto);

    PageResp<ProductPropertyValueRespVO> toVO(PageDTO<ProductPropertyValueDTO> dto);
}
