package com.followba.store.admin.service;

import com.followba.store.admin.vo.in.ProductBrandCreateIn;
import com.followba.store.admin.vo.in.ProductBrandListIn;
import com.followba.store.admin.vo.in.ProductBrandPageIn;
import com.followba.store.admin.vo.in.ProductBrandUpdateIn;
import com.followba.store.admin.vo.out.ProductBrandRespVO;
import com.followba.store.admin.vo.out.ProductBrandSimpleRespVO;
import com.followba.store.common.resp.PageResp;

import java.util.List;

public interface ProductBrandService {

    Long createBrand(ProductBrandCreateIn reqVO);

    void updateBrand(ProductBrandUpdateIn reqVO);

    void deleteBrand(Long id);

    ProductBrandRespVO getBrand(Long id);

    List<ProductBrandRespVO> getBrandList(ProductBrandListIn reqVO);

    List<ProductBrandSimpleRespVO> getBrandListByStatus(Integer status);

    PageResp<ProductBrandRespVO> getBrandPage(ProductBrandPageIn reqVO);

    void validateProductBrand(Long id);
}
