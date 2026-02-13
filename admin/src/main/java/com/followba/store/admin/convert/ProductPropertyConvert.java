package com.followba.store.admin.convert;

import com.followba.store.admin.vo.in.ProductPropertySaveIn;
import com.followba.store.admin.vo.out.ProductPropertyRespVO;
import com.followba.store.common.resp.PageResp;
import com.followba.store.dao.dto.PageDTO;
import com.followba.store.dao.dto.ProductPropertyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductPropertyConvert {
    ProductPropertyConvert INSTANCE = Mappers.getMapper(ProductPropertyConvert.class);

    ProductPropertyDTO toDTO(ProductPropertySaveIn in);

    ProductPropertyRespVO toVO(ProductPropertyDTO dto);

    List<ProductPropertyRespVO> toVO(List<ProductPropertyDTO> dto);

    PageResp<ProductPropertyRespVO> toVO(PageDTO<ProductPropertyDTO> dto);
}
