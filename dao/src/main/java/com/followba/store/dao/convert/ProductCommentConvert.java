package com.followba.store.dao.convert;

import com.followba.store.dao.dto.ProductCommentDTO;
import com.followba.store.dao.po.ProductComment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductCommentConvert {
    ProductCommentConvert INSTANCE = Mappers.getMapper(ProductCommentConvert.class);

    ProductCommentDTO toDTO(ProductComment po);

    List<ProductCommentDTO> toDTO(List<ProductComment> po);

    ProductComment toPO(ProductCommentDTO dto);
}
