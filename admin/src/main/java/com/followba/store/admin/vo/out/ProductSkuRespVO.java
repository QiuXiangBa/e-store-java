package com.followba.store.admin.vo.out;

import lombok.Data;

import java.util.List;

@Data
public class ProductSkuRespVO {

    private Long id;

    private Long spuId;

    private List<Property> properties;

    private Integer price;

    private Integer marketPrice;

    private Integer costPrice;

    private String barCode;

    private String picUrl;

    private Integer stock;

    private Double weight;

    private Double volume;

    private Integer subCommissionFirstPrice;

    private Integer subCommissionSecondPrice;

    private Integer salesCount;

    @Data
    public static class Property {

        private Long propertyId;

        private String propertyName;

        private Long valueId;

        private String valueName;

        private String valuePicUrl;
    }
}
