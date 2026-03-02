package com.followba.store.product.vo.out;

import lombok.Data;

/**
 * 商城分类出参 / Mall category output.
 */
@Data
public class ProductAppCategoryOut {

    /**
     * 分类 ID / Category id.
     */
    private Long id;

    /**
     * 父分类 ID / Parent category id.
     */
    private Long parentId;

    /**
     * 分类名称 / Category name.
     */
    private String name;

    /**
     * 排序值 / Sort value.
     */
    private Integer sort;
}

