package com.followba.store.admin.service;

import com.followba.store.admin.vo.in.ProductPropertyValuePageIn;
import com.followba.store.admin.vo.in.ProductPropertyValueSaveIn;
import com.followba.store.admin.vo.out.ProductPropertyValueRespVO;
import com.followba.store.common.resp.PageResp;

import java.util.List;

public interface ProductPropertyValueService {

    Long createPropertyValue(ProductPropertyValueSaveIn reqVO);

    void updatePropertyValue(ProductPropertyValueSaveIn reqVO);

    void deletePropertyValue(Long id);

    ProductPropertyValueRespVO getPropertyValue(Long id);

    PageResp<ProductPropertyValueRespVO> getPropertyValuePage(ProductPropertyValuePageIn reqVO);

    List<ProductPropertyValueRespVO> getPropertyValueSimpleList(Long propertyId);
}
