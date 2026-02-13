package com.followba.store.admin.controller;

import com.followba.store.admin.service.ProductBrandService;
import com.followba.store.admin.vo.in.ProductBrandCreateIn;
import com.followba.store.admin.vo.in.ProductBrandListIn;
import com.followba.store.admin.vo.in.ProductBrandPageIn;
import com.followba.store.admin.vo.in.ProductBrandUpdateIn;
import com.followba.store.admin.vo.out.CommonBooleanRespVO;
import com.followba.store.admin.vo.out.CommonIdRespVO;
import com.followba.store.admin.vo.out.ProductBrandRespVO;
import com.followba.store.admin.vo.out.ProductBrandSimpleRespVO;
import com.followba.store.common.resp.Out;
import com.followba.store.common.resp.PageResp;
import com.followba.store.dao.enums.StatusEnums;
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
@RequestMapping("/product/brand")
@Validated
public class ProductBrandController {

    @Resource
    private ProductBrandService productBrandService;

    @PostMapping("/create")
    public Out<CommonIdRespVO> createBrand(@Valid @RequestBody ProductBrandCreateIn reqVO) {
        return Out.success(CommonIdRespVO.of(productBrandService.createBrand(reqVO)));
    }

    @PutMapping("/update")
    public Out<CommonBooleanRespVO> updateBrand(@Valid @RequestBody ProductBrandUpdateIn reqVO) {
        productBrandService.updateBrand(reqVO);
        return Out.success(CommonBooleanRespVO.ok());
    }

    @DeleteMapping("/delete")
    public Out<CommonBooleanRespVO> deleteBrand(@RequestParam("id") Long id) {
        productBrandService.deleteBrand(id);
        return Out.success(CommonBooleanRespVO.ok());
    }

    @GetMapping("/get")
    public Out<ProductBrandRespVO> getBrand(@RequestParam("id") Long id) {
        return Out.success(productBrandService.getBrand(id));
    }

    @GetMapping("/list-all-simple")
    public Out<List<ProductBrandSimpleRespVO>> getSimpleBrandList() {
        return Out.success(productBrandService.getBrandListByStatus(StatusEnums.OPEN.getCode()));
    }

    @GetMapping("/page")
    public Out<PageResp<ProductBrandRespVO>> getBrandPage(@Valid ProductBrandPageIn reqVO) {
        return Out.success(productBrandService.getBrandPage(reqVO));
    }

    @GetMapping("/list")
    public Out<List<ProductBrandRespVO>> getBrandList(@Valid ProductBrandListIn reqVO) {
        return Out.success(productBrandService.getBrandList(reqVO));
    }
}
