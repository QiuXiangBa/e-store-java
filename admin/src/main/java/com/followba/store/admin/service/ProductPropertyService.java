package com.followba.store.admin.service;

import com.followba.store.admin.vo.in.ProductPropertyPageIn;
import com.followba.store.admin.vo.in.ProductPropertySaveIn;
import com.followba.store.admin.vo.out.ProductPropertyRespVO;
import com.followba.store.common.resp.PageResp;

import java.util.List;

public interface ProductPropertyService {

    Long createProperty(ProductPropertySaveIn reqVO);

    void updateProperty(ProductPropertySaveIn reqVO);

    void deleteProperty(Long id);

    ProductPropertyRespVO getProperty(Long id);

    PageResp<ProductPropertyRespVO> getPropertyPage(ProductPropertyPageIn reqVO);

    List<ProductPropertyRespVO> getPropertySimpleList();
}
