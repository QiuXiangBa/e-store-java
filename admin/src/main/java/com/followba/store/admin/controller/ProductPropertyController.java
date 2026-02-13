package com.followba.store.admin.controller;

import com.followba.store.admin.service.ProductPropertyService;
import com.followba.store.admin.vo.in.ProductPropertyPageIn;
import com.followba.store.admin.vo.in.ProductPropertySaveIn;
import com.followba.store.admin.vo.out.CommonBooleanRespVO;
import com.followba.store.admin.vo.out.CommonIdRespVO;
import com.followba.store.admin.vo.out.ProductPropertyRespVO;
import com.followba.store.common.resp.Out;
import com.followba.store.common.resp.PageResp;
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
@RequestMapping("/product/property")
@Validated
public class ProductPropertyController {

    @Resource
    private ProductPropertyService productPropertyService;

    @PostMapping("/create")
    public Out<CommonIdRespVO> createProperty(@Valid @RequestBody ProductPropertySaveIn reqVO) {
        return Out.success(CommonIdRespVO.of(productPropertyService.createProperty(reqVO)));
    }

    @PutMapping("/update")
    public Out<CommonBooleanRespVO> updateProperty(@Valid @RequestBody ProductPropertySaveIn reqVO) {
        productPropertyService.updateProperty(reqVO);
        return Out.success(CommonBooleanRespVO.ok());
    }

    @DeleteMapping("/delete")
    public Out<CommonBooleanRespVO> deleteProperty(@RequestParam("id") Long id) {
        productPropertyService.deleteProperty(id);
        return Out.success(CommonBooleanRespVO.ok());
    }

    @GetMapping("/get")
    public Out<ProductPropertyRespVO> getProperty(@RequestParam("id") Long id) {
        return Out.success(productPropertyService.getProperty(id));
    }

    @GetMapping("/page")
    public Out<PageResp<ProductPropertyRespVO>> getPropertyPage(@Valid ProductPropertyPageIn reqVO) {
        return Out.success(productPropertyService.getPropertyPage(reqVO));
    }

    @GetMapping("/simple-list")
    public Out<List<ProductPropertyRespVO>> getPropertySimpleList() {
        return Out.success(productPropertyService.getPropertySimpleList());
    }
}
