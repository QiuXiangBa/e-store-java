package com.followba.store.dao.dto;

import java.util.Date;
import lombok.Data;

@Data
public class ProductCategoryPropertyDTO {

    private Long id;

    private Long categoryId;

    private Long propertyId;

    private Boolean enabled;

    private Boolean required;

    private Boolean supportValueImage;

    private Boolean valueImageRequired;

    private Integer sort;

    private String creator;

    private Date createTime;

    private String updater;

    private Date updateTime;

    private Boolean deleted;

    private Long tenantId;
}
