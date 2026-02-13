package com.followba.store.admin.controller;

import com.followba.store.admin.service.ProductPropertyValueService;
import com.followba.store.admin.vo.in.ProductPropertyValuePageIn;
import com.followba.store.admin.vo.in.ProductPropertyValueSaveIn;
import com.followba.store.admin.vo.out.CommonBooleanRespVO;
import com.followba.store.admin.vo.out.CommonIdRespVO;
import com.followba.store.admin.vo.out.ProductPropertyValueRespVO;
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
@RequestMapping("/product/property/value")
@Validated
public class ProductPropertyValueController {

    @Resource
    private ProductPropertyValueService productPropertyValueService;

    @PostMapping("/create")
    public Out<CommonIdRespVO> createPropertyValue(@Valid @RequestBody ProductPropertyValueSaveIn reqVO) {
        return Out.success(CommonIdRespVO.of(productPropertyValueService.createPropertyValue(reqVO)));
    }

    @PutMapping("/update")
    public Out<CommonBooleanRespVO> updatePropertyValue(@Valid @RequestBody ProductPropertyValueSaveIn reqVO) {
        productPropertyValueService.updatePropertyValue(reqVO);
        return Out.success(CommonBooleanRespVO.ok());
    }

    @DeleteMapping("/delete")
    public Out<CommonBooleanRespVO> deletePropertyValue(@RequestParam("id") Long id) {
        productPropertyValueService.deletePropertyValue(id);
        return Out.success(CommonBooleanRespVO.ok());
    }

    @GetMapping("/get")
    public Out<ProductPropertyValueRespVO> getPropertyValue(@RequestParam("id") Long id) {
        return Out.success(productPropertyValueService.getPropertyValue(id));
    }

    @GetMapping("/page")
    public Out<PageResp<ProductPropertyValueRespVO>> getPropertyValuePage(@Valid ProductPropertyValuePageIn reqVO) {
        return Out.success(productPropertyValueService.getPropertyValuePage(reqVO));
    }

    @GetMapping("/simple-list")
    public Out<List<ProductPropertyValueRespVO>> getPropertyValueSimpleList(@RequestParam("propertyId") Long propertyId) {
        return Out.success(productPropertyValueService.getPropertyValueSimpleList(propertyId));
    }
}
