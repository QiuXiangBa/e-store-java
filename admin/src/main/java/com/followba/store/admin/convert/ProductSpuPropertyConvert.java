package com.followba.store.admin.convert;

import com.followba.store.admin.vo.in.ProductSpuDisplayPropertyIn;
import com.followba.store.admin.vo.out.ProductSpuDisplayPropertyRespVO;
import com.followba.store.dao.dto.ProductSpuPropertyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductSpuPropertyConvert {
    ProductSpuPropertyConvert INSTANCE = Mappers.getMapper(ProductSpuPropertyConvert.class);

    ProductSpuPropertyDTO toDTO(ProductSpuDisplayPropertyIn in);

    List<ProductSpuPropertyDTO> toDTO(List<ProductSpuDisplayPropertyIn> inList);

    ProductSpuDisplayPropertyRespVO toVO(ProductSpuPropertyDTO dto);

    List<ProductSpuDisplayPropertyRespVO> toVO(List<ProductSpuPropertyDTO> dtoList);
}
