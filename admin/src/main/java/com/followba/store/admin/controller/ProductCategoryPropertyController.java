package com.followba.store.admin.controller;

import com.followba.store.admin.service.ProductCategoryPropertyService;
import com.followba.store.admin.vo.in.ProductCategoryPropertyListIn;
import com.followba.store.admin.vo.in.ProductCategoryPropertySaveIn;
import com.followba.store.admin.vo.out.CommonBooleanRespVO;
import com.followba.store.admin.vo.out.ProductCategoryPropertyRespVO;
import com.followba.store.common.resp.Out;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product/category/property")
@Validated
public class ProductCategoryPropertyController {

    @Resource
    private ProductCategoryPropertyService productCategoryPropertyService;

    @PutMapping("/save-batch")
    public Out<CommonBooleanRespVO> saveBatch(@Valid @RequestBody ProductCategoryPropertySaveIn reqVO) {
        productCategoryPropertyService.saveBatch(reqVO);
        return Out.success(CommonBooleanRespVO.ok());
    }

    @GetMapping("/list")
    public Out<List<ProductCategoryPropertyRespVO>> list(@Valid ProductCategoryPropertyListIn reqVO) {
        return Out.success(productCategoryPropertyService.listByCategoryId(reqVO.getCategoryId(), reqVO.getPropertyType()));
    }
}
