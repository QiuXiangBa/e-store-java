package com.followba.store.admin.service;

import com.followba.store.admin.vo.in.ProductCategoryPropertySaveIn;
import com.followba.store.admin.vo.out.ProductCategoryPropertyRespVO;

import java.util.List;

public interface ProductCategoryPropertyService {

    void saveBatch(ProductCategoryPropertySaveIn reqVO);

    List<ProductCategoryPropertyRespVO> listByCategoryId(Long categoryId, Integer propertyType);
}
