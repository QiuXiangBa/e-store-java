package com.followba.store.dao.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProductSkuDTO {

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

    private String creator;

    private Date createTime;

    private String updater;

    private Date updateTime;

    private Byte deleted;

    private Long tenantId;

    @Data
    public static class Property {

        private Long propertyId;

        private String propertyName;

        private Long valueId;

        private String valueName;

        private String valuePicUrl;
    }
}
