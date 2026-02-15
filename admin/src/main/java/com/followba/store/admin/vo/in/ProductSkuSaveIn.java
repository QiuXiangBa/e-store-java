package com.followba.store.admin.vo.in;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ProductSkuSaveIn {

    private Long id;

    private List<Property> properties;

    @NotNull(message = "价格不能为空")
    private Integer price;

    @NotNull(message = "市场价不能为空")
    private Integer marketPrice;

    @NotNull(message = "成本价不能为空")
    private Integer costPrice;

    private String barCode;

    private String picUrl;

    @NotNull(message = "库存不能为空")
    private Integer stock;

    private Double weight;

    private Double volume;

    private Integer subCommissionFirstPrice;

    private Integer subCommissionSecondPrice;

    @Data
    public static class Property {

        private Long propertyId;

        private String propertyName;

        private Long valueId;

        private String valueName;

        private String valuePicUrl;
    }
}
