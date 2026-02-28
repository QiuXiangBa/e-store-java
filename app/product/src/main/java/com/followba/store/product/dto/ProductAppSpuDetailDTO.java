package com.followba.store.product.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductAppSpuDetailDTO {

    private Long spuId;

    private String name;

    private String introduction;

    private String picUrl;

    private List<String> sliderPicUrls;

    private List<ProductAppSkuDTO> skuList;
}
