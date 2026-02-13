package com.followba.store.admin.convert;

import com.followba.store.admin.vo.out.ProductFavoriteRespVO;
import com.followba.store.common.resp.PageResp;
import com.followba.store.dao.dto.PageDTO;
import com.followba.store.dao.dto.ProductFavoriteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductFavoriteConvert {
    ProductFavoriteConvert INSTANCE = Mappers.getMapper(ProductFavoriteConvert.class);

    ProductFavoriteRespVO toVO(ProductFavoriteDTO dto);

    List<ProductFavoriteRespVO> toVO(List<ProductFavoriteDTO> dto);

    PageResp<ProductFavoriteRespVO> toVO(PageDTO<ProductFavoriteDTO> dto);
}
