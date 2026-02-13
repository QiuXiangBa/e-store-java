package com.followba.store.admin.controller;

import com.followba.store.admin.service.ProductSpuService;
import com.followba.store.admin.vo.in.ProductSpuPageIn;
import com.followba.store.admin.vo.in.ProductSpuSaveIn;
import com.followba.store.admin.vo.in.ProductSpuUpdateStatusIn;
import com.followba.store.admin.vo.out.CommonBooleanRespVO;
import com.followba.store.admin.vo.out.CommonIdRespVO;
import com.followba.store.admin.vo.out.ProductSpuCountRespVO;
import com.followba.store.admin.vo.out.ProductSpuRespVO;
import com.followba.store.admin.vo.out.ProductSpuSimpleRespVO;
import com.followba.store.common.resp.Out;
import com.followba.store.common.resp.PageResp;
import com.followba.store.dao.enums.ProductSpuStatusEnum;
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

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/product/spu")
@Validated
public class ProductSpuController {

    @Resource
    private ProductSpuService productSpuService;

    @PostMapping("/create")
    public Out<CommonIdRespVO> createProductSpu(@Valid @RequestBody ProductSpuSaveIn reqVO) {
        return Out.success(CommonIdRespVO.of(productSpuService.createSpu(reqVO)));
    }

    @PutMapping("/update")
    public Out<CommonBooleanRespVO> updateSpu(@Valid @RequestBody ProductSpuSaveIn reqVO) {
        productSpuService.updateSpu(reqVO);
        return Out.success(CommonBooleanRespVO.ok());
    }

    @PutMapping("/update-status")
    public Out<CommonBooleanRespVO> updateStatus(@Valid @RequestBody ProductSpuUpdateStatusIn reqVO) {
        productSpuService.updateSpuStatus(reqVO);
        return Out.success(CommonBooleanRespVO.ok());
    }

    @DeleteMapping("/delete")
    public Out<CommonBooleanRespVO> deleteSpu(@RequestParam("id") Long id) {
        productSpuService.deleteSpu(id);
        return Out.success(CommonBooleanRespVO.ok());
    }

    @GetMapping("/get-detail")
    public Out<ProductSpuRespVO> getSpuDetail(@RequestParam("id") Long id) {
        return Out.success(productSpuService.getSpuDetail(id));
    }

    @GetMapping("/list-all-simple")
    public Out<List<ProductSpuSimpleRespVO>> getSpuSimpleList() {
        return Out.success(productSpuService.getSpuListByStatus(ProductSpuStatusEnum.ENABLE.getCode()));
    }

    @GetMapping("/list")
    public Out<List<ProductSpuRespVO>> getSpuList(@RequestParam("spuIds") Collection<Long> spuIds) {
        return Out.success(productSpuService.getSpuList(spuIds));
    }

    @GetMapping("/page")
    public Out<PageResp<ProductSpuRespVO>> getSpuPage(@Valid ProductSpuPageIn reqVO) {
        return Out.success(productSpuService.getSpuPage(reqVO));
    }

    @GetMapping("/get-count")
    public Out<ProductSpuCountRespVO> getSpuCount() {
        return Out.success(productSpuService.getTabsCount());
    }
}
