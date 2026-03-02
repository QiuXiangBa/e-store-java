package com.followba.store.product.dto;

import lombok.Data;

/**
 * 商城分类 DTO / Mall category DTO.
 */
@Data
public class ProductAppCategoryDTO {

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

