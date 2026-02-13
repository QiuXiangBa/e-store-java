package com.followba.store.admin.convert;

import com.followba.store.admin.vo.in.ProductSkuSaveIn;
import com.followba.store.admin.vo.out.ProductSkuRespVO;
import com.followba.store.dao.dto.ProductSkuDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductSkuConvert {
    ProductSkuConvert INSTANCE = Mappers.getMapper(ProductSkuConvert.class);

    ProductSkuDTO toDTO(ProductSkuSaveIn in);

    List<ProductSkuDTO> toDTO(List<ProductSkuSaveIn> in);

    ProductSkuRespVO toVO(ProductSkuDTO dto);

    List<ProductSkuRespVO> toVO(List<ProductSkuDTO> dto);
}
