package com.followba.store.dao.dto;

import lombok.Data;

import java.util.Date;

/**
 * 用户收货地址 DTO / Trade user address DTO.
 */
@Data
public class TradeUserAddressDTO {

    private Long id;

    private Long userId;

    private String receiverName;

    private String receiverMobile;

    private String provinceCode;

    private String provinceName;

    private String cityCode;

    private String cityName;

    private String districtCode;

    private String districtName;

    private String detailAddress;

    private String postalCode;

    private Integer isDefault;

    private Integer status;

    private String creator;

    private Date createTime;

    private String updater;

    private Date updateTime;

    private Short deleted;

    private Long tenantId;
}
