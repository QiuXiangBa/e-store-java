package com.followba.store.auth.dto;

import lombok.Data;

@Data
public class TradeUserAddressDTO {

    private Long id;

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
}
