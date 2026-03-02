package com.followba.store.product.service;

import com.followba.store.product.dto.ProductAppCategoryDTO;

import java.util.List;

/**
 * 商城分类服务 / Mall category service.
 */
public interface MallCategoryService {

    /**
     * 查询可发布分类（启用 + 叶子）/ List publishable categories (enabled + leaf).
     */
    List<ProductAppCategoryDTO> listEnabledLeaf();
}

