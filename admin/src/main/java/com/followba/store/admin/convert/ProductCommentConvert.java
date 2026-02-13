package com.followba.store.admin.convert;

import com.followba.store.admin.vo.in.ProductCommentCreateIn;
import com.followba.store.admin.vo.out.ProductCommentRespVO;
import com.followba.store.common.resp.PageResp;
import com.followba.store.dao.dto.PageDTO;
import com.followba.store.dao.dto.ProductCommentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductCommentConvert {
    ProductCommentConvert INSTANCE = Mappers.getMapper(ProductCommentConvert.class);

    ProductCommentDTO toDTO(ProductCommentCreateIn in);

    ProductCommentRespVO toVO(ProductCommentDTO dto);

    List<ProductCommentRespVO> toVO(List<ProductCommentDTO> dto);

    PageResp<ProductCommentRespVO> toVO(PageDTO<ProductCommentDTO> dto);
}
