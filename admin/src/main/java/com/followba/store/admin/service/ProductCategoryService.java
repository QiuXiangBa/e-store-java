package com.followba.store.admin.service;

import com.followba.store.admin.vo.in.ProductCategoryListIn;
import com.followba.store.admin.vo.in.ProductCategorySaveIn;
import com.followba.store.admin.vo.out.ProductCategoryRespVO;

import java.util.List;

public interface ProductCategoryService {

    Long createCategory(ProductCategorySaveIn reqVO);

    void updateCategory(ProductCategorySaveIn reqVO);

    void deleteCategory(Long id);

    ProductCategoryRespVO getCategory(Long id);

    List<ProductCategoryRespVO> getCategoryList(ProductCategoryListIn reqVO);

    void validateCategory(Long id);

    int getCategoryLevel(Long id);
}
