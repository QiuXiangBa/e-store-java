package com.followba.store.admin.vo.in;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ProductSpuSaveIn {

    private Long id;

    @NotBlank(message = "商品名称不能为空")
    private String name;

    @NotBlank(message = "关键字不能为空")
    private String keyword;

    @NotBlank(message = "简介不能为空")
    private String introduction;

    @NotBlank(message = "详情不能为空")
    private String description;

    private String barCode;

    @NotNull(message = "分类不能为空")
    private Long categoryId;

    @NotNull(message = "品牌不能为空")
    private Integer brandId;

    @NotBlank(message = "封面图不能为空")
    private String picUrl;

    private String sliderPicUrls;

    private String videoUrl;

    @NotNull(message = "排序不能为空")
    private Integer sort;

    @NotNull(message = "规格类型不能为空")
    private Boolean specType;

    private Long deliveryTemplateId;

    private Boolean recommendHot;

    private Boolean recommendBenefit;

    private Boolean recommendBest;

    private Boolean recommendNew;

    private Boolean recommendGood;

    private Integer giveIntegral;

    private String giveCouponTemplateIds;

    private Boolean subCommissionType;

    private String activityOrders;

    @Valid
    @NotEmpty(message = "SKU 列表不能为空")
    private List<ProductSkuSaveIn> skus;
}
