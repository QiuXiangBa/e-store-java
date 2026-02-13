package com.followba.store.admin.controller;

import com.followba.store.admin.service.ProductBrowseHistoryService;
import com.followba.store.admin.vo.in.ProductBrowseHistoryPageIn;
import com.followba.store.admin.vo.out.ProductBrowseHistoryRespVO;
import com.followba.store.common.resp.Out;
import com.followba.store.common.resp.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product/browse-history")
@Validated
public class ProductBrowseHistoryController {

    @Resource
    private ProductBrowseHistoryService productBrowseHistoryService;

    @GetMapping("/page")
    public Out<PageResp<ProductBrowseHistoryRespVO>> getBrowseHistoryPage(@Valid ProductBrowseHistoryPageIn reqVO) {
        return Out.success(productBrowseHistoryService.getBrowseHistoryPage(reqVO));
    }
}
