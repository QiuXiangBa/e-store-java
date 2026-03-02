package com.followba.store.product.controller;

import com.followba.store.common.resp.Out;
import com.followba.store.product.convert.MallCategoryConvert;
import com.followba.store.product.dto.ProductAppCategoryDTO;
import com.followba.store.product.service.MallCategoryService;
import com.followba.store.product.vo.out.ProductAppCategoryOut;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商城分类接口 / Mall category API.
 */
@RestController
@RequestMapping("/mall/category")
public class MallCategoryController {

    @Resource
    private MallCategoryService mallCategoryService;

    @GetMapping("/list")
    public Out<List<ProductAppCategoryOut>> list() {
        List<ProductAppCategoryDTO> categoryDTOList = mallCategoryService.listEnabledLeaf();
        return Out.success(MallCategoryConvert.INSTANCE.toOutList(categoryDTOList));
    }
}

