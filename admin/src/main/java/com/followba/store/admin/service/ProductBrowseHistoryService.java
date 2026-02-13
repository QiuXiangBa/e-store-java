package com.followba.store.admin.service;

import com.followba.store.admin.vo.in.ProductBrowseHistoryPageIn;
import com.followba.store.admin.vo.out.ProductBrowseHistoryRespVO;
import com.followba.store.common.resp.PageResp;

public interface ProductBrowseHistoryService {

    PageResp<ProductBrowseHistoryRespVO> getBrowseHistoryPage(ProductBrowseHistoryPageIn reqVO);
}
