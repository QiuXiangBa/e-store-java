package com.followba.store.admin.convert;

import com.followba.store.admin.vo.out.ProductBrowseHistoryRespVO;
import com.followba.store.common.resp.PageResp;
import com.followba.store.dao.dto.PageDTO;
import com.followba.store.dao.dto.ProductBrowseHistoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductBrowseHistoryConvert {
    ProductBrowseHistoryConvert INSTANCE = Mappers.getMapper(ProductBrowseHistoryConvert.class);

    ProductBrowseHistoryRespVO toVO(ProductBrowseHistoryDTO dto);

    List<ProductBrowseHistoryRespVO> toVO(List<ProductBrowseHistoryDTO> dto);

    PageResp<ProductBrowseHistoryRespVO> toVO(PageDTO<ProductBrowseHistoryDTO> dto);
}
