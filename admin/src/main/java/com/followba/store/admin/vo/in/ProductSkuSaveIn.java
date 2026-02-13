package com.followba.store.admin.vo.in;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductSkuSaveIn {

    private Long id;

    private String properties;

    @NotNull(message = "价格不能为空")
    private Integer price;

    @NotNull(message = "市场价不能为空")
    private Integer marketPrice;

    @NotNull(message = "成本价不能为空")
    private Integer costPrice;

    private String barCode;

    @NotNull(message = "图片不能为空")
    private String picUrl;

    @NotNull(message = "库存不能为空")
    private Integer stock;

    private Double weight;

    private Double volume;

    private Integer subCommissionFirstPrice;

    private Integer subCommissionSecondPrice;
}
