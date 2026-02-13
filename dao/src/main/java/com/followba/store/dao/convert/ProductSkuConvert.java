package com.followba.store.dao.convert;

import com.followba.store.dao.dto.ProductSkuDTO;
import com.followba.store.dao.po.ProductSku;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductSkuConvert {
    ProductSkuConvert INSTANCE = Mappers.getMapper(ProductSkuConvert.class);

    ProductSkuDTO toDTO(ProductSku po);

    List<ProductSkuDTO> toDTO(List<ProductSku> po);

    ProductSku toPO(ProductSkuDTO dto);
}
