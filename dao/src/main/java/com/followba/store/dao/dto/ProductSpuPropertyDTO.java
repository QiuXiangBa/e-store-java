package com.followba.store.dao.dto;

import java.util.Date;
import lombok.Data;

@Data
public class ProductSpuPropertyDTO {

    private Long id;

    private Long spuId;

    private Long propertyId;

    private String propertyName;

    private String valueText;

    private Integer sort;

    private String creator;

    private Date createTime;

    private String updater;

    private Date updateTime;

    private Boolean deleted;

    private Long tenantId;
}
