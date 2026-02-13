package com.followba.store.admin.service.impl;

import com.followba.store.admin.convert.ProductFavoriteConvert;
import com.followba.store.admin.service.ProductFavoriteService;
import com.followba.store.admin.vo.in.ProductFavoritePageIn;
import com.followba.store.admin.vo.out.ProductFavoriteRespVO;
import com.followba.store.common.resp.PageResp;
import com.followba.store.dao.biz.BizProductFavoriteMapper;
import com.followba.store.dao.dto.PageDTO;
import com.followba.store.dao.dto.ProductFavoriteDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ProductFavoriteServiceImpl implements ProductFavoriteService {

    @Resource
    private BizProductFavoriteMapper bizProductFavoriteMapper;

    @Override
    public PageResp<ProductFavoriteRespVO> getFavoritePage(ProductFavoritePageIn reqVO) {
        PageDTO<ProductFavoriteDTO> page = bizProductFavoriteMapper.selectPage(reqVO.getPageNum(), reqVO.getPageSize(),
                reqVO.getUserId(), reqVO.getSpuId());
        return ProductFavoriteConvert.INSTANCE.toVO(page);
    }
}
