package com.followba.store.dao.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProductSpuDTO {

    private Long id;

    private String name;

    private String keyword;

    private String introduction;

    private String description;

    private String barCode;

    private Long categoryId;

    private Long brandId;

    private String picUrl;

    private List<String> sliderPicUrls;

    private String videoUrl;

    private Integer sort;

    private Integer status;

    private Integer specType;

    private Integer price;

    private Integer marketPrice;

    private Integer costPrice;

    private Integer stock;

    private List<Integer> deliveryTypes;

    private Long deliveryTemplateId;

    private Boolean recommendHot;

    private Boolean recommendBenefit;

    private Boolean recommendBest;

    private Boolean recommendNew;

    private Boolean recommendGood;

    private Integer giveIntegral;

    private String giveCouponTemplateIds;

    private Integer subCommissionType;

    private String activityOrders;

    private Integer salesCount;

    private Integer virtualSalesCount;

    private Integer browseCount;

    private String creator;

    private Date createTime;

    private String updater;

    private Date updateTime;
}
