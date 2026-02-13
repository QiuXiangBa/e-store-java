package com.followba.store.admin.vo.out;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProductSpuRespVO {

    private Long id;

    private String name;

    private String keyword;

    private String introduction;

    private String description;

    private String barCode;

    private Long categoryId;

    private Integer brandId;

    private String picUrl;

    private String sliderPicUrls;

    private String videoUrl;

    private Integer sort;

    private Byte status;

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

    private Integer price;

    private Integer marketPrice;

    private Integer costPrice;

    private Integer stock;

    private Integer salesCount;

    private Integer virtualSalesCount;

    private Integer browseCount;

    private Date createTime;

    private List<ProductSkuRespVO> skus;
}
