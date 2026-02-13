package com.followba.store.admin.controller;

import com.followba.store.admin.service.ProductCategoryService;
import com.followba.store.admin.vo.in.ProductCategoryListIn;
import com.followba.store.admin.vo.in.ProductCategorySaveIn;
import com.followba.store.admin.vo.in.ProductCategorySortBatchUpdateIn;
import com.followba.store.admin.convert.ProductCategoryConvert;
import com.followba.store.admin.vo.out.CommonBooleanRespVO;
import com.followba.store.admin.vo.out.CommonIdRespVO;
import com.followba.store.admin.vo.out.ProductCategoryRespVO;
import com.followba.store.common.resp.Out;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product/category")
@Validated
public class ProductCategoryController {

    @Resource
    private ProductCategoryService productCategoryService;

    @PostMapping("/create")
    public Out<CommonIdRespVO> createCategory(@Valid @RequestBody ProductCategorySaveIn reqVO) {
        return Out.success(CommonIdRespVO.of(productCategoryService.createCategory(reqVO)));
    }

    @PutMapping("/update")
    public Out<CommonBooleanRespVO> updateCategory(@Valid @RequestBody ProductCategorySaveIn reqVO) {
        productCategoryService.updateCategory(reqVO);
        return Out.success(CommonBooleanRespVO.ok());
    }

    @PutMapping("/update-sort-batch")
    public Out<CommonBooleanRespVO> updateCategorySortBatch(@Valid @RequestBody ProductCategorySortBatchUpdateIn reqVO) {
        productCategoryService.updateCategorySortBatch(ProductCategoryConvert.INSTANCE.toSortDTOList(reqVO.getItems()));
        return Out.success(CommonBooleanRespVO.ok());
    }

    @DeleteMapping("/delete")
    public Out<CommonBooleanRespVO> deleteCategory(@RequestParam("id") Long id) {
        productCategoryService.deleteCategory(id);
        return Out.success(CommonBooleanRespVO.ok());
    }

    @GetMapping("/get")
    public Out<ProductCategoryRespVO> getCategory(@RequestParam("id") Long id) {
        return Out.success(productCategoryService.getCategory(id));
    }

    @GetMapping("/list")
    public Out<List<ProductCategoryRespVO>> getCategoryList(@Valid ProductCategoryListIn reqVO) {
        return Out.success(productCategoryService.getCategoryList(reqVO));
    }
}
