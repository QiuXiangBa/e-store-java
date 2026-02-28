package com.followba.store.product.vo.out;

import lombok.Data;

import java.util.List;

@Data
public class ProductAppSpuDetailOut {

    private Long spuId;

    private String name;

    private String introduction;

    private String picUrl;

    private List<String> sliderPicUrls;

    private List<ProductAppSkuOut> skuList;
}
