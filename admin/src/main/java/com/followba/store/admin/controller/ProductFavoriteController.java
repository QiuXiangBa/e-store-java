package com.followba.store.admin.controller;

import com.followba.store.admin.service.ProductFavoriteService;
import com.followba.store.admin.vo.in.ProductFavoritePageIn;
import com.followba.store.admin.vo.out.ProductFavoriteRespVO;
import com.followba.store.common.resp.Out;
import com.followba.store.common.resp.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product/favorite")
@Validated
public class ProductFavoriteController {

    @Resource
    private ProductFavoriteService productFavoriteService;

    @GetMapping("/page")
    public Out<PageResp<ProductFavoriteRespVO>> getFavoritePage(@Valid ProductFavoritePageIn reqVO) {
        return Out.success(productFavoriteService.getFavoritePage(reqVO));
    }
}
