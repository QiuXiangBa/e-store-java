package com.followba.store.admin.service;

import com.followba.store.admin.vo.in.ProductFavoritePageIn;
import com.followba.store.admin.vo.out.ProductFavoriteRespVO;
import com.followba.store.common.resp.PageResp;

public interface ProductFavoriteService {

    PageResp<ProductFavoriteRespVO> getFavoritePage(ProductFavoritePageIn reqVO);
}
