package com.followba.store.dao.convert;

import com.followba.store.dao.dto.ProductFavoriteDTO;
import com.followba.store.dao.po.ProductFavorite;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductFavoriteConvert {
    ProductFavoriteConvert INSTANCE = Mappers.getMapper(ProductFavoriteConvert.class);

    ProductFavoriteDTO toDTO(ProductFavorite po);

    List<ProductFavoriteDTO> toDTO(List<ProductFavorite> po);

    ProductFavorite toPO(ProductFavoriteDTO dto);
}
