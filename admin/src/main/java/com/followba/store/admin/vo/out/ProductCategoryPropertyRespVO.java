package com.followba.store.admin.vo.out;

import lombok.Data;

@Data
public class ProductCategoryPropertyRespVO {

    private Long id;

    private Long categoryId;

    private Long propertyId;

    private String propertyName;

    private Integer propertyType;

    private Boolean enabled;

    private Boolean required;

    private Boolean supportValueImage;

    private Boolean valueImageRequired;

    private Integer sort;
}
