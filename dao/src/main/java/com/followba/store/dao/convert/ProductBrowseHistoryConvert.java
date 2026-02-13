package com.followba.store.dao.convert;

import com.followba.store.dao.dto.ProductBrowseHistoryDTO;
import com.followba.store.dao.po.ProductBrowseHistory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductBrowseHistoryConvert {
    ProductBrowseHistoryConvert INSTANCE = Mappers.getMapper(ProductBrowseHistoryConvert.class);

    ProductBrowseHistoryDTO toDTO(ProductBrowseHistory po);

    List<ProductBrowseHistoryDTO> toDTO(List<ProductBrowseHistory> po);

    ProductBrowseHistory toPO(ProductBrowseHistoryDTO dto);
}
