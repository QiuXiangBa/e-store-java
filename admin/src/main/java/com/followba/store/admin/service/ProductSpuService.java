package com.followba.store.admin.service;

import com.followba.store.admin.vo.in.ProductSpuPageIn;
import com.followba.store.admin.vo.in.ProductSpuSaveIn;
import com.followba.store.admin.vo.in.ProductSpuUpdateStatusIn;
import com.followba.store.admin.vo.out.ProductSpuCountRespVO;
import com.followba.store.admin.vo.out.ProductSpuRespVO;
import com.followba.store.admin.vo.out.ProductSpuSimpleRespVO;
import com.followba.store.common.resp.PageResp;

import java.util.Collection;
import java.util.List;

public interface ProductSpuService {

    Long createSpu(ProductSpuSaveIn reqVO);

    void updateSpu(ProductSpuSaveIn reqVO);

    void updateSpuStatus(ProductSpuUpdateStatusIn reqVO);

    void deleteSpu(Long id);

    ProductSpuRespVO getSpuDetail(Long id);

    List<ProductSpuRespVO> getSpuList(Collection<Long> ids);

    List<ProductSpuSimpleRespVO> getSpuListByStatus(Integer status);

    PageResp<ProductSpuRespVO> getSpuPage(ProductSpuPageIn reqVO);

    ProductSpuCountRespVO getTabsCount();

    Long getSpuCountByCategoryId(Long categoryId);
}
